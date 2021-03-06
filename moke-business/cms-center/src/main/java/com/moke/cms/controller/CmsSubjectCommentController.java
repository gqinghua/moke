package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsSubjectComment;
import com.moke.cms.service.ICmsSubjectCommentService;
import com.moke.common.utils.CommonResult;
import com.moke.common.utils.ValidatorUtils;
import com.moke.log.monitor.PointUtil;
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
 * 专题评论表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "CmsSubjectCommentController", description = "专题评论表管理")
@RequestMapping("/cms/CmsSubjectComment")
public class CmsSubjectCommentController {

    @Resource
    private ICmsSubjectCommentService ICmsSubjectCommentService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有专题评论表列表")
    @ApiOperation("根据条件查询所有专题评论表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsSubjectComment:read')")
    public Result getCmsSubjectCommentByPage(CmsSubjectComment entity,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            IPage<CmsSubjectComment> create_time = ICmsSubjectCommentService.page(new Page<CmsSubjectComment>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time"));
            PointUtil.info("5L","SS","SS");
            return Result.SUCCESS(create_time);
        } catch (Exception e) {
            log.error("根据条件查询所有专题评论表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存专题评论表")
    @ApiOperation("保存专题评论表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsSubjectComment:create')")
    public Object saveCmsSubjectComment(@RequestBody CmsSubjectComment entity) {
        try {
            if (ICmsSubjectCommentService.save(entity)) {
                return Result.SUCCESS("查询成功");
            }
        } catch (Exception e) {
            log.error("保存专题评论表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新专题评论表")
    @ApiOperation("更新专题评论表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectComment:update')")
    public Result updateCmsSubjectComment(@RequestBody CmsSubjectComment entity) {
        try {
            if (ICmsSubjectCommentService.updateById(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("更新专题评论表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除专题评论表")
    @ApiOperation("删除专题评论表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectComment:delete')")
    public Result deleteCmsSubjectComment(@ApiParam("专题评论表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("专题评论表id");
            }
            if (ICmsSubjectCommentService.removeById(id)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("删除专题评论表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给专题评论表分配专题评论表")
    @ApiOperation("查询专题评论表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectComment:read')")
    public Result getCmsSubjectCommentById(@ApiParam("专题评论表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("专题评论表id");
            }
            CmsSubjectComment coupon = ICmsSubjectCommentService.getById(id);
            return Result.SUCCESS(coupon);
        } catch (Exception e) {
            log.error("查询专题评论表明细：%s", e.getMessage(), e);
            return Result.failed();
        }

    }

    @ApiOperation(value = "批量删除专题评论表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除专题评论表")
    @PreAuthorize("hasAuthority('cms:CmsSubjectComment:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsSubjectCommentService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count,"删除成功");
        } else {
            return Result.failed();
        }
    }

}
