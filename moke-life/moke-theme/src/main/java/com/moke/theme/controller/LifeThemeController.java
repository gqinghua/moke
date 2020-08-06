package com.moke.theme.controller;

import com.moke.common.utils.CommonResult;
import com.moke.theme.entity.LifeThemeEntity;
import com.moke.theme.service.LifeThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 话题表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-08 00:07:02
 */
@RestController
@RequestMapping("/moke/lifetheme")
@Api(tags = "LifeThemeController", description = "")
public class LifeThemeController {
    @Autowired
    private LifeThemeService lifeThemeService;

    /**
     * 列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("分页查询")
    public CommonResult list(@RequestBody LifeThemeEntity lifeTheme,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        CommonResult list = lifeThemeService.list(lifeTheme, pageNum, pageSize);
        return list;

    }

    /**
    * 增加
    * @param
    * @return
    */
    @ApiOperation("增加")
    @PostMapping(value = "/insert")
    public CommonResult insert(@RequestBody LifeThemeEntity lifeTheme) {
        CommonResult insert = lifeThemeService.insert(lifeTheme);
        return insert;
    }


    /**
     * 修改
     */
    @ApiOperation("更新")
    @PostMapping(value = "/update")
    public CommonResult update(@RequestBody LifeThemeEntity lifeTheme) {
        CommonResult update = lifeThemeService.update(lifeTheme);

        return update;
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delect")
    public CommonResult delect(Long id) {
        CommonResult delect = lifeThemeService.delect(id);
        return delect;
    }

    @ApiOperation("分页查询")
    @GetMapping(value = "/selectById")
    public CommonResult selectById(Long id){
        CommonResult list = lifeThemeService.selectById(id);
        return list;
    }

}
