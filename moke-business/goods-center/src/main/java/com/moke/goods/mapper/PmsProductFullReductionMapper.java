package com.moke.goods.mapper;

import com.moke.common.entity.pms.PmsProductFullReduction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 产品满减表(只针对同商品) Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Mapper
public interface PmsProductFullReductionMapper extends BaseMapper<PmsProductFullReduction> {

}
