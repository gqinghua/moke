package com.moke.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.entity.sms.SmsSignRecord;
import com.moke.marking.mapper.SmsSignRecordMapper;
import com.moke.marking.service.ISmsSignRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 签到记录 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-10-17
 */
@Service
public class SmsSignRecordServiceImpl extends ServiceImpl<SmsSignRecordMapper, SmsSignRecord> implements ISmsSignRecordService {

}
