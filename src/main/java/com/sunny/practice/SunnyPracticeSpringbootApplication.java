package com.sunny.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.sunny.practice"},
        exclude = {DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableSwagger2
public class SunnyPracticeSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunnyPracticeSpringbootApplication.class, args);
    }
}
