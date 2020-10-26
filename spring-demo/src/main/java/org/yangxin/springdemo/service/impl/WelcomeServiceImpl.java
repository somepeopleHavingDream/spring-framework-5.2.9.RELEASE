package org.yangxin.springdemo.service.impl;

import org.springframework.stereotype.Service;
import org.yangxin.springdemo.service.WelcomeService;

/**
 * @author yangxin
 * 2020/10/20 21:01
 */
@Service
public class WelcomeServiceImpl implements WelcomeService {

	@Override
	public String sayHello(String name) {
		System.out.println("欢迎您：" + name);
		return "success";
	}
}
