package com.shure.common.web.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Shure
 * @date 2022/2/10 9:35
 */
@ConfigurationProperties(prefix = "async.executor")
@Data
public class AsyncExecutorConfiguration {

    /**
     * 核心线程数
     */
    private Integer coreNum;

    /**
     * 最大玺线程数
     */
    private Integer maxNum;

    /**
     * 最大空闲时间
     */
    private Integer maxTimeoutSec = 300;

    /**
     * 是否允许核心线程超时
     */
    private boolean allowCoreThreadTimeOut = false;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = "Ken-Executor-";

    /**
     * 阻塞队列的最大容量
     */
    private Integer queueCapacity = 1000;

    /**
     * 拒绝的处理器
     */
    private RejectEnum rejectHandler = RejectEnum.CallerRunsPlicy;

    @PostConstruct
    public void init() {
        // 配置核心线程数，cpu 核心数，
        // 1、获取cpu的核心数量
        int num = Runtime.getRuntime().availableProcessors();
        if (coreNum == null || coreNum <= 0) {
            coreNum = num;
        }

        // 配置最大线程数，cpu 核心数的 2 倍
        if (maxNum == null || maxNum <= 0) {
            maxNum = num * 2;
        }
    }

    public enum RejectEnum {

        // 抛出异常的方式
        AbortPolicy(new ThreadPoolExecutor.AbortPolicy()),
        // 提交任务的线程，自行执行该任务
        CallerRunsPlicy(new ThreadPoolExecutor.CallerRunsPolicy()),
        // 线程池会放弃当前等待队列中，最久的任务，当前被拒绝的任务放入队列
        DiscardOldestPolicy(new ThreadPoolExecutor.DiscardOldestPolicy()),
        // 直接丢弃当前拒绝的任务
        DiscardPolicy(new ThreadPoolExecutor.DiscardPolicy());

        /**
         * 拒绝策略的处理器
         */
        private RejectedExecutionHandler rejectedExecutionHandler;

        RejectEnum(RejectedExecutionHandler rejectedExecutionHandler) {
            this.rejectedExecutionHandler = rejectedExecutionHandler;
        }

        public RejectedExecutionHandler getRejectedExecutionHandler() {
            return rejectedExecutionHandler;
        }
    }
}
