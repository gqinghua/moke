package com.moke.moke.controller;



import com.moke.common.utils.CommonResult;
import com.moke.moke.entity.MybatisDemoEntity;
import com.moke.moke.service.MybatisDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-09-11 23:17:48
 */
@RestController
@RequestMapping("/moke/mybatisdemo")
@Api(tags = "MybatisDemoController", description = "")
public class MybatisDemoController {
    @Autowired
    private MybatisDemoService mybatisDemoService;

    /**
     * 列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("分页查询")
    public CommonResult list(@RequestBody MybatisDemoEntity mybatisDemo,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        CommonResult list = mybatisDemoService.list(mybatisDemo, pageNum, pageSize);
        return list;

    }

    /**
    * 增加
    * @param
    * @return
    */
    @ApiOperation("增加")
    @PostMapping(value = "/insert")
    public CommonResult insert(@RequestBody MybatisDemoEntity mybatisDemo) {
        CommonResult insert = mybatisDemoService.insert(mybatisDemo);
        return insert;
    }


    /**
     * 修改
     */
    @ApiOperation("更新")
    @PostMapping(value = "/update")
    public CommonResult update(@RequestBody MybatisDemoEntity mybatisDemo) {
        CommonResult update = mybatisDemoService.update(mybatisDemo);

        return update;
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delect")
    public CommonResult delect(Long id) {
        CommonResult delect = mybatisDemoService.delect(id);
        return delect;
    }

    @ApiOperation("分页查询")
    @GetMapping(value = "/selectById")
    public CommonResult selectById(Long id){
        CommonResult list = mybatisDemoService.selectById(id);
        return list;
    }

}
