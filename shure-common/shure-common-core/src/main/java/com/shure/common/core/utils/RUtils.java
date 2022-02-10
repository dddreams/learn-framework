package com.shure.common.core.utils;

import com.shure.common.core.result.Codes;
import com.shure.common.core.result.R;

/**
 * 返回结果的 工具类
 *
 * @author Shure
 * @date 2022/1/21 11:20
 */
public class RUtils {

    /**
     * 创建成功的 R 对象
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> R createSucc(T data) {
        return new R(Codes.SUCCESS.code, Codes.SUCCESS.msg, data);
    }

    /**
     * 创建失败的 R 对象
     *
     * @param <T>
     * @return
     */
    public static <T> R createFail(Codes codes) {
        return new R(codes.code, codes.msg, null);
    }

    /**
     * 创建失败的 R 对象
     *
     * @param <T>
     * @return
     */
    public static <T> R createFail(Codes codes, T data) {
        return new R(codes.code, codes.msg, data);
    }

    /**
     * 创建失败的 R 对象
     *
     * @param <T>
     * @return
     */
    public static <T> R createFail(Integer code, String msg) {
        return new R(code, msg, null);
    }


    /**
     * 创建常规的 R 对象
     *
     * @param <T>
     * @return
     */
    public static <T> R create(Integer code, String msg, T data) {
        return new R(code, msg, data);
    }
}
