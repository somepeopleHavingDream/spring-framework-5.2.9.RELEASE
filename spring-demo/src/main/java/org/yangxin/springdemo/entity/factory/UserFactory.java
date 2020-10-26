package org.yangxin.springdemo.entity.factory;

import org.yangxin.springdemo.entity.User;

/**
 * 实例工厂调用
 *
 * @author yangxin
 * 2020/10/26 21:39
 */
public class UserFactory {

	/**
	 * 普通的方法，返回User对象，
	 * 不能通过类名调用，需要通过对象调用
	 */
	public User getUser() {
		return new User();
	}
}
