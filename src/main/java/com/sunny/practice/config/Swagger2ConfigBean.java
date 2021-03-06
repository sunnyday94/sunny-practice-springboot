/**
 * FileName: Swagger2Config
 * Author:   sunny
 * Date:     2018/8/24 17:41
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description Swagger2配置
 * @author sunny
 * @create 2018/9/5
 * @since 1.0.0
 */
@Slf4j
public class Swagger2ConfigBean {

    @Value("${swagger2.basePackage:com.sunny.practice.controller}")
    private String basePackage;

    @Value("${swagger2.title:springboot}")
    private String title;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description("sunny-practice-springboot")
                .contact(new Contact("sunny", "", "sunnyday940908@163.com"))
                .version("1.0")
                .build();
    }
}
