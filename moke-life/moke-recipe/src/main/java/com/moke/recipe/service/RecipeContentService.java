package com.moke.recipe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moke.common.utils.CommonResult;
import com.moke.recipe.entity.RecipeContentEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 食谱表详情
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-03 22:32:44
 */
public interface RecipeContentService extends IService<RecipeContentEntity> {



    /**
        * 列表
        */
    public CommonResult list(RecipeContentEntity recipeContent,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);
    /**
    * 增加
    * @param
    * @return
    */
    public CommonResult insert(RecipeContentEntity recipeContent);


    /**
     * 修改
     */
    public CommonResult update(RecipeContentEntity recipeContent);

    /**
     * 删除
     */
    public CommonResult delect(Long id);

    CommonResult selectById(Long id);
}

