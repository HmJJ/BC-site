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
     *  Centos 卸载旧版docker
     */
    public static final String CENTOS_UNINSTALL_DOCKER = "sudo yum remove docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-selinux docker-engine-selinux docker-engine";
    /**
     *  Centos 安装docker 步骤一
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP1 = "sudo yum install -y yum-utils device-mapper-persistent-data lvm2";
    /**
     *  Centos 安装docker 步骤二
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP2 = "sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo";
    /**
     *  Centos 安装docker 步骤三
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP3 = "sudo yum-config-manager --enable docker-ce-edge";
    /**
     *  Centos 安装docker 步骤四
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP4 = "sudo yum-config-manager --enable docker-ce-test";
    /**
     *  Centos 安装docker 步骤五
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP5 = "sudo yum-config-manager --disable docker-ce-edge";
    /**
     *  Centos 安装docker 步骤六
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP6 = "sudo yum install docker-ce";
    /**
     *  Centos 安装docker 步骤七
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP7 = "sudo systemctl start docker";
    /**
     *  Centos 安装docker 步骤八
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP8 = "service docker start";
    /**
     *  Centos 安装docker 步骤九
     */
    public static final String CENTOS_INSTALL_DOCKER_STEP9 = "chkconfig docker on";

    /**
     *  Centos 查看docker-compose版本
     */
    public static final String CENTOS_CHECK_DOCKER_COMPOSE_VERSION = "docker-compose --version";
    /**
     *  Centos 安装docker-compose 步骤一
     */
    public static final String CENTOS_INSTALL_DOCKER_COMPOSE_STEP1 = "yum install curl";
    /**
     *  Centos 安装docker-compose 步骤二
     */
    public static final String CENTOS_INSTALL_DOCKER_COMPOSE_STEP2 = "curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-composechmod +x /usr/local/bin/docker-compose";
    /**
     *  Centos 安装docker-compose 步骤三
     */
    public static final String CENTOS_INSTALL_DOCKER_COMPOSE_STEP3 = "cp /usr/local/bin/docker-compose /usr/bin";
    
    /**
     *  Centos 查看go版本
     */
    public static final String CENTOS_CHECK_GO_VERSION = "bash -lc 'go version'";
    /**
     *  Centos 安装go 步骤一
     */
    public static final String CENTOS_INSTALL_GO_STEP1 = "wget -P /usr/local/src https://storage.googleapis.com/golang/go1.10.1.linux-amd64.tar.gz";
    /**
     *  Centos 安装go 步骤二
     */
    public static final String CENTOS_INSTALL_GO_STEP2 = "tar -xvf go1.10.1.linux-amd64.tar.gz -C /usr/local";
    /**
     *  Centos 安装go 步骤三
     */
    public static final String CENTOS_INSTALL_GO_STEP3 = "mkdir -p /opt/gopath/src/github.com/hyperledger";
    /**
     *  Centos 安装go 步骤四
     */
    public static final String CENTOS_INSTALL_GO_STEP4 = "echo 'export GOPATH=/opt/gopath' >> /etc/profile";
    /**
     *  Centos 安装go 步骤五
     */
    public static final String CENTOS_INSTALL_GO_STEP5 = "echo 'export PATH=$PATH:/usr/local/go/bin:$GOPATH/bin' >> /etc/profile";
    /**
     *  Centos 安装go 步骤六
     */
    public static final String CENTOS_INSTALL_GO_STEP6 = "source /etc/profile";
    /**
     *  Centos 安装go 步骤七
     */
    public static final String CENTOS_INSTALL_GO_STEP7 = "ln -s $GOPATH /opt/gopath";
    /**
     *  Centos 安装go 步骤八
     */
    public static final String CENTOS_INSTALL_GO_STEP8 = "go get -u github.com/golang/lint/golint";
    /**
     *  Centos 安装go 步骤九
     */
    public static final String CENTOS_INSTALL_GO_STEP9 = "go install github.com/golang/lint/golint";

    /**
     *  Centos 查看git版本
     */
    public static final String CENTOS_CHECK_GIT_VERSION = "git version";
    /**
     *  Centos 安装git
     */
    public static final String CENTOS_INSTALL_GIT = "yum -y install git";
    
    /**
     *  Centos 安装zip/unzip
     */
    public static final String CENTOS_INSTALL_ZIP = "yum install zip unzip";
    
    /**
     *  查看 fabric文件位置
     */
    public static final String CENTOS_CHECK_FABRIC_POSITION = "find /opt/ -maxdepth 5 -path \"*fabric\"";
    /**
     *  Centos fabric源码下载
     */
    public static final String CENTOS_DOWNLOAD_FABRIC = "wget -P /opt/gopath/src/github.com/hyperledger https://github.com/hyperledger/fabric/archive/release-1.1.zip";
    /**
     *  Centos 删除已有的fabric文件夹
     */
    public static final String CENTOS_REMOVE_FABRIC = "cd /opt/gopath/src/github.com/hyperledger && rm -rf fabric";
    /**
     *  Centos 解压下载的fabric
     */
    public static final String CENTOS_UNZIP_FABRIC = "unzip release-1.1.zip -d /opt/gopath/src/github.com/hyperledger && mv release-1.1 fabric";
    
    /**
     *  Centos 运行fabric e2e_cli实例
     */
    public static final String CENTOS_FABRIC_E2E = "cd /opt/gopath/src/github.com/hyperledger/fabric/examples/e2e_cli && ./network_setup.sh up";
}
