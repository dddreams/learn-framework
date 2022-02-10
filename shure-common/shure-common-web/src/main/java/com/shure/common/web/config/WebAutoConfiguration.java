package com.shure.common.web.config;

import com.shure.common.web.async.AsyncExecutorConfig;
import com.shure.common.web.async.AsyncExecutorConfiguration;
import com.shure.common.web.excrption.GlobalException;
import com.shure.common.web.utils.ApplicationContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Web 模块的自动配置，使用 Spring Boot 的自动装配功能
 *
 * @author Shure
 * @date 2022/1/20 11:12
 */
@Configuration
@ComponentScan("com.shure.business")
public class WebAutoConfiguration {

    /**
     * 配置全局异常处理器
     *
     * @return
     */
    @Bean
    public GlobalException getGlobalException() {
        return new GlobalException();
    }

    /**
     * spring 容器的工具类
     *
     * @return
     */
    @Bean
    public ApplicationContextUtils getApplicationContextUtils() {
        return new ApplicationContextUtils();
    }

    /**
     * api 多接口配置自动装配
     *
     * @return
     */
    //@Bean
    //public ApiVersionConfiguration getApiVersionConfiguration() {
    //    return new ApiVersionConfiguration();
    //}

    /**
     * 配置异步线程池（取代 Spring 默认的线程池）
     *
     * @return
     */
    @Bean
    public AsyncExecutorConfig getAsyncExecutorConfig() {
        return new AsyncExecutorConfig();
    }

    /**
     * 线程池配套的配置类
     *
     * @return
     */
    @Bean
    public AsyncExecutorConfiguration getAsyncExecutorConfiguration() {
        return new AsyncExecutorConfiguration();
    }

}
