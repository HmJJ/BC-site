package com.springboot.code.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.springboot.basic.support.CommonRequestAttributes;
import com.springboot.code.server.vo.ServerVO;

/**
* @author nott
* @version 创建时间：2019年1月7日下午3:45:39
* 类说明
*/
public class ReadCommandUtils {

	/**
	 * 获得操作命令
	 * @param OSType
	 * @param actName
	 * @return
	 */
	public static StringBuffer getCommand(String OSType, String actName) {
		
		if(StringUtils.isBlank(actName)) {
			return new StringBuffer();
		}
		String path = "";
		
		try {
			switch (OSType) {
			case "centos":					
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/centos/"+actName+".txt").getPath();
				break;
			case "ubuntu":					
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/ubuntu/"+actName+".txt").getPath();
				break;
			default:
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/"+actName+".txt").getPath();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isBlank(path)) {
			return new StringBuffer();
		}
		
		File file = new File(path);
		
		return readCommand(file);
		
	}

	/**
	 * 读取操作文件内容
	 * @param file
	 * @return
	 */
	private static StringBuffer readCommand(File file) {
		
		StringBuffer command = new StringBuffer();
		try {
			InputStream in = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			while(true) {
				line = br.readLine();
				if(line == null) {
					break;
				}
				if("".equals(line)) {
					continue;
				}
				if(StringUtils.isNotEmpty(command)) {
					command.append("\r\n"); 
					command.append(line);										
				} else {
					command.append(line);					
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return command;
	}
	
	/**
	 * 上传执行脚本到服务器
	 * @param OSType
	 * @param actName
	 * @return
	 */
	public static String uploadCommand(SSHUtils conn, String OSType, String actName) {
		String result = "";
		if(StringUtils.isBlank(actName)) {
			return result;
		}
		String path = "";
		
		try {
			switch (OSType) {
			case "centos":					
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/centos/"+actName+".sh").getPath();
				break;
			case "ubuntu":					
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/ubuntu/"+actName+".sh").getPath();
				break;
			default:
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/"+actName+".sh").getPath();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isBlank(path)) {
			return result;
		}
		
		result = upload(conn, path, actName);
		
		return result;
		
	}
	
	/**
	 * 执行上传并赋可执行权限
	 * @param conn
	 * @param remoteFile
	 * @param actName
	 * @return
	 */
	private static String upload(SSHUtils conn, String localTargetFile, String actName) {
		String result = "";
		result = conn.execute("mkdir -p /opt/commands");
		String remoteFileDir = "/opt/commands/";
		conn.createChannel();
		conn.upload(localTargetFile, remoteFileDir);
		conn.closeChannel();
//		result = conn.execute("cd /opt/commands");
//		result = conn.execute("chmod +x /opt/commands/" + actName + ".sh");
		result = conn.execute("find /opt/commands/ -maxdepth 1 -path \"*" + actName + ".sh\"");
		return result;
	}
	
	/**
	 * 获得服务器连接对象
	 * @param commom
	 * @return
	 */
	public static SSHUtils getConn(CommonRequestAttributes commom) {
		HttpSession session = commom.getRequest().getSession();
		if (session.getAttribute("serverInfo") == null) {
			return null;
		}
		ServerVO serverVO = (ServerVO) session.getAttribute("serverInfo");

		SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(),
				serverVO.getUserPwd());
		return conn;
	}
	
}
