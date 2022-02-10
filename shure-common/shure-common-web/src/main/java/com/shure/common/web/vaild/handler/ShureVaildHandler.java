package com.shure.common.web.vaild.handler;

import com.shure.common.web.vaild.annotation.ShureVaild;

/**
 * 拓展接口，主要让开发者实现该接口，拓展自定义校验规则
 *
 * @author Shure
 * @date 2022/1/21 16:34
 */
public interface ShureVaildHandler<T> {
    /**
     * 实际的校验方法
     *
     * @param data
     * @return
     */
    boolean vaild(ShureVaild shureVaild, T data);
}
