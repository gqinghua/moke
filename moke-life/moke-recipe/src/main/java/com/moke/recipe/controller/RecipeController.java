package com.moke.recipe.controller;


import com.moke.common.utils.CommonResult;
import com.moke.recipe.entity.RecipeEntity;
import com.moke.recipe.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 食谱主表
 *
 * @author
 * @email sunlightcs@gmail.com
 * @date 2020-05-02 00:31:24
 */
@Slf4j
@RestController
@Api(tags = "RecipeController", description = "食谱主表")
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    //    增删改查

    @ApiOperation("增加")
    @PostMapping(value = "/insert")
    public CommonResult insert(@RequestBody RecipeEntity entity) {
        CommonResult insert = recipeService.insert(entity);
        return insert;
    }
    @ApiOperation("删除")
    @GetMapping(value = "/delect")
    public CommonResult delect(Long id) {
        CommonResult delect = recipeService.delect(id);
        return delect;
    }

    @ApiOperation("更新")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@RequestBody RecipeEntity entity) {
        CommonResult update = recipeService.update(entity);
        return update;
    }

    @ApiOperation("分页查询")
    @PostMapping(value = "/list")
    public CommonResult list( RecipeEntity entity,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        CommonResult list = recipeService.list(entity, pageNum, pageSize);
        return  list;
    }

    @ApiOperation("根据id查询")
    @GetMapping(value = "/selectById")
    public CommonResult selectById(Long id){
        CommonResult list = recipeService.selectById(id);
        return list;
    }
}
