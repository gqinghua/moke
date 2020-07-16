package com.moke.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.entity.oms.UmsMemberReceiveAddress;
import com.moke.order.mapper.UmsMemberReceiveAddressMapper;
import com.moke.order.service.IUmsMemberReceiveAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements IUmsMemberReceiveAddressService {

    @Resource
    private UmsMemberReceiveAddressMapper addressMapper;
    @Override
    public UmsMemberReceiveAddress getDefaultItem(Long userId) {
        UmsMemberReceiveAddress q = new UmsMemberReceiveAddress();
        q.setDefaultStatus(1);
        q.setMemberId(userId);
        return this.getOne(new QueryWrapper<>(q));
    }

    @Transactional
    @Override
    public int setDefault(Long id,Long userId) {

        addressMapper.updateStatusByMember(userId);

        UmsMemberReceiveAddress def = new UmsMemberReceiveAddress();
        def.setId(id);
        def.setDefaultStatus(1);
        this.updateById(def);
        return 1;
    }
}
