package com.moke.member.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moke.common.annotation.SysLog;
import com.moke.common.entity.ums.SysSchool;
import com.moke.common.entity.ums.UmsMember;
import com.moke.common.entity.ums.UmsMemberBlanceLog;
import com.moke.common.entity.ums.UmsMemberLevel;
import com.moke.common.utils.CommonResult;
import com.moke.common.utils.ValidatorUtils;

import com.moke.member.mapper.UmsMemberBlanceLogMapper;
import com.moke.member.service.ISysSchoolService;
import com.moke.member.service.IUmsMemberLevelService;
import com.moke.member.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Auther: shenzhuan
 * @Date: 2019/4/2 15:02
 * @Description:
 */
@Slf4j
@RestController
@Api(tags = "NotAuthUmsController", description = "关系管理")
@RequestMapping("/notAuth")
public class NotAuthUmsController {
    @Resource
    private UmsMemberBlanceLogMapper memberBlanceLogMapper;
    @Resource
    private IUmsMemberLevelService umsMemberLevelService;
    @Resource
    private IUmsMemberService IUmsMemberService;
    @Resource
    private ISysSchoolService schoolService;
    @SysLog(MODULE = "ums", REMARK = "给会员表分配会员表")
    @ApiOperation("查询会员表明细")
    @GetMapping(value = "/member/detail")
    public Object getUmsMemberById(@RequestParam(value = "username", required = false) String username) {
        try {
            if (ValidatorUtils.empty(username)) {
                return new CommonResult().paramFailed("会员表username");
            }
            UmsMember coupon = IUmsMemberService.getByUsername(username);
            return coupon;
        } catch (Exception e) {
            log.error("查询会员表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @GetMapping(value = "/notAuth/getMemberLevelById")
    public UmsMemberLevel getMemberLevelById(@RequestParam("memberLevelId") Long memberLevelId){
        return umsMemberLevelService.getById(memberLevelId);
    }

    @SysLog(MODULE = "ums", REMARK = "给会员表分配会员表")
    @ApiOperation("查询会员表明细")
    @GetMapping(value = "/getSchoolById")
    SysSchool getSchoolById(@RequestParam("id") Long id){
        return schoolService.getById(id);
    }

    @SysLog(MODULE = "ums", REMARK = "给会员表分配会员表")
    @ApiOperation("查询会员表明细")
    @GetMapping(value = "pageSchool")
    IPage<SysSchool> pageSchool(@RequestParam("entity") SysSchool entity,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum){
        return schoolService.page(new Page<SysSchool>(pageNum, pageSize), new QueryWrapper<>(entity));
    }


    @SysLog(MODULE = "ums", REMARK = "给会员表分配会员表")
    @ApiOperation("查询会员表明细")
    @PostMapping(value = "/updateMember")
    void updateMember(@RequestBody  UmsMember member){
        IUmsMemberService.updateById(member);
    }

    @PostMapping(value = "/notAuth/saveBlanceLog")
    void saveBlanceLog(@RequestBody UmsMemberBlanceLog blog){
        memberBlanceLogMapper.insert(blog);
    }
}
