package com.shure.common.web.apiversion.annotation;

import com.shure.common.web.apiversion.config.ApiVersionConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Shure
 * @date 2022/2/7 11:38
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(ApiVersionConfiguration.class)
public @interface EnableApiVersion {
}
