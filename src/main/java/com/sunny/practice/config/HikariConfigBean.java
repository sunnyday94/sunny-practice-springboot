package com.sunny.practice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @description Hikari数据源配置
 * @author sunny
 * @create 2018/9/5
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class HikariConfigBean extends HikariConfig {

    @Bean
    @ConditionalOnClass(HikariDataSource.class)
    @ConditionalOnProperty(name = "spring.datasource", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource(this);
        return dataSource;
    }

}
