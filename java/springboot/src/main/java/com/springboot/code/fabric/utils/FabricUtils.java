package com.springboot.code.fabric.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.springboot.basic.support.CommonRequestAttributes;
import com.springboot.code.utils.CentosConstants;
import com.springboot.code.utils.ReadCommandUtils;
import com.springboot.code.utils.SSHUtils;

/**
* @author nott
* @version 创建时间：2018年12月29日下午4:26:50
* 类说明
*/
@Service(value="fabricUtils")
@SuppressWarnings("unused")
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
			result = ReadCommandUtils.uploadCommand(conn, "centos","installDocker");
			result = conn.execute("./opt/command/installDocker.sh");
			
			return checkDocker(conn);
		}
	}
	
	/**
	 * centos卸载docker
	 * @param conn
	 * @return
	 */
	public String centosUnInstallDocker(SSHUtils conn) {
		String result = "";
		result = ReadCommandUtils.uploadCommand(conn, "centos","uninstallDocker");
		result = conn.execute("./opt/command/uninstallDocker.sh");
		
		return checkDocker(conn);
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
			result = ReadCommandUtils.uploadCommand(conn, "centos","installDockerCompose");
			result = conn.execute("./opt/command/installDockerCompose.sh");
			
			return checkDockerCompose(conn);
		}
	}
	
	/**
	 * centos卸载docker-compose
	 * @param conn
	 * @return
	 */
	public String centosUnInstallDockerCompose(SSHUtils conn) {
		String result = "";
		result = ReadCommandUtils.uploadCommand(conn, "centos","uninstallDockerCompose");
		result = conn.execute("./opt/command/uninstallDockerCompose.sh");
		
		return checkDockerCompose(conn);
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
			result = ReadCommandUtils.uploadCommand(conn, "centos","installGit");
			result = conn.execute("./opt/command/installGit.sh");
			
			return checkGit(conn);
		}
	}
	
	/**
	 * centos卸载git
	 * @param conn
	 * @return
	 */
	public String centosUnInstallGit(SSHUtils conn) {
		String result = "";
		result = ReadCommandUtils.uploadCommand(conn, "centos","uninstallGit");
		result = conn.execute("./opt/command/uninstallGit.sh");
		
		return checkGit(conn);
	}
	
	/**
	 * centos安装工具
	 * @param conn
	 * @return
	 */
	public String installTools(SSHUtils conn) {
		String result = "";
		result = ReadCommandUtils.uploadCommand(conn, "centos","installFabricTools");
		result = conn.execute("./opt/command/installFabricTools.sh");
		
		return checkGo(conn);
	}
	
	/**
	 * 安装go
	 * @param conn
	 * @return
	 */
	public String installGo(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_GO_VERSION);
		if(isInstalled(result)) {
			return result;
		} else {
			result = ReadCommandUtils.uploadCommand(conn, "linux","installGo");
			result = conn.execute("./opt/command/installGo.sh");
			
			return checkGo(conn);
		}
	}
	
	/**
	 * 卸载go
	 * @param conn
	 * @return
	 */
	public String unInstallGo(SSHUtils conn) {
		String result = "";
		result = ReadCommandUtils.uploadCommand(conn, "linux","uninstallGo");
		result = conn.execute("./opt/command/uninstallGo.sh");
		
		return checkGo(conn);
	}
	
	/**
	 * 下载fabric源码并且测试e2e_cli实例
	 * @param conn
	 * @return
	 */
	public String downloadFabric(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_FABRIC_POSITION);
		if(isInstalled(result)) {
			return result;
		} else {
			result = ReadCommandUtils.uploadCommand(conn, "linux","downloadFabric");
			result = conn.execute("./opt/command/downloadFabric.sh");
			
			return checkFabricSource(conn);
		}
	}
	
	/**
	 * 测试fabric e2e_cli实例
	 * @param conn
	 * @return
	 */
	public String testFabric(SSHUtils conn) {
		String result = "";
		result = conn.execute("find /opt/commands/logs -maxdepth 1 -path \"*testFabricResult.log\"");
		if(StringUtils.isBlank(result) || !isInstalled(result)) {
			result = execTest(conn);
		} else {
			result = conn.execute("tail -n 9 /opt/commands/logs/testFabricResult.log");
			if(result.contains("ERROR")) {
				result = execTest(conn);
			}
			if(result.contains("All GOOD")) {
				result = result.substring(0, 83);
			}
		}
		return result;
	}
	
	private String execTest(SSHUtils conn) {
		String result = "";
		result = ReadCommandUtils.uploadCommand(conn,"linux","testFabric");
		if(!isInstalled(result)) {
			return "上传失败";
		}
		result = conn.execute("rm -rf /opt/commands/logs/testFabricResult.log");
		result = conn.execute("bash /opt/commands/testFabric.sh > /opt/commands/logs/testFabricResult.log 2>&1 &");
		Boolean flag = true;
		do{
			result = conn.execute("tail -n 9 /opt/commands/logs/testFabricResult.log");
			if(result.contains("ERROR")) {
				result = conn.execute("tail -n 4 /opt/commands/logs/testFabricResult.log");
				flag = false;
			}
			if(result.contains("All GOOD")) {
				result = conn.execute("tail -n 9 /opt/commands/logs/testFabricResult.log");
				flag = false;
			}
		}while(flag);
		return result;
	}
	
	/**
	 * 检查是否安装
	 * @param result
	 * @return
	 */
	public Boolean isInstalled(String result) {
		if(result == null || result.isEmpty() || 
				StringUtils.isBlank(result) || 
				result.contains("command not found") || 
				result.contains("No such file or directory") ||
				result.contains("Permission denied")) {
			return false;
		} else {
			return true;
		}
	}
	
}
