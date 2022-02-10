package com.shure.common.web.apiversion;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shure
 * @date 2022/1/27 17:03
 */
public class ApiVersionRequestCondition implements RequestCondition<ApiVersionRequestCondition> {

    private final String VERSION_NAME = "version";

    /**
     * 当前的版本号
     */
    private double version;

    public double getVersion() {
        return this.version;
    }

    public ApiVersionRequestCondition(double version) {
        this.version = version;
    }


    /**
     * 当类的条件和方法的条件同时存在时，怎么处理，
     *
     * @param method
     * @return
     */
    @Override
    public ApiVersionRequestCondition combine(ApiVersionRequestCondition method) {
        // 当前 this 代表 controller 的请求条件
        // method 代表Controller中某个方法的请求条件
        return method;
    }

    /**
     * 根据请求返回符合条件的 requestCondition 对象，如果没有返回null
     *
     * @param request
     * @return
     */
    @Override
    public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        double reqVersion = 1.0;
        // 获取请求的版本号，
        String apiversion = request.getHeader(VERSION_NAME);
        if (StringUtils.isEmpty(apiversion)) {
            apiversion = request.getParameter(VERSION_NAME);
        }
        if (!StringUtils.isEmpty(apiversion)) {
            reqVersion = Math.max(Double.valueOf(apiversion), 1.0);
        }
        if (this.version <= reqVersion) {
            return this;
        }
        return null;
    }

    /**
     * 排序的作用，为了选择最优的请求条件，
     * 返回版本号相对较大的方法，
     *
     * @param other
     * @param request
     * @return
     */
    @Override
    public int compareTo(ApiVersionRequestCondition other, HttpServletRequest request) {
        return Double.compare(other.getVersion(), this.getVersion());
    }
}
