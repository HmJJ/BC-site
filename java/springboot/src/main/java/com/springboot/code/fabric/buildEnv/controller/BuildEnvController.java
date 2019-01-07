package com.springboot.code.fabric.buildEnv.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.springboot.basic.support.CommonRequestAttributes;
import com.springboot.code.fabric.utils.FabricUtils;
import com.springboot.code.server.vo.ServerVO;
import com.springboot.code.utils.BCsiteConstants;
import com.springboot.code.utils.SSHUtils;
import com.springboot.code.vo.VueCommonRespVO;

/**
 * @author nott
 * @version 创建时间：2018年12月27日上午11:14:45 类说明
 */
@Controller
@RequestMapping("buildEnv")
public class BuildEnvController {
	
	@Autowired private FabricUtils fabricUtils;
	
	/**
	 * 部署前检查各个软件的安装情况
	 * @param commom
	 * @return
	 */
	@RequestMapping("checkVersion")
	@ResponseBody
	public String checkVersion(CommonRequestAttributes commom) {
		SSHUtils conn = getConn(commom);
		if(conn == null) {
			return JSON.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_FAILURE, "请检查服务器连接设置！"));
		}
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("docker", fabricUtils.checkDocker(conn));
		results.put("docker-compose", fabricUtils.checkDockerCompose(conn));
		results.put("go", fabricUtils.checkGo(conn));
		results.put("git", fabricUtils.checkGit(conn));
		results.put("fabric", fabricUtils.checkFabricSource(conn));
		
		return JSON.toJSONString(new VueCommonRespVO(results));
	}
	
	/**
	 * 执行一键部署
	 * @param commom
	 * @return
	 */
	@RequestMapping("oneTap")
	@ResponseBody
	public String oneTapBuildEnv(CommonRequestAttributes commom) {
		SSHUtils conn = getConn(commom);
		if(conn == null) {
			return JSON.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_FAILURE, "请检查服务器连接设置！"));
		}
		String releaseInfo = conn.execute(BCsiteConstants.LINUX_CHECK_RELEASE);
		String release = releaseInfo.substring(0, releaseInfo.indexOf(" "));
		Map<String, Object> results = new HashMap<String, Object>();
		switch (release) {
		case "CentOS":
			results.put("docker", fabricUtils.centosInstallDocker(conn));
			results.put("docker-compose", fabricUtils.centosInstallDockerCompose(conn));
			results.put("go", fabricUtils.installGo(conn));
			results.put("git", fabricUtils.centosInstallGit(conn));
			results.put("fabric", fabricUtils.downloadFabric(conn));
			break;
		case "Ubuntu":
			break;
		default:
			break;
		}
		
		return JSON.toJSONString(new VueCommonRespVO(results));
	}

	/**
	 * 执行分步部署
	 * @param commom
	 * @return
	 */
	@RequestMapping("subTap")
	@ResponseBody
	public String subTapBuildEnv(CommonRequestAttributes commom, @RequestParam String type) {
		if(StringUtils.isBlank(type)) {
			return JSON.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_FAILURE, "请检查参数"));
		}
		SSHUtils conn = getConn(commom);
		if(conn == null) {
			return JSON.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_FAILURE, "请检查服务器连接设置！"));
		}
		String releaseInfo = conn.execute(BCsiteConstants.LINUX_CHECK_RELEASE);
		String release = releaseInfo.substring(0, releaseInfo.indexOf(" "));
		Map<String, Object> results = new HashMap<String, Object>();
		switch (release) {
		case "CentOS":
			switch (type) {
			case "docker":
				results.put("docker-uninstall", fabricUtils.centosUnInstallDocker(conn));
				results.put("docker", fabricUtils.centosInstallDocker(conn));
				break;
			case "docker-compose":				
				results.put("docker-compose-uninstall", fabricUtils.centosUnInstallDockerCompose(conn));
				results.put("docker-compose", fabricUtils.centosInstallDockerCompose(conn));
				break;
			case "go":				
				results.put("go-uninstall", fabricUtils.unInstallGo(conn));
				results.put("go", fabricUtils.installGo(conn));
				break;
			case "git":				
				results.put("git-uninstall", fabricUtils.centosUnInstallGit(conn));
				results.put("git", fabricUtils.centosInstallGit(conn));
				break;
			case "fabric":				
				results.put("tools-install", fabricUtils.installTools(conn));
				results.put("fabric", fabricUtils.downloadFabric(conn));
				break;
			default:
				break;
			}
			break;
		case "Ubuntu":
			break;
		default:
			break;
		}
		
		return JSON.toJSONString(new VueCommonRespVO(results));
	}
	
	/**
	 * 获得服务器连接对象
	 * @param commom
	 * @return
	 */
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

	/**
	 * 检测是否连接正常
	 * @param commom
	 * @return
	 */
	public Boolean testIsConn(CommonRequestAttributes commom) {
		SSHUtils conn = getConn(commom);
		if(conn == null) {
			return false;
		}
		return conn.login();
	}
	
}
