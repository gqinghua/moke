package com.moke.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.cms.CmsPrefrenceArea;
import com.moke.cms.service.ICmsPrefrenceAreaService;
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
 * 优选专区
 * </p>
 *
 * @author
 * @since
 */
@Slf4j
@RestController
@Api(tags = "CmsPrefrenceAreaController", description = "优选专区管理")
@RequestMapping("/cms/CmsPrefrenceArea")
public class CmsPrefrenceAreaController {

    @Resource
    private ICmsPrefrenceAreaService ICmsPrefrenceAreaService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有优选专区列表")
    @ApiOperation("根据条件查询所有优选专区列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsPrefrenceArea:read')")
    public Result getCmsPrefrenceAreaByPage(CmsPrefrenceArea entity,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return Result.SUCCESS(ICmsPrefrenceAreaService.page(new Page<CmsPrefrenceArea>(pageNum, pageSize), new QueryWrapper<>(entity)),"查询成功");
        } catch (Exception e) {
            log.error("根据条件查询所有优选专区列表：%s", e.getMessage(), e);
        }
        return Result.failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存优选专区")
    @ApiOperation("保存优选专区")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsPrefrenceArea:create')")
    public Result saveCmsPrefrenceArea(@RequestBody CmsPrefrenceArea entity) {
        try {
            if (ICmsPrefrenceAreaService.save(entity)) {
                return Result.SUCCESS("保存成功");
            }
        } catch (Exception e) {
            log.error("保存优选专区：%s", e.getMessage(), e);
            return Result.failed("保存失败");
        }
        return Result.failed("保存失败");
    }

    @SysLog(MODULE = "cms", REMARK = "更新优选专区")
    @ApiOperation("更新优选专区")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsPrefrenceArea:update')")
    public Result updateCmsPrefrenceArea(@RequestBody CmsPrefrenceArea entity) {
        try {
            if (ICmsPrefrenceAreaService.updateById(entity)) {
                return Result.SUCCESS("更新成功");
            }
        } catch (Exception e) {
            log.error("更新优选专区：%s", e.getMessage(), e);
            return Result.failed("更新失败");
        }
        return Result.failed("更新失败");
    }

    @SysLog(MODULE = "cms", REMARK = "删除优选专区")
    @ApiOperation("删除优选专区")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsPrefrenceArea:delete')")
    public Result deleteCmsPrefrenceArea(@ApiParam("优选专区id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("优选专区id");
            }
            if (ICmsPrefrenceAreaService.removeById(id)) {
                return Result.SUCCESS("删除成功");
            }
        } catch (Exception e) {
            log.error("删除优选专区：%s", e.getMessage(), e);
            return Result.failed("删除失败");
        }
        return Result.failed("删除失败");
    }

    @SysLog(MODULE = "cms", REMARK = "给优选专区分配优选专区")
    @ApiOperation("查询优选专区明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsPrefrenceArea:read')")
    public Result getCmsPrefrenceAreaById(@ApiParam("优选专区id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return Result.failed("优选专区id");
            }
            CmsPrefrenceArea coupon = ICmsPrefrenceAreaService.getById(id);
            return Result.SUCCESS(coupon,"查询成功");
        } catch (Exception e) {
            log.error("查询优选专区明细：%s", e.getMessage(), e);
            return Result.failed("查询失败");
        }

    }

    @ApiOperation(value = "批量删除优选专区")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除优选专区")
    @PreAuthorize("hasAuthority('cms:CmsPrefrenceArea:delete')")
    public Result deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsPrefrenceAreaService.removeByIds(ids);
        if (count) {
            return Result.SUCCESS(count,"批量删除成功");
        } else {
            return Result.failed("批量删除成功");
        }
    }

}
