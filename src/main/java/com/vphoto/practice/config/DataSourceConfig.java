package com.vphoto.practice.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

		/**
		 * @Description: 数据源
		 * @Author: sunny
		 * @Date: 2018/8/24 15:23
		 */
	 	@Bean
	    @ConfigurationProperties(prefix = "spring.datasource")
	    public DataSource dataSource() {
	        return new com.alibaba.druid.pool.DruidDataSource();
	    }
	    
	    /**
	     * @Description: 事务管理
	     * @Author: sunny
	     * @Date: 2018/8/24 15:23
	     */
	    @Bean
	    public PlatformTransactionManager transactionManager() {
	        return new DataSourceTransactionManager(dataSource());
	    }

}
