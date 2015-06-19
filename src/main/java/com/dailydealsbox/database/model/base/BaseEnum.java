/**
 * 
 */
package com.dailydealsbox.database.model.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;

/**
 * @author x_ye
 */
public class BaseEnum {

  public static enum MEMBER_ROLE {
    MEMBER, ADMIN;
  }

  public static enum MEMBER_LOGIN_TYPE {
    DAILYDEALSBOX, FACEBOOK;
  }

  public static enum COUNTRY {
    CA, US;
  }

  public static enum MEMBER_ADDRESS_TYPE {
    BILLING, LIVING, SHIPPING;
  }

  public static enum MEMBER_PHONE_TYPE {
    MOBILE, HOME, WORK, FAX;
  }

  public static enum LANGUAGE {
    EN, FR;
  }

  public static enum CURRENCY {
    CAD, USD;
  }

  public static enum PRODUCT_FEE_TITLE {
    SHIPPING;
  }

  public static enum PRODUCT_FEE_TYPE {
    PERCENTAGE, DOLLER;
  }

  public static enum PRODUCT_TAX_TITLE {
    FEDERAL, PROVINCE, DUTY;
  }

  public static enum PRODUCT_TAX_TYPE {
    PERCENTAGE;
  }

  public static enum RESPONSE_STATUS {
    SUCCESS, FAIL, NEED_LOGIN, NO_PERMISSION, EMPTY_RESULT;
  }

  /**
   * enums
   * 
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static Map<String, List> enums() {
    Map<String, List> map = new HashMap<>();
    for (Class<?> clz : BaseEnum.class.getDeclaredClasses()) {
      if (clz.isEnum()) {
        map.put(clz.getSimpleName(), Arrays.asList(clz.getEnumConstants()));
      }
    }
    return map;
  }

  public static void main(String[] args) {

    System.out.println(EnumUtils.getEnumMap(COUNTRY.class));
  }
}
