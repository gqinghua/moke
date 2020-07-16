package com.moke.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moke.common.entity.oms.OmsOrderReturnApply;
import com.moke.order.vo.OmsUpdateStatusParam;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface IOmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {
    /**
     * 修改申请状态
     */
    int updateStatus(Long id, OmsUpdateStatusParam statusParam);
}
