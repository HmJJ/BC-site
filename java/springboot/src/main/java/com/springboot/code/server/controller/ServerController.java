package com.springboot.code.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.springboot.basic.support.CommonRequestAttributes;
import com.springboot.basic.utils.StringUtils;
import com.springboot.code.server.vo.ServerVO;
import com.springboot.code.utils.BCsiteConstants;
import com.springboot.code.utils.SSHUtils;
import com.springboot.code.vo.VueCommonRespVO;

@Controller
@RequestMapping("server")
public class ServerController {

    @RequestMapping(value = "conn", method = RequestMethod.POST)
    @ResponseBody
    public String serverConn(CommonRequestAttributes commom, @RequestBody ServerVO serverVO){
        if(StringUtils.isBlank(serverVO.getIp()) || StringUtils.isBlank(serverVO.getUserName()) || StringUtils.isBlank(serverVO.getUserPwd())) {
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_FAILURE, Boolean.FALSE, "参数不正确或者为空，连接失败"));
        }

        HttpSession session = commom.getRequest().getSession();
        session.setAttribute("serverInfo", serverVO);
        session.setAttribute("connStatus", true);

        SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(), serverVO.getUserPwd());

        String result = "";

        result = conn.execute(BCsiteConstants.LINUX_CHECK_VERSION);

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("connStatus", conn.login());
        return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, map, "操作成功"));
    }

    @RequestMapping(value = "execute")
    @ResponseBody
    public String serverExecute(CommonRequestAttributes commom, @RequestParam String cmd){
        if(StringUtils.isBlank(cmd)) {
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, Boolean.FALSE, "参数不正确或者为空，连接失败"));
        }

        HttpSession session = commom.getRequest().getSession();
        ServerVO serverVO = (ServerVO) session.getAttribute("serverInfo");

        if(serverVO == null || StringUtils.isBlank(cmd)) {
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, Boolean.FALSE, "参数不正确或者为空，连接失败"));
        }

        SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(), serverVO.getUserPwd());

        String result = "";

        result = conn.execute(cmd);
        
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("connStatus", (boolean)session.getAttribute("connStatus"));
        return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, map, "操作成功"));
    }
    
    @RequestMapping(value = "serverInfo")
    @ResponseBody
    public String getServerInfo(CommonRequestAttributes commom){

    	Map<String, Object> map = new HashMap<>();
        HttpSession session = commom.getRequest().getSession();
        ServerVO serverVO = (ServerVO) session.getAttribute("serverInfo");
        
        map.put("serverVO", serverVO);
        if(serverVO == null) {
            map.put("connStatus", false);
        } else {
        	SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(), serverVO.getUserPwd());
            map.put("connStatus", conn.login());
        }
        
        return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, map, "操作成功"));
    }

}
