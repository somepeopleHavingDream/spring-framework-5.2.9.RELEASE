package org.yangxin.springdemo.entity.factory;

import org.yangxin.springdemo.entity.User;

/**
 * 静态工厂调用
 *
 * @author yangxin
 * 2020/10/26 21:35
 */
public class StaticFactory {

	/**
	 * 静态的方法，返回User对象
	 */
	public static User getUser() {
		return new User();
	}
}
