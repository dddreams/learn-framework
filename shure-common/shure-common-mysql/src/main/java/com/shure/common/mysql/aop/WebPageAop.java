package com.shure.common.mysql.aop;

import com.shure.common.mysql.page.BasePageResult;
import com.shure.common.mysql.page.KenPage;
import com.shure.common.mysql.page.Page;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shure
 * @date 2022/1/26 17:18
 */
@Aspect
public class WebPageAop {

    @Around("@within(org.springframework.web.bind.annotation.RestController)||@within(org.springframework.stereotype.Controller)")
    public Object pageAop(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取参数
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String pageType = request.getParameter("pageType");
        if (!StringUtils.isEmpty(pageNo) && !StringUtils.isEmpty(pageSize)) {
            // 设置分页参数
            KenPage.setPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));

            if (StringUtils.isEmpty(pageType) || !pageType.equals("1")) {
                Page page = KenPage.getPage();
                page.setEnable(true);
            }
        }

        try {
            Object result = joinPoint.proceed();

            // 获取page对象
            Page page = KenPage.getPage();
            if (result instanceof BasePageResult && page != null && page.getPageCount() != null) {
                BasePageResult basePageResult = (BasePageResult) result;
                basePageResult.setPage(page);
            }
            return result;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            // 清空PageUtils中的分页数据
            KenPage.clear();
        }
    }
}
