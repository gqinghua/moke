package com.xxx.utils;

import java.util.Objects;

/**
 * 地址处理工具
 */
public final class EventBusAddressUtil {

    /**
     * 地址正向编,解决集群中“/”的问题,默认是以点的形式
     */
    public static String positiveFormate(Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz must not be null.");
        return clazz.getName();
    }

    /**
     * 地址正向编,解决集群中“/”的问题,默认是以点的形式
     * (适用于集群，将自定义的router api中“/” 转换成“.”)
     */
    public static String positiveFormate(String address) {
        Objects.requireNonNull(address, "address must not be null.");
        if (address.startsWith("/")) {
            address = address.substring(1);
        }
        return address.trim().replace("/", ".");
    }

    /**
     * 反向解析,还原地址
     * 适用于根据api router去寻址对应的class
     */
    public static String negativeFormate(String address) {
        Objects.requireNonNull(address, "address must not be null.");
        return address.trim().replace(".", "/");
    }
}
