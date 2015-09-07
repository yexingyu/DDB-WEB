/**
 *
 */
package com.dailydealsbox.web;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dailydealsbox.web.database.service.ProductService;
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
@EnableTransactionManagement
@EnableJpaRepositories("com.dailydealsbox.web.database.repository")
@EnableSwagger2
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {
  public static Logger logger = LoggerFactory.getLogger(WebApplication.class);

  @Autowired
  DDBAuthorizationInterceptor authInterceptor;
  @Autowired
  ProductService              productService;

  /**
   * crondMinutely
   */
  @Scheduled(initialDelay = 60 * 1000, fixedRate = 60 * 1000)
  public void crondMinutely() {
    logger.info("crondMinutely() is running.");
    logger.info("crondMinutely() is called.");
  }

  /**
   * crondHourly
   */
  @Scheduled(initialDelay = 3600 * 1000, fixedRate = 3600 * 1000)
  public void crondHourly() {
    logger.info("crondHourly() is running.");
    // update reputation
    try {
      this.productService.fixProduct();
    } catch (Exception e) {
      e.printStackTrace();
    }
    logger.info("crondHourly() is called.");
  }

  /**
   * getEhCacheManager
   *
   * @return
   */
  @Bean
  public CacheManager getEhCacheManager() {
    return new EhCacheCacheManager(this.getEhCacheFactory().getObject());
  }

  /**
   * getEhCacheFactory
   *
   * @return
   */
  @Bean
  public EhCacheManagerFactoryBean getEhCacheFactory() {
    EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
    factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
    factoryBean.setShared(true);
    return factoryBean;
  }

  /**
   * getAdapter
   *
   * @return
   */
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
