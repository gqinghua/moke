package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.cms.service.ICmsTopicMemberService;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsTopicMember;
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
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "CmsTopicMemberController", description = "管理")
@RequestMapping("/cms/CmsTopicMember")
public class CmsTopicMemberController {

    @Resource
    private ICmsTopicMemberService ICmsTopicMemberService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有列表")
    @ApiOperation("根据条件查询所有列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsTopicMember:read')")
    public Result getCmsTopicMemberByPage(CmsTopicMember entity,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ICmsTopicMemberService.page(new Page<CmsTopicMember>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存")
    @ApiOperation("保存")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsTopicMember:create')")
    public Result saveCmsTopicMember(@RequestBody CmsTopicMember entity) {
        try {
            if (ICmsTopicMemberService.save(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("保存：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新")
    @ApiOperation("更新")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopicMember:update')")
    public Result updateCmsTopicMember(@RequestBody CmsTopicMember entity) {
        try {
            if (ICmsTopicMemberService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除")
    @ApiOperation("删除")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopicMember:delete')")
    public Result deleteCmsTopicMember(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("id");
            }
            if (ICmsTopicMemberService.removeById(id)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("删除：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给分配")
    @ApiOperation("查询明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopicMember:read')")
    public Result getCmsTopicMemberById(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("id");
            }
            CmsTopicMember coupon = ICmsTopicMemberService.getById(id);
            return Result.SUCCESS(coupon);
        } catch (Exception e) {
            log.error("查询明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除")
    @PreAuthorize("hasAuthority('cms:CmsTopicMember:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsTopicMemberService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }

}
