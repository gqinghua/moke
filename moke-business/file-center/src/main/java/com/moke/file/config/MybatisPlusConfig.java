package com.moke.file.config;

import com.moke.db.config.DefaultMybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @date
 */
@Configuration
@MapperScan({"com.mallplus.file.mapper*"})
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {

}
