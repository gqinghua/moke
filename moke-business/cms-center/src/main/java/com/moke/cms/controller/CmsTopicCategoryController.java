package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.cms.service.ICmsTopicCategoryService;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsTopicCategory;
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

/**
 * <p>
 * 话题分类表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "CmsTopicCategoryController", description = "话题分类表管理")
@RequestMapping("/cms/CmsTopicCategory")
public class CmsTopicCategoryController {

    @Resource
    private ICmsTopicCategoryService ICmsTopicCategoryService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有话题分类表列表")
    @ApiOperation("根据条件查询所有话题分类表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsTopicCategory:read')")
    public Result getCmsTopicCategoryByPage(CmsTopicCategory entity,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ICmsTopicCategoryService.page(new Page<CmsTopicCategory>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有话题分类表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存话题分类表")
    @ApiOperation("保存话题分类表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsTopicCategory:create')")
    public Result saveCmsTopicCategory(@RequestBody CmsTopicCategory entity) {
        try {
            if (ICmsTopicCategoryService.save(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("保存话题分类表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新话题分类表")
    @ApiOperation("更新话题分类表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopicCategory:update')")
    public Result updateCmsTopicCategory(@RequestBody CmsTopicCategory entity) {
        try {
            if (ICmsTopicCategoryService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新话题分类表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除话题分类表")
    @ApiOperation("删除话题分类表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopicCategory:delete')")
    public Result deleteCmsTopicCategory(@ApiParam("话题分类表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("话题分类表id");
            }
            if (ICmsTopicCategoryService.removeById(id)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("删除话题分类表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给话题分类表分配话题分类表")
    @ApiOperation("查询话题分类表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopicCategory:read')")
    public Result getCmsTopicCategoryById(@ApiParam("话题分类表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("话题分类表id");
            }
            CmsTopicCategory coupon = ICmsTopicCategoryService.getById(id);
            return Result.SUCCESS(coupon);
        } catch (Exception e) {
            log.error("查询话题分类表明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除话题分类表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除话题分类表")
    @PreAuthorize("hasAuthority('cms:CmsTopicCategory:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsTopicCategoryService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }

}
