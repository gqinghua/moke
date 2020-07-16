package com.moke.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.common.entity.oms.UmsMemberReceiveAddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员收货地址表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Mapper
public interface UmsMemberReceiveAddressMapper extends BaseMapper<UmsMemberReceiveAddress> {

    void updateStatusByMember(Long memberId);
}
