package com.zk.listener;

import com.zk.cache.CategoryCache;
import com.zk.utils.SpringContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BbsListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("监听器启动");
        ServletContext servletContext = event.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        SpringContextUtil.setContext(ctx);
        CategoryCache categoryCache = (CategoryCache) SpringContextUtil.getBean("categoryCache");
        //加载初始化
        categoryCache.refreshCategoryCache();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
