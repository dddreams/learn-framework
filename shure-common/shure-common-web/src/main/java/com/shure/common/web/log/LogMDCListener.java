package com.shure.common.web.log;

import org.slf4j.MDC;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring boot  的事件监听器
 * <p>
 * ApplicationEnvironmentPreparedEvent  应用环境准备事件
 *
 * @author Shure
 * @date 2022/1/20 16:49
 */
public class LogMDCListener implements GenericApplicationListener {

    private static final String APPLICATION_CONFIG_NAME = "configurationProperties";
    private static final String LOG_PATH = "spring.application.name";
    private static final String LOG_NAME = "logging.file.name";

    private static final Map<String, String> CATCH = new HashMap<>();

    /**
     * 设置当前要监听的事件类型
     *
     * @param resolvableType
     * @return
     */
    @Override

    public boolean supportsEventType(ResolvableType resolvableType) {
        // A.class.isAssignableFrom(B.class)
        // 如果A类是B类的父类（接口、听一个类型）返回 true
        //return ApplicationEnvironmentPreparedEvent.class == resolvableType.getRawClass();
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(resolvableType.getRawClass());
    }

    /**
     * 监听到事件的处理方法
     *
     * @param event
     */

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 解决引入配置中心后，实施刷新配置时 日志的变量获取不到的问题
        String logPath = CATCH.get("logPath");
        String logName = CATCH.get("logName");

        if (StringUtils.isEmpty(logName) || StringUtils.isEmpty(logName)) {
            // 获取当前服务的名称
            ApplicationEnvironmentPreparedEvent environmentPreparedEvent = (ApplicationEnvironmentPreparedEvent) event;
            // 获得配置环境对象
            ConfigurableEnvironment environment = environmentPreparedEvent.getEnvironment();
            // 获取属性来源集合，application.yml 配置文件
            MutablePropertySources propertySources = environment.getPropertySources();

            PropertySource<?> propertySource = propertySources.get(APPLICATION_CONFIG_NAME);
            logPath = (String) propertySource.getProperty(LOG_PATH);
            logName = (String) propertySource.getProperty(LOG_NAME);
            if (StringUtils.isEmpty(logName)) {
                logName = logPath;
            }
            CATCH.put("logPath", logPath);
            CATCH.put("logName", logName);
        }
        // 设置MDC
        MDC.put("logPath", logPath);
        MDC.put("logName", logName);
    }

    /**
     * 控制监听器的顺序，值越小，优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 19;
    }
}
