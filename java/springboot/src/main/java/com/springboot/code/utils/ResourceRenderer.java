package com.springboot.code.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
* @author nott
* @version 创建时间：2019年3月20日下午4:50:26
* 类说明
*/
public class ResourceRenderer {
	
	public static InputStream resourceLoader(String fileFullPath) throws IOException {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		return resourceLoader.getResource(fileFullPath).getInputStream();
	}

}
