package com.springboot.code.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.basic.utils.StringUtils;
import com.springboot.code.server.vo.ServerVO;
import com.springboot.code.utils.BCsiteConstants;
import com.springboot.code.utils.SSHUtils;
import com.springboot.code.vo.VueCommonRespVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("server")
public class ServerController {

    @RequestMapping(value = "conn", method = RequestMethod.POST)
    @ResponseBody
    public String serverConn(@RequestBody ServerVO serverVO){
        System.out.println(serverVO.toString());
        if(StringUtils.isBlank(serverVO.getIp()) || StringUtils.isBlank(serverVO.getUserName()) || StringUtils.isBlank(serverVO.getUserPwd())) {
            return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, Boolean.FALSE, "参数不正确或者为空，连接失败"));
        }
        SSHUtils conn = SSHUtils.getInstance(serverVO.getIp(), serverVO.getPort(), serverVO.getUserName(), serverVO.getUserPwd());

        String result = "";

        result = conn.execute(BCsiteConstants.CENTOS_CHECK_VERSION);
        return JSONObject.toJSONString(new VueCommonRespVO(VueCommonRespVO.CODE_SUCCESS, result, "操作成功"));
    }

}
