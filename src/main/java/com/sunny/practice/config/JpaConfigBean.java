/**
 * FileName: JpaConfigBean
 * Author:   sunny
 * Date:     2018/9/5 16:42
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * @description Jpa配置类
 * @author sunny
 * @create 2018/9/5
 * @since 1.0.0
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class JpaConfigBean {

    /**
     *
     */
    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;


    @Value("${hibernate.packages.scan}")
    private String packagesToScan;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(packagesToScan);
        factory.setDataSource(dataSource);


        Properties pro = new Properties();
        for (Map.Entry<String, String> e : getVendorProperties(dataSource).entrySet()) {

            if (!e.getKey().contains("hbm2ddl"))
                pro.setProperty(e.getKey(), e.getValue());
            System.out.println(">>>>" + e.getKey() + "==" + e.getValue());
        }
        System.out.println(">>>>hibernate.hbm2ddl.auto=" + hbm2ddlAuto);
        System.out.println(">>>>hibernate.packages.scan=" + packagesToScan);
        //原因：两种不同风格的字段生成格式影响数据查询与保存，这里设置。只保留一种风格
        pro.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        factory.setJpaProperties(pro);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        jpaProperties.determineDatabase(dataSource);
        return jpaProperties.getProperties();
    }
}
