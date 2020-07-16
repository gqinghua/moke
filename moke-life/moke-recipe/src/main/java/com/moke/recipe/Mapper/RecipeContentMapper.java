package com.moke.recipe.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.recipe.entity.RecipeContentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 食谱表详情
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-03 22:32:44
 */
@Mapper
public interface RecipeContentMapper extends BaseMapper<RecipeContentEntity> {
	
}
