package com.shure.common.mysql.plugin;

import com.shure.common.mysql.page.KenPage;
import com.shure.common.mysql.page.Page;
import com.shure.common.mysql.utils.MyBatisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Shure
 * @date 2022/1/26 15:05
 */
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )}
)
@Slf4j
public class PagePlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //Object target = null;
        //MetaObject invoMeteObject = SystemMetaObject.forObject(invocation);
        //while (invoMeteObject.hasGetter("h")) {
        //    target = invoMeteObject.getValue("h.target");
        //    invoMeteObject = SystemMetaObject.forObject(target);
        //}

        // 1、获得当前执行的 sql 语句
        StatementHandler statementHandler = MyBatisUtils.getNoPorxyTarget(invocation.getTarget());
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql().toLowerCase().trim();

        // 判断 sql 语句是否分页
        if (!sql.startsWith("select")) {
            // 如果不是 select 关键字开头  不分页 直接放行
            return invocation.proceed();
        }

        // 获取分页参数
        Page page = KenPage.getPage();
        if (page == null || !page.isEnable()) {
            // 如果没有 page 对象，说明当前没有设置分页参数，不分页，直接放行
            return invocation.proceed();
        }

        // 分页
        log.debug("[Page] - sql - {}", sql);

        // 先构建一个查询分页总条数的 sql
        Integer count = this.totalSql(invocation, statementHandler, sql);
        log.debug("[Page] - count - {}", count);

        // 校验page参数
        if (page.getPageNo() == null || page.getPageNo() < 1) page.setPageNo(1);
        if (page.getPageSize() == null || page.getPageSize() < 1) page.setPageSize(10);

        // 设置page对象
        page.setPageCount(count);
        page.setPageTotal(page.getPageCount() % page.getPageSize() == 0 ?
                page.getPageCount() / page.getPageSize() :
                page.getPageCount() / page.getPageSize() + 1);

        if (page.getPageNo() > page.getPageTotal()) page.setPageNo(page.getPageTotal());
        log.debug("[Page] - page object - {}", page);

        // sql 分页
        sql += " limit " + ((page.getPageNo() - 1) * page.getPageSize()) + "," + page.getPageSize();
        log.debug("[Page] - exec page sql - {}", sql);

        // 设置新sql 到BoundSql中实际执行的sql
        MetaObject metaObject = SystemMetaObject.forObject(boundSql);
        metaObject.setValue("sql", sql);

        return invocation.proceed();
    }

    /**
     * 执行查询总条数的方法
     *
     * @return
     */
    private Integer totalSql(Invocation invocation, StatementHandler statementHandler, String sql) throws SQLException {
        // 截取 order by 关键字
        int orderIndex;
        if ((orderIndex = sql.lastIndexOf("order by")) != -1) {
            sql = sql.substring(0, orderIndex);
        }

        // 构造 select count(*)
        int from = sql.indexOf("from");
        String totalSql = "select count(*) as total " + sql.substring(from);
        log.debug("[Page] - count sql - [{}]", totalSql);

        // 执行sql
        // 1，获取 prepare 的参数 Connection
        Connection conn = (Connection) invocation.getArgs()[0];
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 获取Statement
            statement = conn.prepareStatement(totalSql);

            // 设置参数， 使用 mybatis 提供的方法
            statementHandler.parameterize(statement);

            // 执行 sql
            resultSet = statement.executeQuery();

            // 获取结果
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        return -1;
    }

}
