package com.moke.theme.service.impl;

import com.moke.common.utils.CommonResult;
import com.moke.theme.Mapper.LifeThemeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.moke.theme.entity.LifeThemeEntity;
import com.moke.theme.service.LifeThemeService;


@Service
public class LifeThemeServiceImpl extends ServiceImpl<LifeThemeMapper, LifeThemeEntity> implements LifeThemeService {


    @Autowired
    private LifeThemeMapper lifeThemeMapper;

    @Override
    public CommonResult insert(LifeThemeEntity lifeTheme) {
        int insert = lifeThemeMapper.insert(lifeTheme);
        if (insert == 1) {
            return new CommonResult().success("修改成功");
        }
        return new CommonResult().failed("修改失败");
    }

    @Override
    public CommonResult delect(Long id) {
        int delect = lifeThemeMapper.deleteById(id);
        if (delect == 1) {
            return new CommonResult().success("删除成功");
        }
        return new CommonResult().failed("删除失败");
    }

    @Override
    public CommonResult update(LifeThemeEntity lifeTheme) {
        int update = lifeThemeMapper.updateById(lifeTheme);
        if (update == 1) {
            return new CommonResult().success("修改成功");
        }
        return new CommonResult().failed("修改失败");
    }

    @Override
    public CommonResult list(LifeThemeEntity lifeTheme, Integer pageNum, Integer pageSize) {

        QueryWrapper q = new QueryWrapper();
//        q.like("name", lifeTheme.getName());
        q.orderByDesc("create_date");

        IPage <LifeThemeEntity> recipeEntityIPage = lifeThemeMapper.selectPage(new Page <LifeThemeEntity>(pageNum, pageSize), q);

        return new CommonResult().success(recipeEntityIPage, "修改成功");
    }


    @Override
    public CommonResult selectById(Long id) {
            LifeThemeEntity lifeTheme = lifeThemeMapper.selectById(id);

        return new CommonResult().success(lifeTheme, "修改成功");
    }
}