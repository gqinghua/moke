package com.moke.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.common.entity.pms.PmsSkuStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * sku的库存 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Mapper
public interface PmsSkuStockMapper extends BaseMapper<PmsSkuStock> {
    /**
     * 批量插入或替换操作
     */
    int replaceList(@Param("list") List<PmsSkuStock> skuStockList);
}
