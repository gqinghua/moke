package com.moke.NacosProvider.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: sw
 * @ClassName EchoController
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-13 16:13
 **/
@RestController
public class EchoController {
    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        System.out.println("我是酸菜鱼,又酸又菜又多鱼,你是一条舔狗");
        return string;
    }
}