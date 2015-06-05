/**
 * 
 */
package com.dailydealsbox.web;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author x_ye
 */
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan("com.dailydealsbox")
@PropertySource(value = { "classpath:database.properties" })
public class WebApplication extends SpringBootServletInitializer {
  public static Logger logger = LoggerFactory.getLogger(WebApplication.class);

  @Autowired
  private Environment  environment;

  /**
   * sessionFactory
   * 
   * @return
   */
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan(new String[] { "com.dailydealsbox.database.model" });
    sessionFactory.setHibernateProperties(hibernateProperties());
    return sessionFactory;
  }

  /**
   * dataSource
   * 
   * @return
   */
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
    dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
    dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
    dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
    return dataSource;
  }

  /**
   * hibernateProperties
   * 
   * @return
   */
  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
    properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
    properties.put("hibernate.enable_lazy_load_no_trans", true);
    //properties.put("hibernate.current_session_context_class", "thread");
    properties.put("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
    properties.put("hibernate.cache.use_structured_entries", "true");
    properties.put("hibernate.cache.use_query_cache", "true");
    properties.put("hibernate.cache.use_second_level_cache", "true");
    //properties.put("hibernate.hbm2ddl.auto", "validate");
    properties.put("hibernate.cache.region.factory_class",
        "org.hibernate.cache.ehcache.EhCacheRegionFactory");
    properties.put("net.sf.ehcache.configurationResourceName", "ehcache.xml");
    return properties;
  }

  /**
   * transactionManager
   * 
   * @param s
   * @return
   */
  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory factory) {
    HibernateTransactionManager txManager = new HibernateTransactionManager();
    txManager.setSessionFactory(factory);
    return txManager;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.springframework.boot.context.web.SpringBootServletInitializer#configure(org.springframework
   * .boot.builder.SpringApplicationBuilder)
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(WebApplication.class);
  }

  /**
   * setupViewResolver
   * 
   * @return
   */
  @Bean
  public InternalResourceViewResolver setupViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }

  /**
   * main
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args).registerShutdownHook();
  }

}
