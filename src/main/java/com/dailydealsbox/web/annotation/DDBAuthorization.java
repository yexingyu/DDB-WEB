/**
 *
 */
package com.dailydealsbox.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;

/**
 * @author x_ye
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Mapping
public @interface DDBAuthorization {
  MEMBER_ROLE[]value() default {};

  boolean requireAuthorization() default true;
}
