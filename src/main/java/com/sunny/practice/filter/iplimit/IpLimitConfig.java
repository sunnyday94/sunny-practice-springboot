/**
 * FileName: IpLimitConfig Author:   sunny Date:     2021/1/4 1:04 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.filter.iplimit;

import com.google.common.collect.ImmutableMap;

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
public class IpLimitConfig {

    /**
     * 工作模式，白名单，黑名单
     * 白名单：白名单模式，只有指定IP可以访问，无时间限制
     * 黑名单：默认可以访问，只有指定IP不可以访问，可以指定时间。
     * 只能二选一。
     */
    @Value("${filter.custom.iplimit.useWhiteList:false}")
    private boolean useWhiteList=false;

    /**
     * 指定的时间,访问周期
     * 单位为秒。
     */
    @Value("${filter.custom.iplimit.interval:10}")
    private int interval=30;

    /**
     * 指定时间内， 所能访问的次数
     */
    @Value("${filter.custom.iplimit.frequency:10}")
    private int frequency=10;


    @Value("#{'${filter.custom.iplimit.urls:/*}'.split(',')}")
    private List<String> urls;


    @Value("${filter.custom.iplimit.order:1}")
    private int order;


    @Autowired
    private IpLimitFilter ipLimitFilter;

    @Bean
    public IpLimitFilter ipLimitFilter() {
        return new IpLimitFilter();
    }

    @Bean
    public FilterRegistrationBean<Filter> reqIDFilterRegistration(){
        FilterRegistrationBean<Filter> frb= new FilterRegistrationBean<>();
        frb.setFilter(ipLimitFilter);
        frb.setUrlPatterns(urls);
        frb.setOrder(order);
        frb.setName("ipLimitFilter");
        frb.setInitParameters(ImmutableMap.of(
                "useWhiteList",String.valueOf(useWhiteList),
                "interval",String.valueOf(interval),
                "frequency",String.valueOf(frequency)
        ));
        if(log.isDebugEnabled()){
            log.debug(" registration ipLimitFilter,the order is: {},urls is: {}:",order,urls);
        }
        return frb;
    }

}
