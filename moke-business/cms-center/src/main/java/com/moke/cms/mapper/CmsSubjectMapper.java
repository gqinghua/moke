package com.moke.cms.mapper;

import com.moke.common.entity.cms.CmsSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 专题表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Mapper
public interface CmsSubjectMapper extends BaseMapper<CmsSubject> {

    int countByToday(Long id);
}
