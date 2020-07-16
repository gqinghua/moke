package com.moke.marking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.common.entity.sms.SmsCouponHistory;
import com.moke.common.vo.SmsCouponHistoryDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Mapper
public interface SmsCouponHistoryMapper extends BaseMapper<SmsCouponHistory> {

    List<SmsCouponHistoryDetail> getDetailList(Long memberId);

}
