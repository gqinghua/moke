package com.moke.goods.mapper;

import com.moke.common.entity.pms.PmsProductAttributeCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.goods.vo.PmsProductAttributeCategoryItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Mapper
public interface PmsProductAttributeCategoryMapper extends BaseMapper<PmsProductAttributeCategory> {

    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
