package org.yangxin.springdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.yangxin.springdemo.controller.HelloController;
import org.yangxin.springdemo.controller.HiController;
import org.yangxin.springdemo.controller.WelcomeController;
import org.yangxin.springdemo.entity.User;
import org.yangxin.springdemo.introduction.LittleUniverse;
import org.yangxin.springdemo.service.WelcomeService;

/**
 * @author yangxin
 * 2020/10/20 20:18
 */
@SuppressWarnings({"SpringFacetCodeInspection", "AlibabaRemoveCommentedCode", "CommentedOutCode"})
@Configuration
@ComponentScan("org.yangxin.springdemo")
@EnableAspectJAutoProxy
public class Entrance {

//	public static void main(String[] args) {
//		System.out.println("Hello Spring!");

//		String xmlPath = "D:\\IdeaProjects\\spring-framework-5.2.9.RELEASE\\spring-demo\\src\\main\\resources\\spring\\spring-config.xml";
//		String xmlPath = "C:\\Users\\yangxin\\IdeaProjects\\spring-framework-5.2.9.RELEASE\\spring-demo\\src\\main\\resources\\spring\\spring-config.xml";
//		String xmlPath = "//home/yangxin/IdeaProjects/spring-framework-5.2.9.RELEASE/spring-demo/src/main/resources/spring/spring-config.xml";
//		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
//		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
//		welcomeService.sayHello("强大的spring框架。");
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
//	}

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Entrance.class);
		System.out.println("轮到AOP登场了");

//		HelloController helloController = context.getBean(HelloController.class);
//		helloController.handleRequest();

		HiController hiController = context.getBean(HiController.class);
//		hiController.handleRequest();
		((LittleUniverse) hiController).burningUp();
	}
}
