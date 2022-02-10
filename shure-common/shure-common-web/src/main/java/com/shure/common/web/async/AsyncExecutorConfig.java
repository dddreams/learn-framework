package com.shure.common.web.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Shure
 * @date 2022/2/8 15:32
 */
@Configuration
public class AsyncExecutorConfig {

    @Autowired
    private AsyncExecutorConfiguration asyncExecutorConfiguration;

    /**
     * 自定义线程参数
     *
     * @return
     */
    @Bean
    @Primary // 设置主要的线程池，避免和 spring 默认线程池冲突
    @ConditionalOnBean(annotation = EnableAsync.class)
    public Executor getAsyncExecutor() {
        // 创建线程池对象
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 设置线程池参数
        taskExecutor.setCorePoolSize(asyncExecutorConfiguration.getCoreNum()); // 核心线程数
        taskExecutor.setMaxPoolSize(asyncExecutorConfiguration.getMaxNum()); // 最大线程数
        taskExecutor.setKeepAliveSeconds(asyncExecutorConfiguration.getMaxTimeoutSec()); // 空闲线程的最大存活时间
        taskExecutor.setAllowCoreThreadTimeOut(asyncExecutorConfiguration.isAllowCoreThreadTimeOut()); // 是否允许核心线程超时
        taskExecutor.setThreadNamePrefix(asyncExecutorConfiguration.getThreadNamePrefix()); // 设置线程名称前缀
        taskExecutor.setRejectedExecutionHandler(asyncExecutorConfiguration.getRejectHandler().getRejectedExecutionHandler()); // 设置拒绝策略
        taskExecutor.setQueueCapacity(asyncExecutorConfiguration.getQueueCapacity()); // 阻塞队列的最大容量
        taskExecutor.initialize();
        return taskExecutor;
    }
}
