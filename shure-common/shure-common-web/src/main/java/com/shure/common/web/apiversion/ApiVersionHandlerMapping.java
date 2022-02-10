package com.shure.common.web.apiversion;

import com.shure.common.web.apiversion.annotation.ApiVersion;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 自定义的接口多版本映射器
 *
 * @author Shure
 * @date 2022/1/27 16:08
 */
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 容器初始化时触发该方法
     * 所有的 Controller 会依次调用该方法，根据当前 Controller 的类信息，返回一个符合当前 Controller 元数据的 RequestCondition 对象
     * <p>
     * 参数：就是一个 Controller 类对象
     *
     * @param handlerType
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        // 找到有 @AipVersion 注解的类
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return new ApiVersionRequestCondition(apiVersion != null ? apiVersion.value() : 1.0);
    }

    /**
     * 所有 Controller 中的所有方法都会依次调用该方法，根据当前的 Method 信息，返回一个 Method 对应的RequestCondition对象
     * 参数：就是Controller中的方法信息
     *
     * @param method
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        // 找到有 @AipVersion 注解的方法
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        if (apiVersion == null) {
            // 获取该方法 类上的注解
            apiVersion = AnnotationUtils.findAnnotation(method.getDeclaringClass(), ApiVersion.class);
        }
        return new ApiVersionRequestCondition(apiVersion != null ? apiVersion.value() : 1.0);
    }
}
