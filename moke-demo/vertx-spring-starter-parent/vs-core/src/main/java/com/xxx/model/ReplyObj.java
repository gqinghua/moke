package com.xxx.model;

import io.vertx.core.json.Json;

/**
 * 响应消息类
 */
public class ReplyObj<T> {

    private int code = 200;//状态

    private String msg = "SUCCESS";//消息

    private T data;

    public T getData() {
        return data;
    }

    public ReplyObj setData(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ReplyObj setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ReplyObj setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return Json.encode(this);
    }

    public static ReplyObj build() {
        return new ReplyObj();
    }
}
