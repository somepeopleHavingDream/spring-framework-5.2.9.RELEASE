package org.yangxin.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.yangxin.springdemo.service.HiService;

/**
 * @author yangxin
 * 2022/5/23 22:27
 */
@Controller
public class HiController {

	private final HiService hiService;

	@Autowired
	public HiController(HiService hiService) {
		this.hiService = hiService;
	}

	public void handleRequest() {
		hiService.sayHi();
		hiService.justWantToSayHi();
	}
}
