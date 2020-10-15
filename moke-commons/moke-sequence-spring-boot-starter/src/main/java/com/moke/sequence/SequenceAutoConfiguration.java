package com.moke.sequence;


import com.moke.sequence.builder.DbSeqBuilder;
import com.moke.sequence.builder.RedisSeqBuilder;
import com.moke.sequence.builder.SnowflakeSeqBuilder;
import com.moke.sequence.properties.SequenceDbProperties;
import com.moke.sequence.properties.SequenceRedisProperties;
import com.moke.sequence.properties.SequenceSnowflakeProperties;
import com.moke.sequence.range.impl.name.DateBizName;
import com.moke.sequence.range.impl.name.DefaultBizName;
import com.moke.sequence.sequence.Sequence;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lengleng
 * @date 2019-05-26
 */
@Configuration
@ComponentScan("com.pig4cloud.common.sequence")
@ConditionalOnMissingBean(Sequence.class)
public class SequenceAutoConfiguration {

	/**
	 * 数据库作为发号器的存储介质
	 *
	 * @param dataSource
	 * @param properties
	 * @return
	 */
	@Bean
	@ConditionalOnBean(DataSource.class)
	@ConditionalOnProperty("xsequence.db")
	public Sequence dbSequence(DataSource dataSource,
							   SequenceDbProperties properties) {
		return DbSeqBuilder
				.create()
				.bizName(new DefaultBizName(properties.getBizName()))
				.dataSource(dataSource)
				.step(properties.getStep())
				.retryTimes(properties.getRetryTimes())
				.tableName(properties.getTableName())
				.build();
	}

	/**
	 * Redis 作为发号器的存储介质
	 *
	 * @param redisProperties
	 * @param properties
	 * @return
	 */
	@Bean
	@ConditionalOnProperty("xsequence.redis")
	public Sequence redisSequence(RedisProperties redisProperties,
								  SequenceRedisProperties properties) {
		return RedisSeqBuilder
				.create()
				.bizName(new DateBizName(properties.getBizName()))
				.ip(redisProperties.getHost())
				.port(redisProperties.getPort())
				.auth(redisProperties.getPassword())
				.step(properties.getStep())
				.build();
	}

	/**
	 * snowflak 算法作为发号器实现
	 *
	 * @param properties
	 * @return
	 */
	@Bean
	@ConditionalOnProperty("xsequence.snowflak")
	public Sequence snowflakeSequence(SequenceSnowflakeProperties properties) {
		return SnowflakeSeqBuilder
				.create()
				.datacenterId(properties.getDatacenterId())
				.workerId(properties.getWorkerId())
				.build();
	}
}