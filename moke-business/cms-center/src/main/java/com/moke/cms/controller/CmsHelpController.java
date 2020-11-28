package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.cms.service.ICmsHelpService;
import com.moke.common.annotation.LoginUser;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsHelp;
import com.moke.common.model.SysUser;
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
 * 帮助表
 * </p>
 *
 * @author
 * @since
 */
@Slf4j
@RestController
@Api(tags = "CmsHelpController", description = "帮助表管理")
@RequestMapping("/cms/CmsHelp")
public class CmsHelpController {
    @Resource
    private ICmsHelpService ICmsHelpService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有帮助表列表")
    @ApiOperation("根据条件查询所有帮助表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsHelp:read')")
    public Result getCmsHelpByPage(CmsHelp entity,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ICmsHelpService.page(new Page<CmsHelp>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));

        } catch (Exception e) {
            log.error("根据条件查询所有帮助表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存帮助表")
    @ApiOperation("保存帮助表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsHelp:create')")
    public Result saveCmsHelp(@RequestBody CmsHelp entity ,@LoginUser(isFull = true) SysUser user) {
        System.out.println(user);
        try {
            if (ICmsHelpService.saves(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("保存帮助表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新帮助表")
    @ApiOperation("更新帮助表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsHelp:update')")
    public Result updateCmsHelp(@RequestBody CmsHelp entity) {
        try {
            if (ICmsHelpService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新帮助表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除帮助表")
    @ApiOperation("删除帮助表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsHelp:delete')")
    public Result deleteCmsHelp(@ApiParam("帮助表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("帮助表id");
            }
            if (ICmsHelpService.removeById(id)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("删除帮助表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给帮助表分配帮助表")
    @ApiOperation("查询帮助表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsHelp:read')")
    public Result getCmsHelpById(@ApiParam("帮助表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.SUCCESS("帮助表id");
            }
            CmsHelp coupon = ICmsHelpService.getById(id);
            return Result.SUCCESS(coupon);
        } catch (Exception e) {
            log.error("查询帮助表明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除帮助表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除帮助表")
    @PreAuthorize("hasAuthority('cms:CmsHelp:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsHelpService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }

}
