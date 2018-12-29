package com.springboot.code.fabric.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.springboot.code.utils.CentosConstants;
import com.springboot.code.utils.SSHUtils;

/**
* @author nott
* @version 创建时间：2018年12月29日下午4:26:50
* 类说明
*/
@Service(value="fabricUtils")
public class FabricUtils {
	
	/**
	 * 检查docker版本
	 * @param conn
	 * @return
	 */
	public String checkDocker(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_DOCKER_VERSION);
		if(isInstalled(result)) {
			return result;
		}
		return "未安装Docker";
	}
	
	/**
	 * 检查docker-compose版本
	 * @param conn
	 * @return
	 */
	public String checkDockerCompose(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_DOCKER_COMPOSE_VERSION);
		if(isInstalled(result)) {
			return result;
		}
		return "未安装Docker-Compose";
	}

	/**
	 * 检查go版本
	 * @param conn
	 * @return
	 */
	public String checkGo(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_GO_VERSION);
		if(isInstalled(result)) {
			return result;
		}
		return "未安装Go";
	}

	/**
	 * 检查git版本
	 * @param conn
	 * @return
	 */
	public String checkGit(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_GIT_VERSION);
		if(isInstalled(result)) {
			return result;
		}
		return "未安装Git";
	}

	/**
	 * 检查是否下载了fabric源码
	 * @param conn
	 * @return
	 */
	public String checkFabricSource(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_FABRIC_POSITION);
		if(isInstalled(result)) {
			return result;
		}
		return "未下载fabric源码";
	}
	
	/**
	 * centos安装docker
	 * @param conn
	 * @return
	 */
	public String centosInstallDocker(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_DOCKER_VERSION);
		if(isInstalled(result)) {
			return result;
		} else {
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP1);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP2);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP3);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP4);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP5);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP6);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP7);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP8);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_STEP9);
			
			return checkDocker(conn);
		}
	}
	
	/**
	 * centos安装docker-compose
	 * @param conn
	 * @return
	 */
	public String centosInstallDockerCompose(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_DOCKER_COMPOSE_VERSION);
		if(isInstalled(result)) {
			return result;
		} else {
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_COMPOSE_STEP1);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_COMPOSE_STEP2);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_DOCKER_COMPOSE_STEP3);
			
			return checkDockerCompose(conn);
		}
	}
	
	/**
	 * centos安装go
	 * @param conn
	 * @return
	 */
	public String centosInstallGo(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_GO_VERSION);
		if(isInstalled(result)) {
			return result;
		} else {
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP1);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP2);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP3);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP4);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP5);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP6);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP7);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP8);
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GO_STEP9);
			
			return checkGo(conn);
		}
	}
	
	/**
	 * centos安装git
	 * @param conn
	 * @return
	 */
	public String centosInstallGit(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_GIT_VERSION);
		if(isInstalled(result)) {
			return result;
		} else {
			result = conn.execute(CentosConstants.CENTOS_INSTALL_GIT);
			
			return checkGit(conn);
		}
	}
	
	/**
	 * 下载fabric源码
	 * @param conn
	 * @return
	 */
	public String downloadFabric(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_FABRIC_POSITION);
		if(isInstalled(result)) {
			return result;
		} else {
			result = conn.execute(CentosConstants.CENTOS_INSTALL_ZIP);
			
			result = conn.execute(CentosConstants.CENTOS_DOWNLOAD_FABRIC);
			result = conn.execute(CentosConstants.CENTOS_REMOVE_FABRIC);
			result = conn.execute(CentosConstants.CENTOS_UNZIP_FABRIC);
			
			return checkFabricSource(conn);
		}
	}
	
	/**
	 * 检查是否安装
	 * @param result
	 * @return
	 */
	public Boolean isInstalled(String result) {
		if(result == null || result.isEmpty() ||
				StringUtils.isBlank(result) || result.contains("command not found")) {			
			return false;
		} else {
			return true;
		}
	}
	
}
