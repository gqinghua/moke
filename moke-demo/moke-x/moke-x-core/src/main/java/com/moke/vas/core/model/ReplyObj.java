package com.moke.vas.core.model;

import io.vertx.core.json.Json;

/**
 * response message class
 */
public class ReplyObj<T> {

    /** status **/
    private int code = 200;

    /** message **/
    private String msg = "SUCCESS";

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
