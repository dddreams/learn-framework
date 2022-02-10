package com.shure.common.mysql.page;

/**
 * 分页工具类
 *
 * @author Shure
 * @date 2022/1/26 15:20
 */
public class KenPage {

    private static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public static void setPage(Integer pageNo, Integer pageSize) {
        pageThreadLocal.set(new Page().setPageNo(pageNo).setPageSize(pageSize));
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    /**
     * 清空pageThreadLocal， 只会清空当前线程
     */
    public static void clear() {
        pageThreadLocal.remove();
    }
}
