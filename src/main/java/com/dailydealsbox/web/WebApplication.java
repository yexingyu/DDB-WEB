/**
 * 
 */
package com.dailydealsbox.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

/**
 * @author x_ye
 */
public class WebApplication {
  public static Logger logger = LoggerFactory.getLogger(WebApplication.class);

  /**
   * main
   * 
   * @param args
   */
  public static void main(String[] args) {
    WebConfiguration.CTX = SpringApplication.run(WebConfiguration.class, args);
    WebConfiguration.CTX.registerShutdownHook();
  }

}
