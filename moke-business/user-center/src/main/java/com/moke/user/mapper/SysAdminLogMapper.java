package com.moke.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moke.common.model.SysAdminLog;
import com.moke.common.vo.LogParam;
import com.moke.common.vo.LogStatisc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Mapper
public interface SysAdminLogMapper extends BaseMapper<SysAdminLog> {

   List<LogStatisc> getLogStatisc(LogParam entity);


}
