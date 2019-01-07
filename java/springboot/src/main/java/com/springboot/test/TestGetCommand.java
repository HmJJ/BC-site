package com.springboot.test;

import com.springboot.code.utils.ReadCommandUtils;

/**
* @author nott
* @version 创建时间：2019年1月7日下午4:06:01
* 类说明
*/
public class TestGetCommand {

	public static void main(String[] args) {
		
		StringBuffer commands = new StringBuffer();
		commands = ReadCommandUtils.getCommand("linux","installGo");
		System.out.println(commands);
		
	}
	
}
