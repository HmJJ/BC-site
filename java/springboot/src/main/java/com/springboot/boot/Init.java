package com.springboot.boot;

import com.springboot.code.entity.User;
import com.springboot.code.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements ApplicationRunner {

	@Autowired
	private UserService userService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 在这里写入初始化时运行的代码
		User user = userService.findByUsername("admin");
		if(user == null) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword("admin");
			admin.setName("nott");
			userService.commit(admin, true);
		}
	}

}
