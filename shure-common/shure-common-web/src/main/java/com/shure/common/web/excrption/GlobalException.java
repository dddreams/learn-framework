package com.shure.common.web.excrption;

import com.shure.common.core.result.Codes;
import com.shure.common.core.result.R;
import com.shure.common.core.utils.RUtils;
import com.shure.common.web.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理 - 处理基于 controller 抛出的异常
 *
 * @author Shure
 * @date 2022/1/21 11:34
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 参数校验，统一异常处理
     *
     * @return
     */
    @ExceptionHandler(BindException.class)
    public R bindExceptionHandler(BindException e) {
        // 获取绑定的返回对象，
        BindingResult result = e.getBindingResult();
        Set<String> errotSet = result.getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toSet());

        return RUtils.createFail(Codes.PARAMS_ERROR, errotSet);
    }

    /**
     * spring 提供的断言异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)

    public R illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("[Service-Exception] - 捕获到业务异常信息", e);
        return RUtils.createFail(Codes.SERVER_EXCEPTION.code, e.getMessage());
    }

    /**
     * 业务异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public R serviceExceptionHandler(ServiceException e) {
        log.error("[Service-Exception] - 捕获到业务异常", e);
        return RUtils.createFail(e.getCode(), e.getMsg());
    }

    /**
     * 默认异常处理
     *
     * @param t
     * @return
     */
    @ExceptionHandler
    public R globalExceptionHandler(Throwable t) {
        log.error("[Global-Exception] - 捕获到全局异常");
        HttpServletRequest request = HttpUtils.getRequest();
        String url = request.getRequestURI().toString();
        log.error("[Global-Exception] - 请求的url - {}", url);
        log.error("[Global-Exception] - 异常信息", t);
        return RUtils.createFail(Codes.SERVER_EXCEPTION);
    }
}
