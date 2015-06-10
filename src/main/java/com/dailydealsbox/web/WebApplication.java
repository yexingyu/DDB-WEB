/**
 * 
 */
package com.dailydealsbox.web;

import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author x_ye
 */
@SpringBootApplication
@ComponentScan("com.dailydealsbox")
@EnableTransactionManagement
@PropertySource(value = { "classpath:database.properties" })
@EnableJpaRepositories("com.dailydealsbox.database.repository")
public class WebApplication extends SpringBootServletInitializer {
  public static Logger logger = LoggerFactory.getLogger(WebApplication.class);

  @Autowired
  private Environment  environment;

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
   * entityManagerFactory
   * 
   * @return
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean.setPackagesToScan("com.dailydealsbox.database.model");
    entityManagerFactoryBean.setJpaProperties(hibernateProperties());
    entityManagerFactoryBean.setSharedCacheMode(SharedCacheMode.ALL);
    return entityManagerFactoryBean;
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

    // Second Cache Settings
    properties.put("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
    properties.put("hibernate.cache.use_structured_entries", true);
    properties.put("hibernate.cache.use_query_cache", true);
    properties.put("hibernate.cache.use_second_level_cache", true);
    properties.put("hibernate.cache.region.factory_class",
        "org.hibernate.cache.ehcache.EhCacheRegionFactory");
    properties.put("net.sf.ehcache.configurationResourceName", "ehcache.xml");
    return properties;
  }

  /**
   * transactionManager
   * 
   * @return
   */
  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
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
   * main
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args).registerShutdownHook();
  }

}
