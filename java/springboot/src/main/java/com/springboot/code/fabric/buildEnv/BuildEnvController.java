package com.springboot.code.fabric.buildEnv;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.basic.support.CommonRequestAttributes;
import com.springboot.code.server.vo.ServerVO;
import com.springboot.code.utils.BCsiteConstants;
import com.springboot.code.utils.CentosConstants;
import com.springboot.code.utils.SSHUtils;

/**
 * @author nott
 * @version 创建时间：2018年12月27日上午11:14:45 类说明
 */
@Controller
@RequestMapping("buildEnv")
public class BuildEnvController {
	
	public String buildEnv(CommonRequestAttributes commom, String step) {
		SSHUtils conn = getConn(commom);
		if(conn == null) {
			//TODO
		}
		String releaseInfo = conn.execute(BCsiteConstants.LINUX_CHECK_RELEASE);
		String release = releaseInfo.substring(0, releaseInfo.indexOf(" "));
		String result = "";
		switch (release) {
		case "Centos":
			result = buildCentosEnv(commom, step);
			break;
		case "Ubuntu":
			result = buildUbuntuEnv(commom, step);
			break;
		default:
			break;
		}
		return result;
	}

	public String buildCentosEnv(CommonRequestAttributes commom, String step) {
		Boolean flag = testIsConn(commom);
		if(!flag) {
			return null;
		}
		SSHUtils conn = getConn(commom);
		String result = "";
		switch (step) {
		case "docker":
			result = conn.execute(CentosConstants.CENTOS_CHECK_DOCKER_VERSION);
			break;
		case "docker-compose":

			break;
		case "go":

			break;
		case "git":

			break;
		case "fabric":

			break;

		default:
			break;
		}
		return result;
	}
	
	public String buildUbuntuEnv(CommonRequestAttributes commom, String step) {
		Boolean flag = testIsConn(commom);
		if(!flag) {
			return null;
		}
		SSHUtils conn = getConn(commom);
		String result = "";
		switch (step) {
		case "docker":
			
			break;
		case "docker-compose":

			break;
		case "go":

			break;
		case "git":

			break;
		case "fabric":

			break;

		default:
			break;
		}
		return result;
	}
	
	public SSHUtils getConn(CommonRequestAttributes commom) {
		HttpSession session = commom.getRequest().getSession();
		if (session.getAttribute("serverInfo") == null) {
			return null;
		}
		ServerVO serverVO = (ServerVO) session.getAttribute("serverInfo");

		SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(),
				serverVO.getUserPwd());
		return conn;
	}

	public Boolean testIsConn(CommonRequestAttributes commom) {
		SSHUtils conn = getConn(commom);
		if(conn == null) {
			return false;
		}
		return conn.login();
	}

}
