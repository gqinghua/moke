package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.cms.service.ISmsHomeRecommendSubjectService;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.SmsHomeRecommendSubject;
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
 * 首页推荐专题表
 * </p>
 *
 * @author
 * @since
 */
@Slf4j
@RestController
@Api(tags = "SmsHomeRecommendSubjectController", description = "首页推荐专题表管理")
@RequestMapping("/marking/SmsHomeRecommendSubject")
public class SmsHomeRecommendSubjectController {

    @Resource
    private ISmsHomeRecommendSubjectService ISmsHomeRecommendSubjectService;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有首页推荐专题表列表")
    @ApiOperation("根据条件查询所有首页推荐专题表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendSubject:read')")
    public Result getSmsHomeRecommendSubjectByPage(SmsHomeRecommendSubject entity,
                                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ISmsHomeRecommendSubjectService.page(new Page<SmsHomeRecommendSubject>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有首页推荐专题表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }



    @SysLog(MODULE = "sms", REMARK = "更新首页推荐专题表")
    @ApiOperation("更新首页推荐专题表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendSubject:update')")
    public Result updateSmsHomeRecommendSubject(@RequestBody SmsHomeRecommendSubject entity) {
        try {
            if (ISmsHomeRecommendSubjectService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新首页推荐专题表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除首页推荐专题表")
    @ApiOperation("删除首页推荐专题表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendSubject:delete')")
    public Result deleteSmsHomeRecommendSubject(@ApiParam("首页推荐专题表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("首页推荐专题表id");
            }
            if (ISmsHomeRecommendSubjectService.removeById(id)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("删除首页推荐专题表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给首页推荐专题表分配首页推荐专题表")
    @ApiOperation("查询首页推荐专题表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendSubject:read')")
    public Result getSmsHomeRecommendSubjectById(@ApiParam("首页推荐专题表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("首页推荐专题表id");
            }
            SmsHomeRecommendSubject coupon = ISmsHomeRecommendSubjectService.getById(id);
            return Result.SUCCESS(coupon);
        } catch (Exception e) {
            log.error("查询首页推荐专题表明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除首页推荐专题表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除首页推荐专题表")
    @PreAuthorize("hasAuthority('marking:SmsHomeRecommendSubject:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsHomeRecommendSubjectService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count);
        } else {
            return Result.failed();
        }
    }
    @ApiOperation("添加首页推荐专题")
    @PostMapping(value = "/create")
    @ResponseBody
    public Result create(@RequestBody List<SmsHomeRecommendSubject> homeBrandList) {
        boolean count = ISmsHomeRecommendSubjectService.saveBatch(homeBrandList);
        if (count ) {
            return Result.SUCCESS(count);
        }
        return Result.failed();
    }

    @ApiOperation("修改推荐排序")
    @RequestMapping(value = "/update/sort/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result updateSort(@PathVariable Long id, Integer sort) {
        int count = ISmsHomeRecommendSubjectService.updateSort(id, sort);
        if (count > 0) {
            return Result.SUCCESS(count);
        }
        return Result.failed();
    }
    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public Result updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = ISmsHomeRecommendSubjectService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return Result.SUCCESS(count);
        }
        return Result.failed();
    }
}
