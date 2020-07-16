package com.moke.recipe.Mapper;

import com.moke.recipe.entity.RecipeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 食谱主表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-30 00:02:16
 */
@Mapper
public interface RecipeMapper extends BaseMapper<RecipeEntity> {

    List<RecipeEntity> listRecipeEntity ();

}
