package com.springboot.code.utils;
/**
* @author nott
* @version 创建时间：2018年12月27日下午2:11:08
* 类说明
*/
public class CentosConstants {

    /**
     *  Centos 查看docker版本
     */
    public static final String CENTOS_CHECK_DOCKER_VERSION = "docker --version";

    /**
     *  Centos 查看docker-compose版本
     */
    public static final String CENTOS_CHECK_DOCKER_COMPOSE_VERSION = "docker-compose --version";
    
    /**
     *  Centos 查看go版本
     */
    public static final String CENTOS_CHECK_GO_VERSION = "bash -lc 'go version'";

    /**
     *  Centos 查看git版本
     */
    public static final String CENTOS_CHECK_GIT_VERSION = "git version";
    
    /**
     *  查看 fabric文件位置
     */
    public static final String CENTOS_CHECK_FABRIC_POSITION = "find /opt/ -maxdepth 5 -path \"*fabric\"";
    
    /**
     *  查看 geth版本
     */
    public static final String CENTOS_CHECK_GETH_VERSION = "geth version";
    
}
