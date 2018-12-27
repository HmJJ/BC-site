package com.springboot.code.utils;

public class BCsiteConstants {

    /**
     * resource状态(启用)
     */
    public static final String RESOURCE_STATUS_YES = "0";
    /**
     * resource状态(禁用)
     */
    public static final String RESOURCE_STATUS_NO = "1";
    /**
     *  linux查看版本信息
     */
    public static final String LINUX_CHECK_VERSION = "cat /proc/version";
    /**
     *  linux查看内核信息
     */
    public static final String LINUX_CHECK_KERNEL = "uname -a";
    /**
     *  linux查看发行版信息
     */
    public static final String LINUX_CHECK_RELEASE = "cat /etc/redhat-release";
    /**
     *  linux查看CPU相关信息
     */
    public static final String LINUX_CHECK_CPUINFO = "cat /proc/cpuinfo";
    /**
     *  linux查看系统运行位数
     */
    public static final String LINUX_CHECK_LONG_BIT = "getconf LONG_BIT";
    /**
     *  linux查看内存使用量
     */
    public static final String LINUX_CHECK_FREE = "free -m";

}
