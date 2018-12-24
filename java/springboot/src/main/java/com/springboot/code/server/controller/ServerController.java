package com.springboot.code.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.basic.support.CommonRequestAttributes;
import com.springboot.basic.support.CommonResponse;
import com.springboot.basic.utils.StringUtils;
import com.springboot.code.server.vo.ServerVO;
import com.springboot.code.utils.BCsiteConstants;
import com.springboot.code.utils.SSHUtils;
import com.springboot.code.vo.VueCommonRespVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("server")
public class ServerController {

    @RequestMapping(value = "conn", method = RequestMethod.POST)
    @ResponseBody
    public String serverConn(CommonRequestAttributes commom, @RequestBody ServerVO serverVO){
        if(StringUtils.isBlank(serverVO.getIp()) || StringUtils.isBlank(serverVO.getUserName()) || StringUtils.isBlank(serverVO.getUserPwd())) {
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, Boolean.FALSE, "参数不正确或者为空，连接失败"));
        }

        HttpSession session = commom.getRequest().getSession();
        session.setAttribute("serverInfo", serverVO);

        SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(), serverVO.getUserPwd());

        String result = "";

        result = conn.execute(BCsiteConstants.CENTOS_CHECK_VERSION);
        return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, result, "操作成功"));
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
        return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, result, "操作成功"));
    }

}
