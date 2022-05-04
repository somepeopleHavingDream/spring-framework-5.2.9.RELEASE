package org.yangxin.springdemo.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.yangxin.springdemo.service.WelcomeService;

/**
 * @author yangxin
 * 2020/10/26 21:15
 */
@Controller
public class WelcomeController implements ApplicationContextAware, BeanNameAware {

	private final WelcomeService welcomeService;

	private String myName;
	private ApplicationContext myContainer;

	@Autowired
	public WelcomeController(WelcomeService welcomeService) {
		this.welcomeService = welcomeService;
	}

	public void handleRequest() {
		welcomeService.sayHello("来自Controller的问候。");

		System.out.println("我是谁：" + myName);
		String[] beanDefinitionNames = myContainer.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println("召唤容器神兽，通过神兽获得：" + beanDefinitionName);
		}
	}

	@Override
	public void setBeanName(String name) {
		this.myName = name;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.myContainer = applicationContext;
	}
}
