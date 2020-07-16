package com.moke.log.dao;

import com.moke.log.model.SlowQueryLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author mall
 */
@Component
public interface SlowQueryLogDao extends ElasticsearchRepository<SlowQueryLog, String> {

}