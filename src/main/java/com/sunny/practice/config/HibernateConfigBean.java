/**
 * @author :sunny
 * @description: Hibernate配置类
 */
package com.sunny.practice.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

//@Configuration()
@EnableJpaRepositories(
//		entityManagerFactoryRef="entityManagerFactoryPrimary"
//		transactionManagerRef="hibernateTransactionManager"//一个源时屏掉 用默认事务名transactionManager
		)//设置dao（repo）所在位置
//@EnableJpaRepositories("com.vphoto.*.dao")//Jpa时用		
public class HibernateConfigBean {
	
	private static Log log=LogFactory.getLog(HibernateConfigBean.class);
	
    @Autowired
    private JpaProperties jpaProperties;

    @Autowired 
    @Qualifier("dataSource")
    private DataSource dataSource;
    
    @Value("${hibernate.packages.scan:com.vphoto.practice.entity}")
    private String packagesToScan;
    
    @Value("${hibernate.hbm2ddl.auto:none}")
    private String hbm2ddlAuto;
    
    /**手动装载Jpa资源
     * @return
     */
//    @Bean
//    public JpaProperties jpaProperties(){
//    	return new JpaProperties();
//    }


    private Map<String, String> getVendorProperties(DataSource dataSource) {
    	jpaProperties.determineDatabase(dataSource);
    	
//    	jpaProperties.getHibernateProperties((HibernateSettings) dataSource);
        return jpaProperties.getProperties();
    }
    
    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager){
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }
    /**直接用Hibernate5 sessionFactory
     * @return
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
    	LocalSessionFactoryBean bean=new LocalSessionFactoryBean();
    	bean.setDataSource(dataSource);
    	Properties pro=new Properties();

    	for(Entry<String,String> e:getVendorProperties(dataSource).entrySet()){
    		
    		if(e.getKey().indexOf("hbm2ddl")==-1)
    			pro.setProperty(e.getKey(), e.getValue());
    		System.out.println(">>>>"+e.getKey()+"=="+ e.getValue());
    	}
    	System.out.println(">>>>hibernate.hbm2ddl.auto="+hbm2ddlAuto);
    	System.out.println(">>>>hibernate.packages.scan="+packagesToScan);
    	//原因：两种不同风格的字段生成格式影响数据查询与保存，这里设置。只保留一种风格
    	pro.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
    	bean.setImplicitNamingStrategy(new ImplicitNamingStrategyJpaCompliantImpl());//H5的格式
    	bean.setPhysicalNamingStrategy(new PhysicalNamingStrategyStandardImpl());//H5的格式
//    	setNamingStrategy(ImprovedNamingStrategy.INSTANCE);//H4的格式
    	bean.setHibernateProperties(pro);
    	bean.setPackagesToScan(packagesToScan);
    	
		return bean;
    	
    }
//    
    @Bean//用Jpa
    public EntityManagerFactory entityManagerFactory(){
    	LocalContainerEntityManagerFactoryBean bean=new LocalContainerEntityManagerFactoryBean();
    	bean.setDataSource(dataSource);
//    	bean.
    	Properties pro=new Properties();
    	for(Entry<String,String> e:getVendorProperties(dataSource).entrySet()){
    		
    		if(e.getKey().indexOf("hbm2ddl")==-1)
    			pro.setProperty(e.getKey(), e.getValue());
    		System.out.println(">>>>"+e.getKey()+"=="+ e.getValue());
    	}
    	System.out.println(">>>>hibernate.hbm2ddl.auto="+hbm2ddlAuto);
    	System.out.println(">>>>hibernate.packages.scan="+packagesToScan);
    	//原因：两种不同风格的字段生成格式影响数据查询与保存，这里设置。只保留一种风格
    	pro.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
//    	bean.setImplicitNamingStrategy(new ImplicitNamingStrategyJpaCompliantImpl());//H5的格式
//    	bean.setPhysicalNamingStrategy(new PhysicalNamingStrategyStandardImpl());//H5的格式
////    	setNamingStrategy(ImprovedNamingStrategy.INSTANCE);//H4的格式
//    	bean.setHibernateProperties(pro);
    	bean.setJpaProperties(pro);
    	bean.setPackagesToScan(packagesToScan);
    	
    	bean.setJpaPropertyMap(getVendorProperties(dataSource));
    	
		return bean.getObject();
    	
    }
   
    
//    @Bean//Hebernate
//    @Resource(name="sessionFactory")
//    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory){
//    	return new HibernateTransactionManager(sessionFactory);
//    }
//    @Bean//Jpa
//    @Primary//默认事务
//    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory){
//    	return new JpaTransactionManager(entityManagerFactory);
//    }
    
//
    /**自定义配置中Hibernate5要求有EntityManager的Bean对象  Jpa自动装载
     * @param sessionFactory
     * @return
     */
//    @Bean
//    @Resource(name="sessionFactory")
//    public EntityManager entityManager(SessionFactory sessionFactory){
//    	return sessionFactory.createEntityManager();
//    }
    
//    @Bean
//    @Primary//默认事务
//    public PlatformTransactionManager txManager(DataSource dataSource) {
//    	System.out.println(">>>>>>>>>>"+dataSource);
//        return new DataSourceTransactionManager(dataSource);
//    }

    //多数据源，要用多事务，建一个事务
//    @Bean(name = "transactionManagerPrimary")
//    @Primary//默认事务
//    PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
//    }

//    @Bean(name = "userTransaction")
//    public UserTransaction userTransaction() throws Throwable {
//        UserTransactionImp userTransactionImp = new UserTransactionImp();
//        userTransactionImp.setTransactionTimeout(10000);
//        return userTransactionImp;
//    }
//    
//    @Bean(name = "transactionManager")
//    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
//    public PlatformTransactionManager transactionManager() throws Throwable {
//        UserTransaction userTransaction = userTransaction();
//        
//        JtaTransactionManager manager = new JtaTransactionManager(userTransaction,atomikosTransactionManager());
//        return manager;
//    }
}
