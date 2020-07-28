package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsSubjectCategory;
import com.moke.cms.service.ICmsSubjectCategoryService;
import com.moke.common.utils.CommonResult;
import com.moke.common.utils.ValidatorUtils;
import com.moke.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 专题分类表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "CmsSubjectCategoryController", description = "专题分类表管理")
@RequestMapping("/cms/CmsSubjectCategory")
public class CmsSubjectCategoryController {

    @Resource
    private ICmsSubjectCategoryService ICmsSubjectCategoryService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有专题分类表列表")
    @ApiOperation("根据条件查询所有专题分类表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsSubjectCategory:read')")
    public Result getCmsSubjectCategoryByPage(CmsSubjectCategory entity,
                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ICmsSubjectCategoryService.page(new Page<CmsSubjectCategory>(pageNum, pageSize), new QueryWrapper<>(entity)),"查询成功");
        } catch (Exception e) {
            log.error("根据条件查询所有专题分类表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存专题分类表")
    @ApiOperation("保存专题分类表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsSubjectCategory:create')")
    public Result saveCmsSubjectCategory(@RequestBody CmsSubjectCategory entity) {
        try {
            if (ICmsSubjectCategoryService.save(entity)) {
                return Result.SUCCESS("保存成功");
            }
        } catch (Exception e) {
            log.error("保存专题分类表：%s", e.getMessage(), e);
            return Result.failed("保存成功");
        }
        return Result.failed("保存失败");
    }

    @SysLog(MODULE = "cms", REMARK = "更新专题分类表")
    @ApiOperation("更新专题分类表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectCategory:update')")
    public Result updateCmsSubjectCategory(@RequestBody CmsSubjectCategory entity) {
        try {
            if (ICmsSubjectCategoryService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新专题分类表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除专题分类表")
    @ApiOperation("删除专题分类表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectCategory:delete')")
    public Result deleteCmsSubjectCategory(@ApiParam("专题分类表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("专题分类表id");
            }
            if (ICmsSubjectCategoryService.removeById(id)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("删除专题分类表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给专题分类表分配专题分类表")
    @ApiOperation("查询专题分类表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectCategory:read')")
    public Result getCmsSubjectCategoryById(@ApiParam("专题分类表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("专题分类表id");
            }
            CmsSubjectCategory coupon = ICmsSubjectCategoryService.getById(id);
            return Result.SUCCESS(coupon);
        } catch (Exception e) {
            log.error("查询专题分类表明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除专题分类表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除专题分类表")
    @PreAuthorize("hasAuthority('cms:CmsSubjectCategory:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsSubjectCategoryService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }

}
