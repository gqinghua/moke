package com.moke.goods.mapper;

import com.moke.common.entity.pms.PmsProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.goods.vo.PmsProductCategoryWithChildrenItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Mapper
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
