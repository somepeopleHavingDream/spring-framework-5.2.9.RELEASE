package org.yangxin.springdemo.service.impl;

import org.springframework.stereotype.Service;
import org.yangxin.springdemo.service.HelloService;

/**
 * @author yangxin
 * 2022/5/23 22:19
 */
@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public void sayHello() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Hello everybody");
	}

	@Override
	public void justWantToThrowException() {
		throw new RuntimeException("Hello exception");
	}
}
