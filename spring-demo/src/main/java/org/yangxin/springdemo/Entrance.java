package org.yangxin.springdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.yangxin.springdemo.controller.WelcomeController;
import org.yangxin.springdemo.service.WelcomeService;

/**
 * @author yangxin
 * 2020/10/20 20:18
 */
@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@ComponentScan("org.yangxin.springdemo")
public class Entrance {

	public static void main1(String[] args) {
		System.out.println("Hello Spring!");
		String xmlPath = "//home/yangxin/IdeaProjects/spring-framework-5.2.9.RELEASE/spring-demo/src/main/resources/spring/spring-config.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
		welcomeService.sayHello("强大的spring框架。");
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Entrance.class);
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}
//		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeServiceImpl");
//		welcomeService.sayHello("强大的spring框架。");

		WelcomeController welcomeController = (WelcomeController) applicationContext.getBean("welcomeController");
		welcomeController.handleRequest();
	}
}
