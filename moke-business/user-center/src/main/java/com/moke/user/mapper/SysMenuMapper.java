package com.moke.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.common.model.SysPermission;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单
 *
 * @author mall
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysPermission> {

    List<SysPermission> listMenuByUserId(Long id);

    List<SysPermission> listPermissByUserId(Long roleId);

}
