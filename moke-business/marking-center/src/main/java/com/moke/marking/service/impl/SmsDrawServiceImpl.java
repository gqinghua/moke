package com.moke.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.entity.sms.SmsDraw;
import com.moke.marking.mapper.SmsDrawMapper;
import com.moke.marking.service.ISmsDrawService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 一分钱抽奖 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-10-17
 */
@Service
public class SmsDrawServiceImpl extends ServiceImpl<SmsDrawMapper, SmsDraw> implements ISmsDrawService {

}
