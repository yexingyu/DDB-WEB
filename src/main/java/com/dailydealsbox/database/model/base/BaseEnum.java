/**
 * 
 */
package com.dailydealsbox.database.model.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.EnumUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    CA("Canada"), US("United States");

    private final String name;

    private COUNTRY(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
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
    SHIPPING, ECO, IMPORT;
  }

  public static enum PRODUCT_FEE_TYPE {
    PERCENTAGE, AMOUNT;
  }

  public static enum PRODUCT_TAX_TITLE {
    FEDERAL, PROVINCE, DUTY;
  }

  public static enum PRODUCT_TAX_TYPE {
    PERCENTAGE, AMOUNT;
  }

  public static enum RESPONSE_STATUS {
    SUCCESS, FAIL, NEED_LOGIN, NO_PERMISSION, EMPTY_RESULT;
  }

  public static enum STORE_TYPE {
    ONLINE, LOCAL
  }

  /**
   * toMap
   * 
   * @param clz
   * @return
   */
  public static <E extends Enum<E>> Map<String, String> toMap(Class<E> clz) {
    Map<String, String> rst = new HashMap<>();
    for (Entry<String, E> e : EnumUtils.getEnumMap(clz).entrySet()) {
      rst.put(e.getKey(), e.getValue().toString());
    }
    return rst;
  }

  /**
   * enums
   * 
   * @return
   */
  @SuppressWarnings({ "unchecked" })
  public static <E extends Enum<E>> Map<String, Map<String, String>> enums() {
    Map<String, Map<String, String>> map = new HashMap<>();
    for (Class<?> clz : BaseEnum.class.getDeclaredClasses()) {
      if (clz.isEnum()) {
        map.put(clz.getSimpleName(), toMap((Class<E>) clz));
      }
    }
    return map;
  }

  public static void main(String[] args) throws JsonProcessingException {
    System.out.println(EnumUtils.getEnumMap(RESPONSE_STATUS.class));
    System.out.println(EnumUtils.getEnumMap(COUNTRY.class));
    System.out.println(enums());
    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.writeValueAsString(enums()));
  }
}
