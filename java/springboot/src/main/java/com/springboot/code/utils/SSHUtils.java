package com.springboot.code.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

public class SSHUtils {

    private SSHUtils() {}

    private static SSHUtils instance;

    public static SSHUtils getInstance(String ip, Integer port, String userName, String userPwd) {
        if (instance == null || (instance != null && !instance.login())) {
            instance = new SSHUtils(ip, port, userName, userPwd);
        }
        return instance;
    }

    //字符编码
    private static String DEFAULTCHART ="utf-8";
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

    private SSHUtils(String ip, Integer port, String userName,String userPwd){
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    /**
     * 远程登录Linux主机
     * @return
     * */
    public boolean login()
    {
        boolean flag =false;
        conn = new Connection(ip, port);
        try {
            conn.connect();
            flag=conn.authenticateWithPassword(userName,userPwd);
            if(flag)
            {
                System.out.println("认证成功");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 远程执行shell脚本或命令
     * */
    public String execute(String cmd)
    {
        String result="";
        try{
            if(login())
            {
                Session session  = conn.openSession();
                session.execCommand(cmd);
                result = processStdout(session.getStdout(),DEFAULTCHART);
                if(result.equals("") || result==null){
                    result = processStdout(session.getStderr(),DEFAULTCHART);
                }
                conn.close();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String executeSuccess(String cmd)
    {
        String result="";
        try{
            if(login())
            {
                Session session = conn.openSession();
                session.execCommand(cmd);
                result = processStdout(session.getStdout(),DEFAULTCHART);
                conn.close();
                session.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 解析脚本执行返回的结果集
     * */
    public static String processStdout(InputStream is,String charset)
    {
        InputStream inputStream = new StreamGobbler(is);
        StringBuffer sb = new StringBuffer();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,charset));
            String line =null;
            while ((line = br.readLine()) !=null){
                sb.append(line +"\n");
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
