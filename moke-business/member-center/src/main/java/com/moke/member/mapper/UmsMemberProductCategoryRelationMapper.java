package com.moke.member.mapper;

import com.moke.common.entity.ums.UmsMemberProductCategoryRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员与产品分类关系表（用户喜欢的分类） Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Mapper
public interface UmsMemberProductCategoryRelationMapper extends BaseMapper<UmsMemberProductCategoryRelation> {

}
