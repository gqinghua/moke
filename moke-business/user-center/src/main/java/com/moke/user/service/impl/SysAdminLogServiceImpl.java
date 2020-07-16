package com.moke.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.model.SysAdminLog;
import com.moke.common.vo.LogParam;
import com.moke.common.vo.LogStatisc;
import com.moke.user.mapper.SysAdminLogMapper;
import com.moke.user.service.ISysAdminLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Service
public class SysAdminLogServiceImpl extends ServiceImpl<SysAdminLogMapper, SysAdminLog> implements ISysAdminLogService {

    @Resource
    private SysAdminLogMapper logMapper;
    @Override
    public List<LogStatisc> selectPageExt(LogParam entity) {
        return logMapper.getLogStatisc(entity);
    }
}
