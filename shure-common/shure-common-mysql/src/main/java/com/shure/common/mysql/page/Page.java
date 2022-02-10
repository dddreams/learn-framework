package com.shure.common.mysql.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Shure
 * @date 2022/1/26 15:04
 */
@Data
@Accessors(chain = true)
public class Page implements Serializable {

    private Integer pageNo;

    private Integer pageSize;

    private Integer pageTotal;

    private Integer pageCount;

    /**
     * 是否分页
     */

    private boolean isEnable;

    @JsonIgnore
    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
