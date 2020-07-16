package com.moke.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.constant.CommonConstant;
import com.moke.common.lock.DistributedLock;
import com.moke.common.model.SysRole;
import com.moke.common.model.SysRoleMenu;
import com.moke.user.mapper.SysRoleMapper;
import com.moke.user.mapper.SysRolePermissionMapper;
import com.moke.user.mapper.SysUserRoleMapper;
import com.moke.user.service.ISysRolePermissionService;
import com.moke.user.service.ISysRoleService;
import com.moke.user.service.ISysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    private final static String LOCK_KEY_ROLECODE = CommonConstant.LOCK_KEY_PREFIX+"rolecode:";
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysRolePermissionMapper roleMenuMapper;
    @Resource
    private ISysUserRoleService userRoleService;
    @Resource
    private ISysRolePermissionService rolePermissionService;


    @Autowired
    private DistributedLock lock;

    @Override
    public List<SysRoleMenu> getRolePermission(Long roleId) {
        return roleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
    }
    @Transactional
    @Override
    public boolean saves(SysRole role) {
    //    role.setCreateTime(new Date());

        roleMapper.insert(role);
        updatePermission(role.getId(),role.getMenuIds());
        return true;
    }

    @Transactional
    @Override
    public boolean updates( SysRole role) {
        role.setId(role.getId());
        updatePermission(role.getId(),role.getMenuIds());
        roleMapper.updateById(role);
        return true;
    }
    public void updatePermission(Long roleId, String permissionIds) {

        //先删除原有关系
        roleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
        //批量插入新关系
        List<SysRoleMenu> relationList = new ArrayList<>();
        if (!StringUtils.isEmpty(permissionIds)){
            String[] mids = permissionIds.split(",");
            for (String permissionId : mids) {
                SysRoleMenu relation = new SysRoleMenu();
                relation.setRoleId(roleId);
                relation.setMenuId(Long.valueOf(permissionId));
                relationList.add(relation);
            }
            rolePermissionService.saveBatch(relationList);
        }

    }
}
