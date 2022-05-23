package org.yangxin.springdemo.service.impl;

import org.springframework.stereotype.Service;
import org.yangxin.springdemo.service.HiService;

/**
 * @author yangxin
 * 2022/5/23 22:21
 */
@Service
public class HiServiceImpl implements HiService {

	@Override
	public void sayHi() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Hi everyone");
	}

	@Override
	public String justWantToSayHi() {
		return "Just want to say hi.";
	}
}
