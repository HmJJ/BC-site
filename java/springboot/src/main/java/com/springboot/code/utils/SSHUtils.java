package com.springboot.code.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.StreamGobbler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SSHUtils {

	private SSHUtils() {
	}

	private static SSHUtils instance;

	public static SSHUtils getInstance() {
		return instance;
	}

	public static SSHUtils getInstance(String ip, Integer port, String userName, String userPwd) {
		instance = new SSHUtils(ip, port, userName, userPwd);
		return instance;
	}

	// 字符编码
	private static String DEFAULTCHART = "utf-8";
	private static Connection conn;

	@Getter
	@Setter
	private String ip;

	@Getter
	@Setter
	private Integer port;

	@Getter
	@Setter
	private String userName;

	@Getter
	@Setter
	private String userPwd;

	private SSHUtils(String ip, Integer port, String userName, String userPwd) {
		this.ip = ip;
		this.port = port;
		this.userName = userName;
		this.userPwd = userPwd;
	}

	private ch.ethz.ssh2.Session session;
	private com.jcraft.jsch.Session uploadsession;
	private Channel channel;
	private ChannelSftp channelSftp;
	
	/**
	 * 远程登录Linux主机
	 * 
	 * @return
	 */
	public boolean login() {
		boolean flag = false;
		conn = new Connection(ip, port);
		try {
			conn.connect();
			flag = conn.authenticateWithPassword(userName, userPwd);
			if (flag) {
				System.out.println("认证成功");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 远程执行shell脚本或命令
	 */
	public String execute(String cmd) {
		String result = "";
		try {
			if (login()) {
				session = conn.openSession();
				session.execCommand(cmd);
				result = processStdout(session.getStdout(), DEFAULTCHART);
				if (result.equals("") || result == null) {
					result = processStdout(session.getStderr(), DEFAULTCHART);
				}
				conn.close();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传文件
	 */
//	public String upload(String localTargetDirectory, String remoteFileDir) {
//		String result = "";
//		String cmd = "scp -P " + port + " " + localTargetDirectory + userName + "@" + ip + ":" + remoteFileDir;
//		result = execute(cmd);
//		return result;
//	}
	public boolean upload(String localTargetDirectory, String remoteFileDir) {
        if (channelSftp == null) {
            log.warn("need create channelSftp before upload file");
            return false;
        }

        if (channelSftp.isClosed()) {
            createChannel(); // 如果被关闭则应重新创建
        }

        try {
        	File file = new File(localTargetDirectory);
            channelSftp.put(file.getAbsolutePath(), remoteFileDir, ChannelSftp.OVERWRITE);
            log.info("sftp upload file success! src: {}, dst: {}", localTargetDirectory, remoteFileDir);
            return true;
        } catch (SftpException e) {
            log.error("sftp upload file failed! src: {}, dst: {}", localTargetDirectory, remoteFileDir, e);
            return false;
        }
       
    }
	
	public void createChannel() {
        try {
            JSch jSch = new JSch();
            uploadsession = jSch.getSession(userName, ip, port);

            if (userPwd != null) {
                // 使用用户名密码创建SSH
            	uploadsession.setPassword(userPwd);
            }

            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");  // 主动接收ECDSA key fingerprint，不进行HostKeyChecking
            uploadsession.setConfig(properties);
            uploadsession.setTimeout(0);  // 设置超时时间为无穷大
            uploadsession.connect(); // 通过session建立连接

            channel = uploadsession.openChannel("sftp"); // 打开SFTP通道
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            log.error("create sftp channel failed!", e);
        }
    }
	
	public void closeChannel() {
        if (channel != null) {
            channel.disconnect();
        }

        if (uploadsession != null) {
        	uploadsession.disconnect();;;
        }
    }
	
	public boolean removeFile(String remoteFileDir) {
        if (channelSftp == null) {
            log.warn("need create channelSftp before remove file");
            return false;
        }

        if (channelSftp.isClosed()) {
            createChannel(); // 如果被关闭则应重新创建
        }

        try {
            channelSftp.rm(remoteFileDir);
            log.info("sftp remove file success! dst: {}", remoteFileDir);
            return true;
        } catch (SftpException e) {
            log.error("sftp remove file failed! dst: {}", remoteFileDir, e);
            return false;
        }
    }

	public String executeSuccess(String cmd) {
		String result = "";
		try {
			if (login()) {
				session = conn.openSession();
				session.execCommand(cmd);
				result = processStdout(session.getStdout(), DEFAULTCHART);
				conn.close();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 解析脚本执行返回的结果集
	 */
	@SuppressWarnings("resource")
	public static String processStdout(InputStream is, String charset) {
		InputStream inputStream = new StreamGobbler(is);
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
