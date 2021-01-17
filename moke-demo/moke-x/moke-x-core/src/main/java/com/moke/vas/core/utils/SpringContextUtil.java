package com.moke.vas.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring context
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * implement application context aware invoke method
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Get object
     *
     * @return Object
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
    /**
     * get Object
     *
     * @return Object
     */
    public static Object getBean(Class classObj) throws BeansException {
        return applicationContext.getBean(classObj);
    }
}