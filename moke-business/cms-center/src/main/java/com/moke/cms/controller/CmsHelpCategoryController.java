package com.moke.cms.controller;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.cms.service.ICmsHelpCategoryService;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsHelpCategory;
import com.moke.common.utils.CommonResult;
import com.moke.common.utils.ValidatorUtils;
import com.moke.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 帮助分类表
 * </p>
 *
 * @author
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "CmsHelpCategoryController", description = "帮助分类表管理")
@RequestMapping("/cms/CmsHelpCategory")
public class CmsHelpCategoryController {
    @Resource
    private ICmsHelpCategoryService ICmsHelpCategoryService;


    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有帮助分类表列表")
    @ApiOperation("根据条件查询所有帮助分类表列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('cms:CmsHelpCategory:read')")
    /**
     * 阿里jetcache的使用
     */
    @Cached( expire = 3600, cacheType= CacheType.BOTH)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600, timeUnit = TimeUnit.SECONDS)
    public Result getCmsHelpCategoryByPage(CmsHelpCategory entity,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ICmsHelpCategoryService.page(new Page<CmsHelpCategory>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有帮助分类表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存帮助分类表")
    @ApiOperation("保存帮助分类表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsHelpCategory:create')")
    public Result saveCmsHelpCategory(@RequestBody CmsHelpCategory entity) {
        try {
            if (ICmsHelpCategoryService.save(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("保存帮助分类表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新帮助分类表")
    @ApiOperation("更新帮助分类表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsHelpCategory:update')")
    public Result updateCmsHelpCategory(@RequestBody CmsHelpCategory entity) {
        try {
            if (ICmsHelpCategoryService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新帮助分类表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除帮助分类表")
    @ApiOperation("删除帮助分类表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsHelpCategory:delete')")
    public Result deleteCmsHelpCategory(@ApiParam("帮助分类表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("帮助分类表id");
            }
            if (ICmsHelpCategoryService.removeById(id)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("删除帮助分类表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给帮助分类表分配帮助分类表")
    @ApiOperation("查询帮助分类表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsHelpCategory:read')")
    @Cached( expire = 3600, cacheType= CacheType.BOTH)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600, timeUnit = TimeUnit.SECONDS)
    public Result getCmsHelpCategoryById(@ApiParam("帮助分类表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("帮助分类表id");
            }
            CmsHelpCategory coupon = ICmsHelpCategoryService.getById(id);
            return Result.SUCCESS(coupon);
        } catch (Exception e) {
            log.error("查询帮助分类表明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除帮助分类表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除帮助分类表")
    @PreAuthorize("hasAuthority('cms:CmsHelpCategory:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsHelpCategoryService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }

}
