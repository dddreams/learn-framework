package com.shure.common.mysql.utils;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * @author Shure
 * @date 2022/1/26 16:58
 */
public class MyBatisUtils {

    public static <T> T getNoPorxyTarget(Object target) {
        MetaObject invoMeteObject = SystemMetaObject.forObject(target);
        while (invoMeteObject.hasGetter("h")) {
            target = invoMeteObject.getValue("h.target");
            invoMeteObject = SystemMetaObject.forObject(target);
        }
        return (T) target;
    }

}
