package com.shure.common.mysql.config;

import com.shure.common.mysql.aop.PageAop;
import com.shure.common.mysql.aop.WebPageAop;
import com.shure.common.mysql.plugin.PagePlugin;
import com.shure.common.mysql.plugin.SQLPlugin;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Shure
 * @date 2022/1/25 15:08
 */
@Configuration
@MapperScan("com.shure.data.mapper")
@EnableTransactionManagement
public class DBAutoConfiguration {

    /**
     * sql监控插件
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "kenplugin.sql.enable", havingValue = "true", matchIfMissing = true)
    public SQLPlugin getSQLPlugin() {
        return new SQLPlugin();
    }

    /**
     * sql分页插件
     *
     * @return
     */
    @Bean
    public PagePlugin getPagePlugin() {
        return new PagePlugin();
    }

    /**
     * web层分页拦截的 AOP
     *
     * @return
     */
    @Bean
    public WebPageAop getWebPageAop() {
        return new WebPageAop();
    }

    /**
     * pageAop
     *
     * @return
     */
    @Bean
    public PageAop getPageAop() {
        return new PageAop();
    }


}
