package com.moke.marking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.common.entity.sms.SmsDraw;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 一分钱抽奖 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-10-17
 */
@Mapper
public interface SmsDrawMapper extends BaseMapper<SmsDraw> {

}
