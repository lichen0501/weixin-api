package com.onedt.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import com.onedt.wx.utils.SpringUtil;

//@EnableConfigurationProperties({ SysConfig.class })
@EnableFeignClients
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		ApplicationContext app = springApplication.run(args);
		SpringUtil.setApplication(app);
	}

}
