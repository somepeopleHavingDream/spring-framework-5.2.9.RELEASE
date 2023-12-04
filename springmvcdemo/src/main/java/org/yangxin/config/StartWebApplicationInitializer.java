package org.yangxin.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author yangxin
 * 2023/11/30 22:49
 */
public class StartWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * SpringContext 中相关的 bean
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{SpringRootConfig.class};
	}

	/**
	 * DispatcherServlet 中上下文相关的 bean
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{MVCConfig.class};
	}

	/**
	 * Servlet 请求映射路径
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	/**
	 * 拦截并处理请求的编码
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[]{encodingFilter};
	}
}
