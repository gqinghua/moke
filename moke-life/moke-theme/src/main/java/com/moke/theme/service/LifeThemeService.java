package com.moke.theme.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moke.common.utils.CommonResult;

import com.moke.theme.entity.LifeThemeEntity;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * 话题表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-08 00:07:02
 */
public interface LifeThemeService extends IService<LifeThemeEntity> {



    /**
        * 列表
        */
    public CommonResult list(LifeThemeEntity lifeTheme,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);
    /**
    * 增加
    * @param
    * @return
    */
    public CommonResult insert(LifeThemeEntity lifeTheme);


    /**
     * 修改
     */
    public CommonResult update(LifeThemeEntity lifeTheme);

    /**
     * 删除
     */
    public CommonResult delect(Long id);

    CommonResult selectById(Long id);
}

