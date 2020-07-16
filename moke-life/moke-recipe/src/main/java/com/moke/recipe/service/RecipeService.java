package com.moke.recipe.service;




import com.baomidou.mybatisplus.extension.service.IService;
import com.moke.common.utils.CommonResult;
import com.moke.recipe.entity.RecipeEntity;



/**
 * 食谱主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-02 00:31:24
 */
public interface RecipeService extends IService<RecipeEntity> {


//    增删改查
    CommonResult insert(RecipeEntity entity);

    CommonResult delect(Long id);

    CommonResult update(RecipeEntity entity);

    CommonResult list(RecipeEntity entity, Integer pageNum, Integer pageSize);

    CommonResult selectById(Long id);
}

