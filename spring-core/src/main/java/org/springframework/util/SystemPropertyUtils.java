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

package org.springframework.util;

import org.springframework.lang.Nullable;

/**
 * Helper class for resolving placeholders in texts. Usually applied to file paths.
 *
 * 帮助者类用于解析文本里的占位符。
 * 通常被用于文件路径。
 *
 * <p>A text may contain {@code ${...}} placeholders, to be resolved as system properties:
 * e.g. {@code ${user.dir}}. Default values can be supplied using the ":" separator
 * between key and value.
 *
 * 文本可能包括${..}占位符，该占位符将被解析为系统属性：
 * 比如${user.dir}。
 * 默认值能被使用：相隔的键值所提供。
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Dave Syer
 * @since 1.2.5
 * @see #PLACEHOLDER_PREFIX
 * @see #PLACEHOLDER_SUFFIX
 * @see System#getProperty(String)
 */
public abstract class SystemPropertyUtils {

	/** Prefix for system property placeholders: "${". */
	// 用于系统属性占位符的前缀：“${”
	public static final String PLACEHOLDER_PREFIX = "${";

	/** Suffix for system property placeholders: "}". */
	// 用于系统属性占位符的后缀：“}”
	public static final String PLACEHOLDER_SUFFIX = "}";

	/** Value separator for system property placeholders: ":". */
	// 用于系统属性占位符的值分隔符：“:"
	public static final String VALUE_SEPARATOR = ":";

	/**
	 * 严格的属性占位符帮助者
	 */
	private static final PropertyPlaceholderHelper strictHelper =
			new PropertyPlaceholderHelper(PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX, VALUE_SEPARATOR, false);

	private static final PropertyPlaceholderHelper nonStrictHelper =
			new PropertyPlaceholderHelper(PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX, VALUE_SEPARATOR, true);


	/**
	 * Resolve {@code ${...}} placeholders in the given text, replacing them with
	 * corresponding system property values.
	 * @param text the String to resolve
	 * @return the resolved String
	 * @throws IllegalArgumentException if there is an unresolvable placeholder
	 * @see #PLACEHOLDER_PREFIX
	 * @see #PLACEHOLDER_SUFFIX
	 */
	public static String resolvePlaceholders(String text) {
		return resolvePlaceholders(text, false);
	}

	/**
	 * Resolve {@code ${...}} placeholders in the given text, replacing them with
	 * corresponding system property values. Unresolvable placeholders with no default
	 * value are ignored and passed through unchanged if the flag is set to {@code true}.
	 * @param text the String to resolve
	 * @param ignoreUnresolvablePlaceholders whether unresolved placeholders are to be ignored
	 * @return the resolved String
	 * @throws IllegalArgumentException if there is an unresolvable placeholder
	 * @see #PLACEHOLDER_PREFIX
	 * @see #PLACEHOLDER_SUFFIX
	 * and the "ignoreUnresolvablePlaceholders" flag is {@code false}
	 */
	public static String resolvePlaceholders(String text, boolean ignoreUnresolvablePlaceholders) {
		if (text.isEmpty()) {
			return text;
		}
		PropertyPlaceholderHelper helper = (ignoreUnresolvablePlaceholders ? nonStrictHelper : strictHelper);
		return helper.replacePlaceholders(text, new SystemPropertyPlaceholderResolver(text));
	}


	/**
	 * PlaceholderResolver implementation that resolves against system properties
	 * and system environment variables.
	 */
	private static class SystemPropertyPlaceholderResolver implements PropertyPlaceholderHelper.PlaceholderResolver {

		private final String text;

		public SystemPropertyPlaceholderResolver(String text) {
			this.text = text;
		}

		@Override
		@Nullable
		public String resolvePlaceholder(String placeholderName) {
			try {
				String propVal = System.getProperty(placeholderName);
				if (propVal == null) {
					// Fall back to searching the system environment.
					propVal = System.getenv(placeholderName);
				}
				return propVal;
			}
			catch (Throwable ex) {
				System.err.println("Could not resolve placeholder '" + placeholderName + "' in [" +
						this.text + "] as system property: " + ex);
				return null;
			}
		}
	}

}
