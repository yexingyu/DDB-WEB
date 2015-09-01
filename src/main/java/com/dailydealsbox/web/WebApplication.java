/**
 *
 */
package com.dailydealsbox.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.dailydealsbox.web.filter.DDBAuthorizationInterceptor;

import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author x_ye
 */
@SpringBootApplication
@ComponentScan("com.dailydealsbox")
@EnableTransactionManagement
@PropertySource(value = { "classpath:database.properties" })
@EnableJpaRepositories("com.dailydealsbox.database.repository")
@EnableSwagger2
@EnableCaching
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {
  public static Logger logger = LoggerFactory.getLogger(WebApplication.class);

  @Autowired
  private Environment         environment;
  @Autowired
  DDBAuthorizationInterceptor authInterceptor;

  //@Autowired
  //private RequestMappingHandlerAdapter adapter;

  /**
   * crondMinutely
   */
  @Scheduled(initialDelay = 60000, fixedRate = 60000)
  public void crondMinutely() {
    logger.info("crondMinutely() is called.");
  }

  /**
   * crondSecondly
   */
  @Scheduled(initialDelay = 1000, fixedRate = 1000)
  public void crondSecondly() {
    logger.info("crondSecondly() is called.");
  }

  @Bean
  WebMvcConfigurerAdapter getAdapter() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(WebApplication.this.authInterceptor);
      }
    };
  }

  /**
   * confApi
   *
   * @return
   */
  @Bean
  public Docket confApi() {
    ResponseMessage msg_500 = new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Error")).build();
    return new Docket(DocumentationType.SWAGGER_2).globalResponseMessage(RequestMethod.GET, Collections.singletonList(msg_500))
        .globalResponseMessage(RequestMethod.POST, Collections.singletonList(msg_500))
        .apiInfo(new ApiInfo("Api Documentation", "Api Documentation, Version: 0.0.1", "0.0.1", null, "xingyu.ye@dailydealsbox.com", null, null));
  }

  /**
   * cacheManager
   *
   * @param caches
   * @return
   */
  @Bean
  @Autowired
  public CacheManager cacheManager(List<Cache> caches) {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(caches);
    return cacheManager;
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
