package com.shure.common.web.vaild.constraint;

import com.shure.common.web.utils.ApplicationContextUtils;
import com.shure.common.web.vaild.annotation.ShureVaild;
import com.shure.common.web.vaild.handler.ShureVaildHandler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * 自定义的参数校验类
 *
 * @author Shure
 * @date 2022/1/21 16:29
 */
public class ShureVaildConstraint implements ConstraintValidator<ShureVaild, Object> {

    private ShureVaild shureVaild;

    @Override
    public void initialize(ShureVaild vaild) {
        this.shureVaild = vaild;
    }

    /**
     * 实际的校验方法， 校验通过返回 true，校验失败返回 false
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //System.out.println("自定义校验类");
        // 非空判断
        if (value != null) {
            //Class<? extends ShureVaildHandler> handler = shureVaild.handler();
            // 交给 具体的实现类 来校验数据
            ShureVaildHandler shureVaildHandler = ApplicationContextUtils.getBean(shureVaild.handler());
            return Optional.ofNullable(shureVaildHandler)
                    .map(shureVaildHandler1 -> {
                        return shureVaildHandler.vaild(shureVaild, value);
                    }).orElse(false);
        }
        return true;
    }
}
