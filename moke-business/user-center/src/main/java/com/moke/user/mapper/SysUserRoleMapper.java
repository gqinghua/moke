package com.moke.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.common.model.SysRoleUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台用户和角色关系表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysRoleUser> {



}
