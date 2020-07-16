package com.moke.recipe.controller;

import com.moke.common.utils.CommonResult;
import com.moke.recipe.entity.RecipeContentEntity;
import com.moke.recipe.service.RecipeContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 食谱表详情
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-03 22:32:44
 */
@RestController
@RequestMapping("/recipecontent")
@Api(tags = "RecipeContentController", description = "食谱主表详情")
public class RecipeContentController {

    @Autowired
    private RecipeContentService recipeContentService;

    /**
     * 列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("分页查询")
    public CommonResult list(RecipeContentEntity recipeContent,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        CommonResult list = recipeContentService.list(recipeContent, pageNum, pageSize);
        return list;

    }

    /**
    * 增加
    * @param
    * @return
    */
    @ApiOperation("增加")
    @PostMapping(value = "/insert")
    public CommonResult insert(@RequestBody RecipeContentEntity recipeContent) {
        CommonResult insert = recipeContentService.insert(recipeContent);
        return insert;
    }


    /**
     * 修改
     */
    @ApiOperation("更新")
    @PostMapping(value = "/update")
    public CommonResult update(@RequestBody RecipeContentEntity recipeContent) {
        CommonResult update = recipeContentService.update(recipeContent);

        return update;
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delect")
    public CommonResult delect(Long id) {
        CommonResult delect = recipeContentService.delect(id);
        return delect;
    }


    @ApiOperation("根据id查询")
    @GetMapping(value = "/selectById")
    public CommonResult selectById(Long id){
        CommonResult list = recipeContentService.selectById(id);
        return list;
    }
}
