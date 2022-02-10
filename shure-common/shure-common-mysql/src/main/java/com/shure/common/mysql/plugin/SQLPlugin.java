package com.shure.common.mysql.plugin;

import com.shure.common.mysql.utils.MyBatisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Statement;

/**
 * 记录 sql 执行时间的插件
 * <p>
 * type: 表示当前受增强对象的类型，
 * 1、记录当前执行的 sql 语句
 * 2、记录当前 sql 的执行十时间
 * method：表示增强内置对象的具体方法
 * args： 方法的参数列表（避免方法重载而无法识别具体增强的方法是哪一个）
 *
 * @author Shure
 * @date 2022/1/26 10:48
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "query",
                args = {Statement.class, ResultHandler.class}
        ),
        @Signature(
                type = StatementHandler.class,
                method = "update",
                args = {Statement.class}
        )}
)
@Slf4j
public class SQLPlugin implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 1、获得当前执行的 sql 语句
        StatementHandler statementHandler = MyBatisUtils.getNoPorxyTarget(invocation.getTarget());
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql().replace("\n", "");
        log.debug("[SQL] - exec sql - [{}]", sql);

        // 2、记录当前sql 的执行时间，其实就是 query 方法执行的时间
        long begin = System.currentTimeMillis();
        Object proceed = invocation.proceed();// 放行，执行实际的方法
        long end = System.currentTimeMillis();
        log.debug("[SQL] - sql exec time - [{}s]", BigDecimal.valueOf(end - begin).divide(BigDecimal.valueOf(1000)).setScale(6, RoundingMode.DOWN).doubleValue());
        return proceed;
    }

}
