package com.shure.common.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Spring 容器的工具方法 - 手动从 Spring 容器中获取 bean 对象
 *
 * @author Shure
 * @date 2022/1/21 16:54
 */
@Component
public class ApplicationContextUtils {

    @Autowired
    private ApplicationContext applicationContext;

    private static ApplicationContext statisApplicationContext;

    /**
     * 初始化方法
     */
    @PostConstruct
    public void init() {
        ApplicationContextUtils.statisApplicationContext = applicationContext;
    }

    /**
     * 根据class 类型获取 Spring 容器中的队形
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls) {
        if (statisApplicationContext != null) {
            return statisApplicationContext.getBean(cls);
        }
        return null;
    }
}
