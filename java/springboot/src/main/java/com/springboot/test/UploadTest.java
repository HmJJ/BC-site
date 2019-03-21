package com.springboot.test;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import com.springboot.boot.BCSiteBoot;
import com.springboot.code.utils.ReadCommandUtils;

/**
* @author nott
* @version 创建时间：2019年3月21日下午3:30:24
* 类说明
*/
@SpringBootTest(classes= {BCSiteBoot.class})
@RunWith(SpringRunner.class)
public class UploadTest {
	
	@Value("${commands_path}")
	private String path;
	
	/**
	 * 外部静态文件访问测试
	 */
	@Test
	public void uploadTest() throws Exception {
		File f = new File(path+"/installGo.sh");
		FileCopyUtils.copy(f, new File("D:/test.txt"));
	}
	
	@Test
	public void readTest() throws Exception {
		
		StringBuffer commands = new StringBuffer();
		commands = ReadCommandUtils.getCommand("linux","installGo");
		System.out.println(commands);
		
	}
}
