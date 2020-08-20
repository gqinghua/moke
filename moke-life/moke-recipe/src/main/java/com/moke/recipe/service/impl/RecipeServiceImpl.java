package com.moke.recipe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.utils.CommonResult;
import com.moke.recipe.Mapper.RecipeMapper;
import com.moke.recipe.entity.RecipeEntity;
import com.moke.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;


@Service
public class RecipeServiceImpl extends ServiceImpl <RecipeMapper, RecipeEntity> implements RecipeService {

    @Resource
    private RecipeMapper recipeDao;

    @Override
    public CommonResult insert(RecipeEntity entity) {
        int insert = recipeDao.insert(entity);
        if (insert == 1) {
            return new CommonResult().success("添加成功");
        }
        return new CommonResult().failed("添加失败");
    }

    @Override
    public CommonResult delect(Long id) {
        int delect = recipeDao.deleteById(id);
        if (delect == 1) {
            return new CommonResult().success("删除成功");
        }
        return new CommonResult().failed("删除失败");
    }

    @Override
    public CommonResult update(RecipeEntity entity) {
//        QueryWrapper q = new QueryWrapper();
//        q.like("name", entity.getName());
//        q.orderByDesc("create_date");
        int update = recipeDao.updateById(entity);
        if (update == 1) {
            return new CommonResult().success("修改成功");
        }
        return new CommonResult().failed("修改失败");
    }

    @Override
    public CommonResult list(RecipeEntity entity, Integer pageNum, Integer pageSize) {
//        recipeDao.selectPage(, )
//        Page <Object> objects = new Page <>();
//        recipeDao.selectPage(, )
//    new Page<>()
//        IPage <RecipeEntity> objects = new Page();
//        recipeDao.selectPage(new Page<RecipeEntity>(pageNum, pageSize), new QueryWrapper<>(entity)))
//        QueryWrapper <RecipeEntity> name = new QueryWrapper <>(entity).like(entity.getName(), "name");
//        QueryWrapper <RecipeEntity> like = new QueryWrapper <>(entity).
//                orderByDesc("create_date").
//                like("name", entity.getName());



        QueryWrapper q = new QueryWrapper();
//        q.like("name", entity.getName());
        q.orderByDesc("create_date");
        Page<RecipeEntity> recipeEntityPage = new Page<>(pageNum, pageSize);
        IPage <RecipeEntity> recipeEntityIPage = recipeDao.selectPage(recipeEntityPage, q);

        return new CommonResult().success(recipeEntityIPage, "查询成功");
    }

    @Override
    public CommonResult selectById(Long id) {
        RecipeEntity recipeEntity = recipeDao.selectById(id);

        return new CommonResult().success(recipeEntity, "修改成功");
    }

    @Override
    public CommonResult selectone(Long id) {
//        QueryWrapper q = new QueryWrapper();
//        RecipeEntity recipeEntity = new RecipeEntity();
//        q.getEntity(recipeEntity);
//        recipeDao.selectOne(RecipeEntity);
        return null;
    }
}