package com.springboot.code.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* @author nott
* @version 创建时间：2019年3月21日下午5:17:30
* 类说明
* 所有的可配置参数统一从这个类里面取
*/
@Component
public class ConfigureParam {

	private static String commandsPath;
	
	public static String getCommandsPath() {
		return commandsPath;
	}

	@Value("${commands_path}")
	private void setCommandsPath(String commands_path) {
		ConfigureParam.commandsPath = commands_path;
	}
	
}
