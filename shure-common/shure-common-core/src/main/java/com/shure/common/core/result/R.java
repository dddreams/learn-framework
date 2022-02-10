package com.shure.common.core.result;

import com.shure.common.mysql.page.BasePageResult;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 接口统一返回结果
 *
 * @author Shure
 * @date 2022/1/21 11:10
 */
@Data
@Accessors(chain = true)
public class R<T> extends BasePageResult {
    private Integer code;
    private String msg;
    private T data;

    public R() {
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
