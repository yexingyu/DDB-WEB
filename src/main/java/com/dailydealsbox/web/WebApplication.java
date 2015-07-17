/**
 *
 */
package com.dailydealsbox.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

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
  private Environment environment;

  @Override
  public void onStartup(ServletContext container) {
    // Create the 'root' Spring application context
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(WebApplication.class);

    // Manage the lifecycle of the root application context
    container.addListener(new ContextLoaderListener(rootContext));

    // Create the dispatcher servlet's Spring application context
    AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
    dispatcherContext.register(WebApplication.class);

    // Register and map the dispatcher servlet
    ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");
  }

  /**
   * faviconHandlerMapping
   *
   * @return
   */
  @Bean
  public SimpleUrlHandlerMapping faviconHandlerMapping() {
    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.setOrder(Integer.MIN_VALUE);
    mapping.setUrlMap(Collections.singletonMap("favicon.ico", this.faviconRequestHandler()));
    return mapping;
  }

  /**
   * faviconRequestHandler
   *
   * @return
   */
  @Bean
  protected ResourceHttpRequestHandler faviconRequestHandler() {
    ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
    requestHandler.setLocations(Arrays.<Resource> asList(new ClassPathResource("/")));
    return requestHandler;
  }

  /**
   * dataSource
   *
   * @return
   */
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(this.environment.getRequiredProperty("jdbc.driverClassName"));
    dataSource.setUrl(this.environment.getRequiredProperty("jdbc.url"));
    dataSource.setUsername(this.environment.getRequiredProperty("jdbc.username"));
    dataSource.setPassword(this.environment.getRequiredProperty("jdbc.password"));
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
    entityManagerFactoryBean.setDataSource(this.dataSource());
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean.setPackagesToScan("com.dailydealsbox.database.model");
    entityManagerFactoryBean.setJpaProperties(this.hibernateProperties());
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

    // hibernate connection settings
    properties.put("hibernate.dialect", this.environment.getRequiredProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", this.environment.getRequiredProperty("hibernate.show_sql"));
    properties.put("hibernate.format_sql", this.environment.getRequiredProperty("hibernate.format_sql"));
    properties.put("hibernate.enable_lazy_load_no_trans", true);

    // Second Cache Settings
    properties.put("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
    properties.put("hibernate.cache.use_structured_entries", true);
    properties.put("hibernate.cache.use_query_cache", true);
    properties.put("hibernate.cache.use_second_level_cache", true);
    properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
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
    transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());
    return transactionManager;
  }

  /**
   * mappingJackson2HttpMessageConverter
   *
   * @return
   */
  //  @Bean
  //  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
  //    ObjectMapper mapper = new ObjectMapper();
  //    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  //    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
  //    return converter;
  //  }

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
