package org.yangxin.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.yangxin.springdemo.service.HelloService;

/**
 * @author yangxin
 * 2022/5/23 22:23
 */
@Controller
public class HelloController {

	private final HelloService helloService;

	@Autowired
	public HelloController(HelloService helloService) {
		this.helloService = helloService;
	}

	public void handleRequest() {
		helloService.sayHello();
		helloService.justWantToThrowException();
	}
}
