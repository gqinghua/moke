package com.moke.common.model;

/**
 * @Author:
 */
public enum CodeEnum {
    SUCCESS(200),
    ERROR(300);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
