package com.moke.marking.service.impl;

import com.moke.common.entity.sms.SmsCouponHistory;
import com.moke.marking.mapper.SmsCouponHistoryMapper;
import com.moke.marking.service.ISmsCouponHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券使用、领取历史表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class SmsCouponHistoryServiceImpl extends ServiceImpl<SmsCouponHistoryMapper, SmsCouponHistory> implements ISmsCouponHistoryService {

}
