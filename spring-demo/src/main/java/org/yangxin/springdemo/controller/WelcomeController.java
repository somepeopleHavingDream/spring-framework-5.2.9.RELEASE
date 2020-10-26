package org.yangxin.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.yangxin.springdemo.service.WelcomeService;

/**
 * @author yangxin
 * 2020/10/26 21:15
 */
@Controller
public class WelcomeController {

	private final WelcomeService welcomeService;

	@Autowired
	public WelcomeController(WelcomeService welcomeService) {
		this.welcomeService = welcomeService;
	}

	public void handleRequest() {
		welcomeService.sayHello("来自Controller的问候。");
	}
}
