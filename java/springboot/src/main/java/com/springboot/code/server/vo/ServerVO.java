package com.springboot.code.server.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServerVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3036252313642299861L;

    /**
     * 服务器ip
     */
    private String ip;

    /**
     * 服务器端口
     */
    private Integer port;

    /**
     * 服务器用户名
     */
    private String userName;

    /**
     * 服务器密码
     */
    private String userPwd;

}
