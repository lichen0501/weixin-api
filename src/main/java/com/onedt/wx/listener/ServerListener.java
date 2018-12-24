package com.onedt.wx.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统启动监听器
 * 
 * @author hy
 */
@WebListener
public class ServerListener implements ServletContextListener {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        logger.info("=================================");
        logger.info("系统[{}]启动完成!!!", contextEvent.getServletContext().getServletContextName());
        logger.info("=================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
