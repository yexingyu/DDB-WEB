/**
 * 
 */
package com.dailydealsbox.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author x_ye
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.dailydealsbox")
@PropertySource("file:etc/web.conf")
@EnableScheduling
public class WebConfiguration {
  public static ConfigurableApplicationContext CTX;

  @Bean
  public InternalResourceViewResolver setupViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
}
