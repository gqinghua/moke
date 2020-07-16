package com.moke.order.service.impl;

import com.moke.common.entity.oms.OmsOrderSetting;
import com.moke.order.mapper.OmsOrderSettingMapper;
import com.moke.order.service.IOmsOrderSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单设置表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Service
public class OmsOrderSettingServiceImpl extends ServiceImpl<OmsOrderSettingMapper, OmsOrderSetting> implements IOmsOrderSettingService {

}
