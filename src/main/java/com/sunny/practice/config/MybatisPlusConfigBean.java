/**
 * FileName: MybatisPlusConfigBean
 * Author:   sunny
 * Date:     2018/9/5 15:47
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description Mybatis-Plus配置
 * @author sunny
 * @create 2018/9/5
 * @since 1.0.0
 */
@Configuration
public class MybatisPlusConfigBean {

    /**
     * @Description: plus 的性能优化
     * @Author: sunny
     * @Date: 2018/9/5 15:48
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
        performanceInterceptor.setMaxTime(1000);
        /*<!--SQL是否格式化 默认false-->*/
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * @Description: mybatis-plus分页插件
     * @Author: sunny
     * @Date: 2018/9/5 15:47
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}