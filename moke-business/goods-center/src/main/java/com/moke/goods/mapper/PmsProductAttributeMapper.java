package com.moke.goods.mapper;

import com.moke.common.entity.pms.PmsProductAttribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.goods.vo.ProductAttrInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Mapper
public interface PmsProductAttributeMapper extends BaseMapper<PmsProductAttribute> {

    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
