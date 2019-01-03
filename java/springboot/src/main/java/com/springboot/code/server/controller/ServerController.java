package com.springboot.code.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.springboot.basic.support.CommonRequestAttributes;
import com.springboot.basic.utils.StringUtils;
import com.springboot.code.entity.ServerInfo;
import com.springboot.code.entity.User;
import com.springboot.code.security.service.UserService;
import com.springboot.code.security.vo.CustomUserDetails;
import com.springboot.code.server.service.ServerService;
import com.springboot.code.server.vo.ServerVO;
import com.springboot.code.utils.BCsiteConstants;
import com.springboot.code.utils.DESCryptoUtils;
import com.springboot.code.utils.SSHUtils;
import com.springboot.code.vo.VueCommonRespVO;

@Controller
@RequestMapping("server")
public class ServerController {
	
	@Autowired private UserService userService;
	@Autowired private ServerService serverService;;

	/**
	 * 连接测试
	 * @param attributes
	 * @param model
	 * @param serverVO
	 * @return
	 */
    @RequestMapping(value = "conn", method = RequestMethod.POST)
    @ResponseBody
    public String serverConn(CommonRequestAttributes attributes, Model model, @RequestBody ServerVO serverVO){
        if(StringUtils.isBlank(serverVO.getIp()) || StringUtils.isBlank(serverVO.getUserName()) || StringUtils.isBlank(serverVO.getUserPwd())) {
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_FAILURE, Boolean.FALSE, "参数不正确或者为空，连接失败"));
        }

        Map<String, Object> map = new HashMap<>();

        SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(), serverVO.getUserPwd());

        String result = "";
        Boolean isLogin = conn.login();
        if(isLogin) {
        	HttpSession session = attributes.getRequest().getSession();
        	session.setAttribute("serverInfo", serverVO);
            session.setAttribute("connStatus", true);
        	CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
    				.getPrincipal();
    		if (details == null) {
    			return null;
    		}
    		User user = details.getUser();
    		
    		ServerInfo serverInfo = serverService.findByIp(serverVO.getIp(), serverVO.getPort());
    		
    		if(serverInfo != null && !checkChange(serverVO, serverInfo)) {
    			serverInfo.setPort(serverVO.getPort());
    			serverInfo.setUserName(serverVO.getUserName());
    			serverInfo.setUserPwd(serverVO.getUserPwd());
    			serverInfo = serverService.commit(user, serverInfo);
    		}
    		
    		if(serverInfo == null) {
    			serverInfo = serverService.commithandler(attributes, model, user, serverVO);
    			map.put("serverInfo", serverInfo);
    		}

    		userService.commit(user, serverInfo);
    		result = conn.execute(BCsiteConstants.LINUX_CHECK_VERSION);
    		map.put("result", result);
    		map.put("connStatus", isLogin);
        } else {
        	map.put("connStatus", false);
        	return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_FAILURE, map, "连接失败,请检查参数!"));
        }

        return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, map, "操作成功"));
    }

    /**
     * 执行命令
     * @param attributes
     * @param cmd
     * @return
     */
    @RequestMapping(value = "execute")
    @ResponseBody
    public String serverExecute(CommonRequestAttributes attributes, @RequestParam String cmd){
        if(StringUtils.isBlank(cmd)) {
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, Boolean.FALSE, "参数不正确或者为空，连接失败"));
        }

        HttpSession session = attributes.getRequest().getSession();
        ServerVO serverVO = (ServerVO) session.getAttribute("serverInfo");

        if(serverVO == null || StringUtils.isBlank(cmd)) {
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, Boolean.FALSE, "参数不正确或者为空，连接失败"));
        }

        SSHUtils conn = SSHUtils.getInstance();
        if(conn == null) {        	
        	conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(), serverVO.getUserPwd());
        }

        Boolean isConn = conn.login();;
        
        Map<String, Object> map = new HashMap<>();
        String result = "";
        if(isConn) {

            result = conn.execute(cmd);
            
            map.put("result", result);
            map.put("connStatus", isConn);
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, map, "操作成功"));
        } else {
        	map.put("result", result);
            map.put("connStatus", isConn);
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_FAILURE, map, "连接失败"));
        }
    }
    
    /**
     * 获得服务器信息
     * @param commom
     * @return
     */
    @RequestMapping(value = "serverInfo")
    @ResponseBody
    public String getServerInfo(CommonRequestAttributes commom){
    	Map<String, Object> map = new HashMap<>();
        HttpSession session = commom.getRequest().getSession();
        ServerVO serverVO = (ServerVO) session.getAttribute("serverInfo");
        
    	CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (details == null) {
			return null;
		}
		User user = details.getUser();
		
		Set<ServerInfo> serverInfos = new HashSet<>();
		
		if(user != null) {
			serverInfos = user.getServerInfos();
		}
        
		if(serverVO == null && !serverInfos.isEmpty()) {
			serverVO = new ServerVO();
			List<ServerInfo> serverList = new ArrayList<ServerInfo>(serverInfos);
			ServerInfo serverInfo = serverList.get(0);
			serverVO.setIp(serverInfo.getIp());
			serverVO.setPort(serverInfo.getPort());
			serverVO.setUserName(serverInfo.getUserName());
			serverVO.setUserPwd(DESCryptoUtils.decryptBasedDes(serverInfo.getUserPwd()));
			session.setAttribute("serverInfo", serverVO);
		}
		
        map.put("serverVO", serverVO);
        if(serverVO == null) {
            map.put("connStatus", false);
        } else {
        	SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(), serverVO.getUserPwd());
            map.put("connStatus", conn.login());
        }
        
        return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, map, "操作成功"));
    }

    /**
     * 校验同一ip的端口号以及用户名密码是否需要更新
     * @param newServer
     * @param oldServer
     * @return
     */
    public boolean checkChange(ServerVO newServer, ServerInfo oldServer) {
    	if(newServer == null || oldServer == null) {
    		return false;
    	}
    	
    	Boolean flag = false;
    	
    	if(newServer.getPort() == oldServer.getPort()) {
    		flag = true;
    	} else {
    		return flag;
    	}
    	
    	if(oldServer.getUserName().equals(newServer.getUserName())) {
    		flag = true;
    	} else {
    		return flag;
    	}
    	
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(newServer.getUserPwd(), oldServer.getUserPwd())) {
			flag = true;
		} else {
    		return flag;
    	}
		
		return flag;
    }
}
