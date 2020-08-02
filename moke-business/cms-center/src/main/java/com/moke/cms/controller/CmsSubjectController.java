package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsSubject;
import com.moke.cms.service.ICmsSubjectService;
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
 * 专题表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "CmsSubjectController", description = "专题表管理")
@RequestMapping("/cms/CmsSubject")
public class CmsSubjectController {

    @Resource
    private ICmsSubjectService ICmsSubjectService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有专题表列表")
    @ApiOperation("根据条件查询所有专题表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsSubject:read')")
    public Result getCmsSubjectByPage(CmsSubject entity,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS((ICmsSubjectService.page(new Page<CmsSubject>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time"))));
        } catch (Exception e) {
            log.error("根据条件查询所有专题表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存专题表")
    @ApiOperation("保存专题表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsSubject:create')")
    public Result saveCmsSubject(@RequestBody CmsSubject entity) {
        try {
            if (ICmsSubjectService.saves(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("保存专题表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新专题表")
    @ApiOperation("更新专题表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubject:update')")
    public Result updateCmsSubject(@RequestBody CmsSubject entity) {
        try {
            if (ICmsSubjectService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新专题表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除专题表")
    @ApiOperation("删除专题表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubject:delete')")
    public Result deleteCmsSubject(@ApiParam("专题表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("专题表id");
            }
            if (ICmsSubjectService.removeById(id)) {
                return Result.SUCCESS("删除成功");
            }
        } catch (Exception e) {
            log.error("删除专题表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给专题表分配专题表")
    @ApiOperation("查询专题表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubject:read')")
    public Result getCmsSubjectById(@ApiParam("专题表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("专题表id");
            }
            CmsSubject coupon = ICmsSubjectService.getById(id);
            return Result.SUCCESS(coupon);
        } catch (Exception e) {
            log.error("查询专题表明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除专题表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除专题表")
    @PreAuthorize("hasAuthority('cms:CmsSubject:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsSubjectService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }
    @ApiOperation("修改推荐状态")
    @RequestMapping(value = "/update/updateRecommendStatus")
    @ResponseBody
    @SysLog(MODULE = "cms", REMARK = "修改推荐状态")
    public Result updateRecommendStatus(@RequestParam("ids") Long ids,
                                        @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = ICmsSubjectService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }
    @ApiOperation("修改展示状态")
    @RequestMapping(value = "/update/updateShowStatus")
    @ResponseBody
    @SysLog(MODULE = "cms", REMARK = "修改展示状态")
    public Result updateShowStatus(@RequestParam("ids") Long ids,
                                   @RequestParam("showStatus") Integer showStatus) {
        int count = ICmsSubjectService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }
}
