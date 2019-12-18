/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.demo.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private long total;

    private List<T> list;

    private String totalStr;


    /**
     * 分页
     * @param list   列表数据
     * @param total  总记录数
     */
    public PageData(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    /**
     * 分页
     * @param list   列表数据
     * @param total  总记录数
     * @param totalStr  总记录数
     */
    public PageData(List<T> list, long total, String totalStr) {
        this.list = list;
        this.total = total;
        this.totalStr = totalStr;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getTotalStr() {
        return totalStr;
    }

    public void setTotalStr(String totalStr) {
        this.totalStr = totalStr;
    }
}