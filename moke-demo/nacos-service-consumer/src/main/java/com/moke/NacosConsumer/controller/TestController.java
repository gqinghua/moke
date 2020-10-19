package com.moke.NacosConsumer.controller;

import com.moke.NacosConsumer.Edas.EchoService;
import com.moke.NacosConsumer.Interface.MyMethodAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.Sequence;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @program: sw
 * @ClassName TestController
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-13 16:19
 **/
@RestController
@Api(tags = "用户接口")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EchoService echoService;

    @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表",notes = "根据name获取用户列表")
    public String rest(@PathVariable String str) {
        return restTemplate.getForObject("http://service-provider/echo/" + str,
                String.class);
    }

    @RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
    public String feign(@PathVariable String str) {
        return echoService.echo(str);
    }


    @RequestMapping(value = "/Test002", method = RequestMethod.GET)
//    @MyMethodAnnotation(desc = "The Class Method sayHello", uri = "com.moke.NacosConsumer#sayHello")
    public String sayHello()  {
//        Class<TestController> clazz = TestController.class;
//        // 获得方法对象
//        Method myClassAnnotation = clazz.getMethod("setId", new Class[]{String.class});
//        MyMethodAnnotation myMethodAnnotation = myClassAnnotation.getAnnotation(MyMethodAnnotation.class);
//        System.out.println(myMethodAnnotation.desc() + "+" + myMethodAnnotation.uri());

        return "ss";
    }
}
