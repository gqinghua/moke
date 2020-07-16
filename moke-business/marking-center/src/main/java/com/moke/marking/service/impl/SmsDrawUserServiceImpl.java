package com.moke.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.entity.sms.SmsDrawUser;
import com.moke.marking.mapper.SmsDrawUserMapper;
import com.moke.marking.service.ISmsDrawUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 抽奖与用户关联表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-10-17
 */
@Service
public class SmsDrawUserServiceImpl extends ServiceImpl<SmsDrawUserMapper, SmsDrawUser> implements ISmsDrawUserService {

}
