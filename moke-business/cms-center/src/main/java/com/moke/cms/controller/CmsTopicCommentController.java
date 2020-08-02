package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsTopicComment;
import com.moke.cms.service.ICmsTopicCommentService;
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
 * 专题评论表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "CmsTopicCommentController", description = "专题评论表管理")
@RequestMapping("/cms/CmsTopicComment")
public class CmsTopicCommentController {

    @Resource
    private ICmsTopicCommentService ICmsTopicCommentService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有专题评论表列表")
    @ApiOperation("根据条件查询所有专题评论表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsTopicComment:read')")
    public Result getCmsTopicCommentByPage(CmsTopicComment entity,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ICmsTopicCommentService.page(new Page<CmsTopicComment>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有专题评论表列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存专题评论表")
    @ApiOperation("保存专题评论表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsTopicComment:create')")
    public Result saveCmsTopicComment(@RequestBody CmsTopicComment entity) {
        try {
            if (ICmsTopicCommentService.save(entity)) {
                return Result.SUCCESS();
            }
        } catch (Exception e) {
            log.error("保存专题评论表：%s", e.getMessage(), e);
            return Result.failed();
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新专题评论表")
    @ApiOperation("更新专题评论表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsTopicComment:update')")
    public Result updateCmsTopicComment(@RequestBody CmsTopicComment entity) {
        try {
            if (ICmsTopicCommentService.updateById(entity)) {
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
    @PreAuthorize("hasAuthority('cms:CmsTopicComment:delete')")
    public Result deleteCmsTopicComment(@ApiParam("专题评论表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("专题评论表id");
            }
            if (ICmsTopicCommentService.removeById(id)) {
                return Result.SUCCESS("删除成功");
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
    @PreAuthorize("hasAuthority('cms:CmsTopicComment:read')")
    public Result getCmsTopicCommentById(@ApiParam("专题评论表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("专题评论表id");
            }
            CmsTopicComment coupon = ICmsTopicCommentService.getById(id);
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
    @PreAuthorize("hasAuthority('cms:CmsTopicComment:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsTopicCommentService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
