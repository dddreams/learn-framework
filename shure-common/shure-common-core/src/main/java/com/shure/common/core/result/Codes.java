package com.shure.common.core.result;

/**
 * 返回结果码的枚举
 */
public enum Codes {

    SUCCESS(200, "success"),
    RESOURCE_NOT_FOUNT(404, "资源未找到"),
    PARAMS_ERROR(400, "参数校验未通过"),
    SERVER_EXCEPTION(500, "服务器异常");

    public Integer code;
    public String msg;

    Codes(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
