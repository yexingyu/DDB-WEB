/**
 * 
 */
package com.dailydealsbox.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author x_ye
 */
@SpringBootApplication
@PropertySource("file:etc/web.conf")
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {
  public static Logger                         logger = LoggerFactory
                                                          .getLogger(WebApplication.class);
  public static ConfigurableApplicationContext CTX;

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
    CTX = SpringApplication.run(WebApplication.class, args);
    CTX.registerShutdownHook();
  }

}
