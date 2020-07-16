package com.moke.recipe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.utils.CommonResult;
import com.moke.recipe.Mapper.RecipeContentMapper;
import com.moke.recipe.entity.RecipeContentEntity;
import com.moke.recipe.service.RecipeContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RecipeContentServiceImpl extends ServiceImpl<RecipeContentMapper, RecipeContentEntity> implements RecipeContentService {

    @Autowired
    private RecipeContentMapper recipeContentMapper;

    @Override
    public CommonResult insert(RecipeContentEntity recipeContent) {
        int insert = recipeContentMapper.insert(recipeContent);
        if (insert == 1) {
            return new CommonResult().success("修改成功");
        }
        return new CommonResult().failed("修改失败");
    }

    /**
     * 修改
     *
     * @param recipeContent
     */
    @Override
    public CommonResult update(RecipeContentEntity recipeContent) {
        int update = recipeContentMapper.updateById(recipeContent);
        if (update == 1) {
            return new CommonResult().success("修改成功");
        }
        return new CommonResult().failed("修改失败");
    }

    @Override
    public CommonResult delect(Long id) {
        int delect = recipeContentMapper.deleteById(id);
        if (delect == 1) {
            return new CommonResult().success("删除成功");
        }
        return new CommonResult().failed("删除失败");
    }

    @Override
    public CommonResult selectById(Long id) {
        RecipeContentEntity recipeContentEntity = recipeContentMapper.selectById(id);

        return new CommonResult().success(recipeContentEntity, "修改成功");
    }

//    @Override
//    public CommonResult update(RecipeEntity entity) {
//        int update = recipeContentMapper.updateById(entity);
//        if (update == 1) {
//            return new CommonResult().success("修改成功");
//        }
//        return new CommonResult().failed("修改失败");
//    }

    @Override
    public CommonResult list(RecipeContentEntity recipeContent, Integer pageNum, Integer pageSize) {

        QueryWrapper q = new QueryWrapper();
//        q.like("name", entity.getName());
        q.orderByDesc("create_date");


        IPage<RecipeContentEntity> recipeEntityIPage = recipeContentMapper.selectPage(new Page<RecipeContentEntity>(pageNum, pageSize), q);

        return new CommonResult().success(recipeEntityIPage, "修改成功");
    }

}