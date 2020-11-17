package com.moke.util;

/**
 * @program: moke
 * @ClassName ss
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-11-11 16:04
 **/


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.StringUtils;

import java.io.IOException;

public class ObjectMapperUtil {

    /**
     * 1.将用户传递的数据转化为json串
     * 2.将用户传递的json串转化为对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //1.将用户传递的数据转化为json串
    public static String toJSON(Object object){

        if(object == null) {
            throw new RuntimeException("传递的数据为null.请检查");
        }

        try {
            String json = MAPPER.writeValueAsString(object);
            return json;
        } catch (JsonProcessingException e) {
            //将检查异常,转化为运行时异常
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //需求: 要求用户传递什么样的类型,我返回什么样的对象  泛型的知识
    public static <T> T toObj(String json,Class<T> target) throws IOException {
        if(StringUtils.isEmpty(json) || target ==null){
            throw new RuntimeException("参数不能为null");
        }
        try {
            return  MAPPER.readValue(json, target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

