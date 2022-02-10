package com.shure.business.protocol.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shure.business.protocol.vaildHandler.BirthdayVaildHandler;
import com.shure.common.web.vaild.annotation.ShureVaild;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author Shure
 * @date 2022/1/21 15:26
 */
@Data
@ShureVaild(message = "年龄与生日不匹配", handler = BirthdayVaildHandler.class)
public class StudentInput {

    @NotNull(message = "姓名不能为空")
    private String name;

    @Min(value = 1, message = "年龄不能小于 1")
    @Max(value = 20, message = "年龄不能大于 20")
    private Integer age;

    @Email
    @NotNull(message = "邮箱不能为空")
    private String email;

    @NotNull(message = "性别不能为空")
    private Integer sex;

    @NotNull(message = "生日不能为空")
    @Past(message = "生日超出范围")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date brithday;

}
