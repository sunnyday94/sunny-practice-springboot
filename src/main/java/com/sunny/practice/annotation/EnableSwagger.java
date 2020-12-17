package com.sunny.practice.annotation;


import com.github.xiaoymin.swaggerbootstrapui.configuration.MarkdownFileConfiguration;
import com.github.xiaoymin.swaggerbootstrapui.configuration.SecurityConfiguration;
import com.github.xiaoymin.swaggerbootstrapui.configuration.SwaggerBootstrapUIConfiguration;
import com.sunny.practice.config.Swagger2ConfigBean;
import org.springframework.context.annotation.Import;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({Swagger2DocumentationConfiguration.class, Swagger2ConfigBean.class, SwaggerBootstrapUIConfiguration.class, SecurityConfiguration.class, MarkdownFileConfiguration.class})
public @interface EnableSwagger {
}
