package org.yangxin.springdemo.entity.factory;

import org.springframework.beans.factory.FactoryBean;
import org.yangxin.springdemo.entity.User;

/**
 * @author yangxin
 * 2021/11/21 上午11:24
 */
public class UserFactoryBean implements FactoryBean<User> {

	@Override
	public User getObject() throws Exception {
		return new User();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}
