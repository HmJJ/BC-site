package com.springboot.code.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

/**
* @author nott
* @version 创建时间：2019年1月7日下午3:45:39
* 类说明
*/
public class ReadCommandUtils {

	public static StringBuffer getCommand(String OSType, String actName) {
		
		if(StringUtils.isBlank(actName)) {
			return new StringBuffer();
		}
		String path = "";
		
		try {
			switch (OSType) {
			case "centos":					
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/centos/"+actName+".txt").getPath();
				break;
			case "ubuntu":					
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/ubuntu/"+actName+".txt").getPath();
				break;
			default:
				path = ReadCommandUtils.class.getClassLoader().getResource("commands/"+actName+".txt").getPath();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isBlank(path)) {
			return new StringBuffer();
		}
		
		File file = new File(path);
		
		return readCommand(file);
		
	}

	private static StringBuffer readCommand(File file) {
		
		StringBuffer command = new StringBuffer();
		try {
			InputStream in = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			while(true) {
				line = br.readLine();
				if(line == null) {
					break;
				}
				if("".equals(line)) {
					continue;
				}
				if(StringUtils.isNotEmpty(command)) {
					command.append(" && " + line);										
				} else {
					command.append(line);					
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return command;
	}
	
}
