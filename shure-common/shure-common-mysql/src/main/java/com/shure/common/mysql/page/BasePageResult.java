package com.shure.common.mysql.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shure
 * @date 2022/1/27 10:42
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class BasePageResult implements Serializable {

    private Page page;

}
