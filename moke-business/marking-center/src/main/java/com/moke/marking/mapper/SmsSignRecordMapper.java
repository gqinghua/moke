package com.moke.marking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.common.entity.sms.SmsSignRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 签到记录 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-10-17
 */
@Mapper
public interface SmsSignRecordMapper extends BaseMapper<SmsSignRecord> {

}
