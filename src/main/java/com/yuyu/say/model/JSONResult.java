/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.yuyu.say.model;

import java.io.Serializable;

/**
 * 响应数据
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public class JSONResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编码：0表示成功，其他值表示失败
     */
    private int status = 0;
    /**
     * 消息内容
     */
    private String message = "success";
    /**
     * 响应数据
     */
    private T content;

    public JSONResult<T> ok(T content) {
        this.setContent(content);
        return this;
    }

    public boolean success() {
        return status == 0 ? true : false;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
