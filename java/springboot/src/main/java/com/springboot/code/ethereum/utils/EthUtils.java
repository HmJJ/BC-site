package com.springboot.code.ethereum.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.springboot.code.utils.CentosConstants;
import com.springboot.code.utils.ReadCommandUtils;
import com.springboot.code.utils.SSHUtils;

/**
* @author nott
* @version 创建时间：2019年1月8日上午10:38:51
* 类说明
*/
@Service(value="ethUtils")
@SuppressWarnings("unused")
public class EthUtils {
	
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
	 * 检查是否安装了go-ethereum
	 * @param conn
	 * @return
	 */
	public String checkGeth(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_GETH_VERSION);
		if(isInstalled(result)) {
			return result;
		}
		return "未安装geth";
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
			result = conn.execute(ReadCommandUtils.getCommand("centos","installGit").toString());
			
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
		result = conn.execute(ReadCommandUtils.getCommand("centos","uninstallGit").toString());
			
		return checkGit(conn);
	}
	
	/**
	 * centos安装eth工具
	 * @param conn
	 * @return
	 */
	public String installTools(SSHUtils conn) {
		String result = "";
		result = conn.execute(ReadCommandUtils.getCommand("centos","installEthTools").toString());
			
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
			result = conn.execute(ReadCommandUtils.getCommand("linux","installGo").toString());
			
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
		result = conn.execute(ReadCommandUtils.getCommand("linux","uninstallGo").toString());
			
		return checkGo(conn);
	}
	
	/**
	 * 下载go-ethereum源码并且安装
	 * @param conn
	 * @return
	 */
	public String downloadEth(SSHUtils conn) {
		String result = "";
		result = conn.execute(CentosConstants.CENTOS_CHECK_GETH_VERSION);
		if(isInstalled(result)) {
			return result;
		} else {
			result = conn.execute(ReadCommandUtils.getCommand("centos","downloadAndConfigEth").toString());
			
			return checkGeth(conn);
		}
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
