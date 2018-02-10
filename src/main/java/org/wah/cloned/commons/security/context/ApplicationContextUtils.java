package org.wah.cloned.commons.security.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException{
        this.context = context;
    }

    public static ApplicationContext getContext(){
        return context;
    }

    public static Object getById(String id){
        return context.getBean(id);
    }

    public static Object getByClass(Class clazz){
        return context.getBean(clazz);
    }

    public static Map getBeansByClass(Class clazz){
        return context.getBeansOfType(clazz);
    }
}
