/**
 * FileName: Swagger2Config
 * Author:   sunny
 * Date:     2018/8/24 17:41
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.vphoto.practice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description: swagger2配置
 * @Author: sunny
 * @Date: 2018/8/24 17:42
 */
@Slf4j
@Configuration
@EnableSwagger2
public class Swagger2Config {
    public Swagger2Config() {
        log.info("=================加载Swagger2Config================");
    }

    @Value("${swagger2.package}")
    private String basePackage;

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
                .title("测试用例管理API")
                .description("测试用例管理API")
                .contact(new Contact("sunny", "", "sunny.wang@v.photos"))
                .version("1.0")
                .build();
    }
}
