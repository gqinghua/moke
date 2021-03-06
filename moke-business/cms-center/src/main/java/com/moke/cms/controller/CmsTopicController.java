package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.cms.service.ICmsTopicMemberService;
import com.moke.cms.service.ICmsTopicService;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsTopic;
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
 * 话题表
 * </p>
 *
 * @author
 * @since
 */
@Slf4j
@RestController
@Api(tags = "CmsTopicController", description = "话题表管理")
@RequestMapping("/cms/CmsTopic")
public class CmsTopicController {

    @Resource
    private ICmsTopicService ICmsTopicService;
    @Resource
    private ICmsTopicMemberService topicMemberService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有话题表列表")
    @ApiOperation("根据条件查询所有话题表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsTopic:read')")
    public Result getCmsTopicByPage(CmsTopic entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ICmsTopicService.page(new Page<CmsTopic>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有话题表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存话题表")
    @ApiOperation("保存话题表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsTopic:create')")
    public Result saveCmsTopic(@RequestBody CmsTopic entity) {
        try {
            if (ICmsTopicService.saves(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("保存话题表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新话题表")
    @ApiOperation("更新话题表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopic:update')")
    public Result updateCmsTopic(@RequestBody CmsTopic entity) {
        try {
            if (ICmsTopicService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新话题表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除话题表")
    @ApiOperation("删除话题表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopic:delete')")
    public Result deleteCmsTopic(@ApiParam("话题表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("话题表id");
            }
            if (ICmsTopicService.removeById(id)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("删除话题表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给话题表分配话题表")
    @ApiOperation("查询话题表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopic:read')")
    public Result getCmsTopicById(@ApiParam("话题表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("话题表id");
            }
            CmsTopic coupon = ICmsTopicService.getById(id);
            return Result.SUCCESS(coupon,"coupon");
        } catch (Exception e) {
            log.error("查询话题表明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除话题表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除话题表")
    @PreAuthorize("hasAuthority('cms:CmsTopic:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsTopicService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }

    @ApiOperation("修改审核状态")
    @RequestMapping(value = "/update/verifyStatus")
    @ResponseBody
    @SysLog(MODULE = "cms", REMARK = "修改审核状态")
    public Result updateVerifyStatus(@RequestParam("ids") Long ids,
                                     @RequestParam("topicId") Long topicId,
                                     @RequestParam("verifyStatus") Integer verifyStatus) {

        int count = ICmsTopicService.updateVerifyStatus(ids,topicId,verifyStatus);
        if (count > 0) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }

    @ApiOperation("根据活动id获取活动参与人员信息")
    @RequestMapping(value = "/fetchCmsTopicMember/{id}", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "cms", REMARK = "根据活动id获取活动参与人员信息")
    public Result fetchCmsTopicMember(@PathVariable Long id) {
        CmsTopicMember member = new CmsTopicMember();
        member.setTopicId(id);
        List<CmsTopicMember> list = topicMemberService.list(new QueryWrapper<>(member));
        return Result.SUCCESS(list);
    }
}
