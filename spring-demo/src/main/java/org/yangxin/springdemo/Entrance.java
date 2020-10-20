package org.yangxin.springdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.yangxin.springdemo.service.WelcomeService;

/**
 * @author yangxin
 * 2020/10/20 20:18
 */
public class Entrance {

	public static void main(String[] args) {
		System.out.println("Hello Spring!");
		String xmlPath = "//home/yangxin/IdeaProjects/spring-framework-5.2.9.RELEASE/spring-demo/src/main/resources/spring/spring-config.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
		welcomeService.sayHello("强大的spring框架。");
	}
}
