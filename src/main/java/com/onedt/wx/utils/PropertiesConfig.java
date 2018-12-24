package com.onedt.wx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取properties/system.properties文件到Properties集合中
 * 
 * @author nemo
 * @version 1.0
 * @date 2017-4-12 下午8:32:26
 */
public class PropertiesConfig {
	private static Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);
	private static final Properties properties = new Properties();
	static {
		InputStream in=null,reader=null;
		try {
			in = PropertiesConfig.class.getClassLoader().getResourceAsStream("wechat/wechat.properties");
			Properties wechat = new Properties();
			wechat.load(in);
			reader = PropertiesConfig.class.getClassLoader().getResourceAsStream(wechat.getProperty("wechat"));
			properties.load(new InputStreamReader(reader, "UTF-8")); 
			properties.load(reader);
			logger.info("[PropertiesConfig]公众号参数：welcome_msg" + properties.get("wechat.title") + "初始化配置参数成功");
			logger.info("[PropertiesConfig]公众号参数：wechat.welcome_msg" + properties.get("wechat.wechat.welcome_msg") + "初始化配置参数成功");
		} catch (IOException e) {
			logger.error("加载PropertiesConfig配置信息，出现异常" + e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("关闭读取PropertiesConfig配置信息，出现异常" + e.getMessage(), PropertiesConfig.class);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("关闭读取PropertiesConfig配置信息，出现异常" + e.getMessage(), PropertiesConfig.class);
				}
			}
		}
	}

	/**
	 * 获取value值
	 * 
	 * @param key
	 * @return value
	 */
	public static String getValue(String key) {
		if (key == null || "".equals(key.trim())) {
			return "";
		}
		return properties.getProperty(key);
	}

	public static void main(String[] args) {
		// 测试
		System.out.println(getValue("ftp.host"));
	}
}
