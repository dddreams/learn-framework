package com.shure.common.web.excrption;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义的业务异常
 *
 * @author Shure
 * @date 2022/1/21 15:08
 */
@Data
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private Integer code;
    private String msg;
}
