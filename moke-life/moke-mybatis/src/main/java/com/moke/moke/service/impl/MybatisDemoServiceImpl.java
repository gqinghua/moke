package com.moke.moke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.utils.CommonResult;
import com.moke.moke.dao.MybatisDemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.moke.moke.entity.MybatisDemoEntity;
import com.moke.moke.service.MybatisDemoService;


@Service("mybatisDemoService")
public class MybatisDemoServiceImpl extends ServiceImpl<MybatisDemoMapper, MybatisDemoEntity> implements MybatisDemoService {
    @Autowired
    private MybatisDemoMapper mybatisDemoMapper;

    @Override
    public CommonResult insert(MybatisDemoEntity mybatisDemo) {
        int insert = mybatisDemoMapper.insert(mybatisDemo);
        if (insert == 1) {
            return new CommonResult().success("修改成功");
        }
        return new CommonResult().failed("修改失败");
    }

    @Override
    public CommonResult delect(Long id) {
        int delect = mybatisDemoMapper.deleteById(id);
        if (delect == 1) {
            return new CommonResult().success("删除成功");
        }
        return new CommonResult().failed("删除失败");
    }

    @Override
    public CommonResult update(MybatisDemoEntity mybatisDemo) {
        int update = mybatisDemoMapper.updateById(mybatisDemo);
        if (update == 1) {
            return new CommonResult().success("修改成功");
        }
        return new CommonResult().failed("修改失败");
    }

    @Override
    public CommonResult list(MybatisDemoEntity mybatisDemo, Integer pageNum, Integer pageSize) {

        QueryWrapper q = new QueryWrapper();
//        q.like("name", mybatisDemo.getName());
        q.orderByDesc("create_date");

        IPage <MybatisDemoEntity> recipeEntityIPage = mybatisDemoMapper.selectPage(new Page <MybatisDemoEntity>(pageNum, pageSize), q);

        return new CommonResult().success(recipeEntityIPage, "修改成功");
    }


    @Override
    public CommonResult selectById(Long id) {
            MybatisDemoEntity mybatisDemo = mybatisDemoMapper.selectById(id);

        return new CommonResult().success(mybatisDemo, "修改成功");
    }
}