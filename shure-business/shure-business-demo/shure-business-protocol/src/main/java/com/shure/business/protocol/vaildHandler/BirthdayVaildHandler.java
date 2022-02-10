package com.shure.business.protocol.vaildHandler;

import com.shure.business.protocol.input.StudentInput;
import com.shure.common.web.vaild.annotation.ShureVaild;
import com.shure.common.web.vaild.handler.ShureVaildHandler;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Shure
 * @date 2022/1/21 17:07
 */
@Component
public class BirthdayVaildHandler implements ShureVaildHandler<StudentInput> {
    @Override
    public boolean vaild(ShureVaild shureVaild, StudentInput data) {
        System.out.println("出发自定义的校验");

        Integer age = data.getAge();
        Date birthday = data.getBrithday();
        if (age == null || birthday == null) {
            return true;
        }
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int nowYear = calendar.get(Calendar.YEAR);

        calendar.setTime(birthday);
        int birthdayYear = calendar.get(Calendar.YEAR);
        if (nowYear - birthdayYear == age) {
            return true;
        }
        return false;
    }
}
