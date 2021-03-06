package com.sunny.practice;

import com.sunny.practice.annotation.EnableSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @Description: 启动类
 * @Author: sunny
 * @Date: 2018/9/5 14:20
 */
@SpringBootApplication(scanBasePackages = {"com.sunny.practice"},
        exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableSwagger
@MapperScan(basePackages={"com.sunny.practice.dao.mybatis.mapper"})  //扫描mapper文件
public class SunnyPracticeSpringbootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SunnyPracticeSpringbootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SunnyPracticeSpringbootApplication.class);
    }
}
