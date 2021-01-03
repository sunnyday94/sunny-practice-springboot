/**
 * FileName: LanLimitConfig Author:   sunny Date:     2021/1/4 1:32 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.filter.lanlimit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

import javax.servlet.Filter;

import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br> 
 *
 * @author sunny
 * @create 2021/1/4
 * @since 1.0.0
 */
@Slf4j
public class LanLimitConfig {

    @Value("#{'${filter.custom.lanlimit.urls:/endpoint/*}'.split(',')}")
    private List<String> urls;

    @Value("${filter.custom.lanlimit.order:2}")
    private int order;

    @Autowired
    private LanLimitFilter lanLimitFilter;

    @Bean
    public LanLimitFilter lanLimitFilter() {
        return new LanLimitFilter();
    }

    @Bean
    public FilterRegistrationBean<Filter> lanLimitFilterRegistration(){
        FilterRegistrationBean<Filter> frb= new FilterRegistrationBean<>();
        frb.setFilter(lanLimitFilter);
        frb.setUrlPatterns(urls);
        frb.setOrder(order);
        frb.setName("lanLimitFilter");
        if(log.isDebugEnabled()){
            log.debug(" registration ipLimitFilter,the order is: {},urls is: {}:",order,urls);
        }
        return frb;
    }
}
