package com.shure.common.web.vaild.annotation;

import com.shure.common.web.vaild.constraint.ShureVaildConstraint;
import com.shure.common.web.vaild.handler.ShureVaildHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义参数校验注解
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
// 当前自定义的参数校验注解，对应的校验类型
@Constraint(validatedBy = ShureVaildConstraint.class)
public @interface ShureVaild {

    /**
     * 校验失败后的提示
     *
     * @return
     */
    String message() default "校验失败";

    /**
     * 校验的分组
     *
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 校验的负载
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 实际的处理校验的 Class 对象
     *
     * @return
     */
    Class<? extends ShureVaildHandler> handler();
}
