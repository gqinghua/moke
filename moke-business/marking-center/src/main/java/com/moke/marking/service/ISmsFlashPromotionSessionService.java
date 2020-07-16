package com.moke.marking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moke.common.entity.sms.SmsFlashPromotionSession;
import com.moke.marking.vo.SmsFlashPromotionSessionDetail;

import java.util.List;

/**
 * <p>
 * 限时购场次表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface ISmsFlashPromotionSessionService extends IService<SmsFlashPromotionSession> {

    /**
     * 修改场次启用状态
     */
    int updateStatus(Long id, Integer status);

    /**
     * 获取全部可选场次及其数量
     */
    List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);
}
