package com.moke.moke.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.moke.common.utils.CommonResult;
import com.moke.moke.entity.MybatisDemoEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-09-11 23:17:48
 */
public interface MybatisDemoService extends IService<MybatisDemoEntity> {



    /**
        * 列表
        */
    public CommonResult list(MybatisDemoEntity mybatisDemo,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);
    /**
    * 增加
    * @param
    * @return
    */
    public CommonResult insert(MybatisDemoEntity mybatisDemo);


    /**
     * 修改
     */
    public CommonResult update(MybatisDemoEntity mybatisDemo);

    /**
     * 删除
     */
    public CommonResult delect(Long id);

    CommonResult selectById(Long id);
}

