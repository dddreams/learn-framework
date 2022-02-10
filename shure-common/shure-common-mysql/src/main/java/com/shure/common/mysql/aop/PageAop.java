package com.shure.common.mysql.aop;

import com.shure.common.mysql.page.KenPage;
import com.shure.common.mysql.page.Page;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Shure
 * @date 2022/1/27 11:04
 */
@Aspect
public class PageAop {

    @Around("@annotation(com.shure.common.mysql.annotation.Paging)")
    public Object pagingAop(ProceedingJoinPoint joinPoint) throws Throwable {

        // 开启分页
        Page page = KenPage.getPage();
        // 判断是否未自动分页
        boolean isAutoPage = page.isEnable();
        if (page != null && !isAutoPage) {
            page.setEnable(true);
        }

        try {
            // 放行，执行sql
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            // 关闭分页
            if (page != null && !isAutoPage) {
                page.setEnable(false);
            }
        }
    }
}
