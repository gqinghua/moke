package com.moke.log.dao;

import com.moke.log.model.SysLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author mall
 */
@Component
public interface SysLogDao extends ElasticsearchRepository<SysLog, String> {

}