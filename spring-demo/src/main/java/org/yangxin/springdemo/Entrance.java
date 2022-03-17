package org.yangxin.springdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.yangxin.springdemo.controller.WelcomeController;
import org.yangxin.springdemo.entity.User;
import org.yangxin.springdemo.service.WelcomeService;

/**
 * @author yangxin
 * 2020/10/20 20:18
 */
@SuppressWarnings({"SpringFacetCodeInspection", "AlibabaRemoveCommentedCode", "CommentedOutCode"})
@Configuration
@ComponentScan("org.yangxin.springdemo")
public class Entrance {

	public static void main(String[] args) {
		System.out.println("Hello Spring!");

		String xmlPath = "D:\\IdeaProjects\\spring-framework-5.2.9.RELEASE\\spring-demo\\src\\main\\resources\\spring\\spring-config.xml";
//		String xmlPath = "C:\\Users\\yangxin\\IdeaProjects\\spring-framework-5.2.9.RELEASE\\spring-demo\\src\\main\\resources\\spring\\spring-config.xml";
//		String xmlPath = "//home/yangxin/IdeaProjects/spring-framework-5.2.9.RELEASE/spring-demo/src/main/resources/spring/spring-config.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
		welcomeService.sayHello("强大的spring框架。");
//
//		// 得到无参构造函数创建的对象
//		User user1a = (User) applicationContext.getBean("user1");
//		User user1b = (User) applicationContext.getBean("user1");
//
//		// 得到静态工厂创建的对象
//		User user2a = (User) applicationContext.getBean("user2");
//		User user2b = (User) applicationContext.getBean("user2");
//
//		// 得到实例工厂创建的对象
//		User user3a = (User) applicationContext.getBean("user3");
//		User user3b = (User) applicationContext.getBean("user3");
//
//		// 得到beanFactory创建的对象
//		User user4a = (User) applicationContext.getBean("userFactoryBean");
//		User user4b = (User) applicationContext.getBean("userFactoryBean");
//
//		System.out.println("无参构造函数创建的对象：" + user1a);
//		System.out.println("无参构造函数创建的对象：" + user1b);
//		System.out.println("静态工厂创建的对象：" + user2a);
//		System.out.println("静态工厂创建的对象：" + user2b);
//		System.out.println("实例工厂创建的对象：" + user3a);
//		System.out.println("实例工厂创建的对象：" + user3b);
//		System.out.println("factoryBean创建的对象：" + user4a);
//		System.out.println("factoryBean创建的对象：" + user4b);
	}

	public static void main1(String[] args) {
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
