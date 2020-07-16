package com.moke.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moke.common.model.SysStore;
import com.moke.user.service.ISysStoreService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/8/7.
 */
@Slf4j
@RestController
@Api(tags = "AppletSysController", description = "")
@RequestMapping("/notAuth")
public class AppletSysController {

    @Resource
    private ISysStoreService ISysStoreService;

    @GetMapping(value = "/selectStoreById")
    SysStore selectStoreById(Long id){
        return ISysStoreService.getById(id);
    }

    @GetMapping(value = "/selectStoreList")
    List<SysStore> selectStoreList(QueryWrapper<SysStore> sysStoreQueryWrapper){
        return  ISysStoreService.list(sysStoreQueryWrapper);
    }
}
