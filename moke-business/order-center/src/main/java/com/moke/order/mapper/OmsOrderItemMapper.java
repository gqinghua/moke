package com.moke.order.mapper;

import com.moke.common.entity.oms.OmsOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单中所包含的商品 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Mapper
public interface OmsOrderItemMapper extends BaseMapper<OmsOrderItem> {

}
