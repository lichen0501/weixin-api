package com.onedt.wx.utils;
/**
 * spring容器上下
 * @author chao
 *
 */

import org.springframework.context.ApplicationContext;

public class SpringUtil {
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplication() {
		return applicationContext;
	}

	public static void setApplication(ApplicationContext app) {
		System.out.println("applicationContext====" + app);
		applicationContext = app;
	}

	public static Object getBean(Class<?> type) {
		return applicationContext.getBean(type);
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 只获取前面几行的异常信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getMessage(Exception e) {
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++) {
			StackTraceElement s = trace[i];
			sb.append("\tat " + s + "\r\n");
			if (i > 5) {
				break;
			}
		}
		return sb.toString();
	}
}
