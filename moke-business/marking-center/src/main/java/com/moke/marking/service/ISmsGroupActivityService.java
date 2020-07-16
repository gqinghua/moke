package com.moke.marking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moke.common.entity.sms.SmsGroupActivity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-10-12
 */
public interface ISmsGroupActivityService extends IService<SmsGroupActivity> {

    int updateShowStatus(Long ids, Integer status);
}
