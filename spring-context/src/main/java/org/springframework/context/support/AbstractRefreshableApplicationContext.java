/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.support;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.lang.Nullable;

/**
 * Base class for {@link org.springframework.context.ApplicationContext}
 * implementations which are supposed to support multiple calls to {@link #refresh()},
 * creating a new internal bean factory instance every time.
 * Typically (but not necessarily), such a context will be driven by
 * a set of config locations to load bean definitions from.
 *
 * org.springframework.context.ApplicationContext实现的基类，它应当支持对刷新方法的多次调用，并且每次创建一个新的内部工厂实例。
 * 通常来说（但不是必要），这样的上下文会被一系列配置位置驱动以从中加载bean定义。
 *
 * <p>The only method to be implemented by subclasses is {@link #loadBeanDefinitions},
 * which gets invoked on each refresh. A concrete implementation is supposed to load
 * bean definitions into the given
 * {@link org.springframework.beans.factory.support.DefaultListableBeanFactory},
 * typically delegating to one or more specific bean definition readers.
 *
 * 被子类实现的唯一方法是loadBeanDefinitions，它在每次刷新时得到调用。
 * 具体的实现应该是将bean定义加载进给予的org.springframework.beans.factory.support.DefaultListableBeanFactory，
 * 这通常委托给一个或多个指定的bean定义阅读者。
 *
 * <p><b>Note that there is a similar base class for WebApplicationContexts.</b>
 * {@link org.springframework.web.context.support.AbstractRefreshableWebApplicationContext}
 * provides the same subclassing strategy, but additionally pre-implements
 * all context functionality for web environments. There is also a
 * pre-defined way to receive config locations for a web context.
 *
 * 注意，对于WebApplicationContexts，有一个相似的基类。
 * org.springframework.web.context.support.AbstractRefreshableWebApplicationContext提供了相同的子类化策略，还额外预实现了用于web环境的所有上下文功能。
 * 有一个预定义的方法，以接收对于web上下文的配置位置。
 *
 * <p>Concrete standalone subclasses of this base class, reading in a
 * specific bean definition format, are {@link ClassPathXmlApplicationContext}
 * and {@link FileSystemXmlApplicationContext}, which both derive from the
 * common {@link AbstractXmlApplicationContext} base class;
 * {@link org.springframework.context.annotation.AnnotationConfigApplicationContext}
 * supports {@code @Configuration}-annotated classes as a source of bean definitions.
 *
 * 以指定bean定义格式读取的基类的具体单独子类，是ClassPathXmlApplicationContext和FileSystemXmlApplication，它们都派生于公共的基类AbstractXmlApplicationContext；
 * org.springframework.context.annotation.AnnotationConfigApplicationContext支持把被@Configuration注解的类当作bean定义源。
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 1.1.3
 * @see #loadBeanDefinitions
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory
 * @see org.springframework.web.context.support.AbstractRefreshableWebApplicationContext
 * @see AbstractXmlApplicationContext
 * @see ClassPathXmlApplicationContext
 * @see FileSystemXmlApplicationContext
 * @see org.springframework.context.annotation.AnnotationConfigApplicationContext
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

	@Nullable
	private Boolean allowBeanDefinitionOverriding;

	@Nullable
	private Boolean allowCircularReferences;

	/**
	 * Bean factory for this context.
	 * */
	@Nullable
	private volatile DefaultListableBeanFactory beanFactory;


	/**
	 * Create a new AbstractRefreshableApplicationContext with no parent.
	 */
	public AbstractRefreshableApplicationContext() {
	}

	/**
	 * Create a new AbstractRefreshableApplicationContext with the given parent context.
	 *
	 * @param parent the parent context
	 */
	public AbstractRefreshableApplicationContext(@Nullable ApplicationContext parent) {
		super(parent);
	}


	/**
	 * Set whether it should be allowed to override bean definitions by registering
	 * a different definition with the same name, automatically replacing the former.
	 * If not, an exception will be thrown. Default is "true".
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowBeanDefinitionOverriding
	 */
	public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
		this.allowBeanDefinitionOverriding = allowBeanDefinitionOverriding;
	}

	/**
	 * Set whether to allow circular references between beans - and automatically
	 * try to resolve them.
	 * <p>Default is "true". Turn this off to throw an exception when encountering
	 * a circular reference, disallowing them completely.
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowCircularReferences
	 */
	public void setAllowCircularReferences(boolean allowCircularReferences) {
		this.allowCircularReferences = allowCircularReferences;
	}


	/**
	 * This implementation performs an actual refresh of this context's underlying
	 * bean factory, shutting down the previous bean factory (if any) and
	 * initializing a fresh bean factory for the next phase of the context's lifecycle.
	 */
	@Override
	protected final void refreshBeanFactory() throws BeansException {
		/*
			如果有，则先销毁工厂，再创建工厂
		 */

		// 如果当前可刷新应用上下文有Bean工厂
		if (hasBeanFactory()) {
			/*
				以下不细究
			 */
			destroyBeans();
			closeBeanFactory();
		}

		try {
			// 创建Bean工厂
			DefaultListableBeanFactory beanFactory = createBeanFactory();
			// 设置该工厂的序列化Id（实际上就是当前可刷新应用上下文的Id）
			beanFactory.setSerializationId(getId());

			// 定制Bean工厂（实际上就是给入参Bean工厂设置两个标记:是否允许Bean定义覆盖标记、是否允许循环引用标记）
			customizeBeanFactory(beanFactory);
			// 加载当前可刷新应用上下文的bean定义至bean工厂中
			loadBeanDefinitions(beanFactory);

			// 设值当前可刷新应用上下文的bean工厂
			this.beanFactory = beanFactory;
		}
		catch (IOException ex) {
			// 不细究
			throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);
		}
	}

	@Override
	protected void cancelRefresh(BeansException ex) {
		DefaultListableBeanFactory beanFactory = this.beanFactory;
		if (beanFactory != null) {
			beanFactory.setSerializationId(null);
		}
		super.cancelRefresh(ex);
	}

	@Override
	protected final void closeBeanFactory() {
		// 获得Bean工厂
		DefaultListableBeanFactory beanFactory = this.beanFactory;

		// 如果Bean工厂不为空
		if (beanFactory != null) {
			// Bean工厂设置序列化Id
			beanFactory.setSerializationId(null);
			this.beanFactory = null;
		}
	}

	/**
	 * Determine whether this context currently holds a bean factory,
	 * i.e. has been refreshed at least once and not been closed yet.
	 */
	protected final boolean hasBeanFactory() {
		// 返回此可刷新应用上下文的bean工厂是否不为null
		return (this.beanFactory != null);
	}

	@Override
	public final ConfigurableListableBeanFactory getBeanFactory() {
		// 获得本实例的Bean工厂
		DefaultListableBeanFactory beanFactory = this.beanFactory;

		// 如果bean工厂为空
		if (beanFactory == null) {
			// 不细究
			throw new IllegalStateException("BeanFactory not initialized or already closed - " +
					"call 'refresh' before accessing beans via the ApplicationContext");
		}

		// 返回Bean工厂
		return beanFactory;
	}

	/**
	 * Overridden to turn it into a no-op: With AbstractRefreshableApplicationContext,
	 * {@link #getBeanFactory()} serves a strong assertion for an active context anyway.
	 */
	@Override
	protected void assertBeanFactoryActive() {
	}

	/**
	 * Create an internal bean factory for this context.
	 * Called for each {@link #refresh()} attempt.
	 * <p>The default implementation creates a
	 * {@link org.springframework.beans.factory.support.DefaultListableBeanFactory}
	 * with the {@linkplain #getInternalParentBeanFactory() internal bean factory} of this
	 * context's parent as parent bean factory. Can be overridden in subclasses,
	 * for example to customize DefaultListableBeanFactory's settings.
	 *
	 * @return the bean factory for this context
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowBeanDefinitionOverriding
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowEagerClassLoading
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowCircularReferences
	 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#setAllowRawInjectionDespiteWrapping
	 */
	protected DefaultListableBeanFactory createBeanFactory() {
		// 先取得内部父Bean工厂,再创建默认可列出Bean工厂
		return new DefaultListableBeanFactory(getInternalParentBeanFactory());
	}

	/**
	 * Customize the internal bean factory used by this context.
	 * Called for each {@link #refresh()} attempt.
	 * <p>The default implementation applies this context's
	 * {@linkplain #setAllowBeanDefinitionOverriding "allowBeanDefinitionOverriding"}
	 * and {@linkplain #setAllowCircularReferences "allowCircularReferences"} settings,
	 * if specified. Can be overridden in subclasses to customize any of
	 * {@link DefaultListableBeanFactory}'s settings.
	 * @param beanFactory the newly created bean factory for this context
	 * @see DefaultListableBeanFactory#setAllowBeanDefinitionOverriding
	 * @see DefaultListableBeanFactory#setAllowCircularReferences
	 * @see DefaultListableBeanFactory#setAllowRawInjectionDespiteWrapping
	 * @see DefaultListableBeanFactory#setAllowEagerClassLoading
	 */
	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
		// 如果当前可刷新应用上下文允许bean定义覆盖
		if (this.allowBeanDefinitionOverriding != null) {
			// 以下不细究
			beanFactory.setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
		}
		// 如果当前可刷新应用上下文允许循环引用
		if (this.allowCircularReferences != null) {
			// 以下不细究
			beanFactory.setAllowCircularReferences(this.allowCircularReferences);
		}
	}

	/**
	 * Load bean definitions into the given bean factory, typically through
	 * delegating to one or more bean definition readers.
	 *
	 * @param beanFactory the bean factory to load bean definitions into
	 * @throws BeansException if parsing of the bean definitions failed
	 * @throws IOException if loading of bean definition files failed
	 * @see org.springframework.beans.factory.support.PropertiesBeanDefinitionReader
	 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader
	 */
	protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
			throws BeansException, IOException;

}
