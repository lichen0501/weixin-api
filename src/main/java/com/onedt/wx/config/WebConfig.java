package com.onedt.wx.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.onedt.wx.listener.ServerListener;

/**
 * Web配置
 * 
 * @author hy
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public ServletListenerRegistrationBean<ServerListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<ServerListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<ServerListener>();
        servletListenerRegistrationBean.setListener(new ServerListener());
        return servletListenerRegistrationBean;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
        super.addCorsMappings(registry);
    }
}
