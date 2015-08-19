/**
 *
 */
package com.dailydealsbox.configuration;

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

  /**
   * @author x_ye
   */
  public static enum MEMBER_LOGIN_TYPE {
    DAILYDEALSBOX("DailyDealsBox"), FACEBOOK("Facebook");
    private final String name;

    private MEMBER_LOGIN_TYPE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum COUNTRY {
    CA("Canada"), US("United States");

    private final String name;

    private COUNTRY(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum MEMBER_ADDRESS_TYPE {
    BILLING("BILLING"), LIVING("LIVING"), SHIPPING("SHIPPING");
    private final String name;

    private MEMBER_ADDRESS_TYPE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum MEMBER_PHONE_TYPE {
    MOBILE("MOBILE"), HOME("HOME"), WORK("WORK"), FAX("FAX");
    private final String name;

    private MEMBER_PHONE_TYPE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum LANGUAGE {
    EN("ENGLISH"), FR("FRENCH"), CN("CHINESE");
    private final String name;

    private LANGUAGE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum STORE_CATEGORY {
    SC_APS("Animals & Pet Supplies"), SC_AA("Apparel & Accessories"), SC_AE("Arts & Entertainment"), SC_BT(
        "Baby & Toddler"), SC_BI("Business & Industrial"), SC_CO("Cameras & Optics"), SC_EL(
        "Electronics"), SC_FBT("Food, Beverages & Tobacco"), SC_FN("Furniture"), SC_HW("Hardware"), SC_HB(
        "Health & Beauty"), SC_HG("Home & Garden"), SC_MT("Mature"), SC_MD("Media"), SC_SW(
        "Software"), SC_SG("Sporting Goods"), SC_TG("Toys & Games"), SC_VP("Vehicles & Parts"), SC_ALL(
        "Retailor in Store, Online"), SC_OR("Retailor Online");
    ;
    private final String name;

    private STORE_CATEGORY(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum CURRENCY {
    CAD("CAD"), USD("USD");
    private final String name;

    private CURRENCY(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum PRODUCT_FEE_TITLE {
    SHIPPING("SHIPPING"), ECO("ECO"), IMPORT("IMPORT");
    private final String name;

    private PRODUCT_FEE_TITLE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum PRODUCT_FEE_TYPE {
    PERCENTAGE("PERCENTAGE"), AMOUNT("AMOUNT");
    private final String name;

    private PRODUCT_FEE_TYPE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum PRODUCT_TAX_TITLE {
    CAFEDERAL("CAFEDERAL"), CAPROVINCE("CAPROVINCE"), CADUTY("CADUTY");
    private final String name;

    private PRODUCT_TAX_TITLE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum PRODUCT_TAX_TYPE {
    PERCENTAGE("PERCENTAGE"), AMOUNT("AMOUNT");
    private final String name;

    private PRODUCT_TAX_TYPE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum STORE_TYPE {
    ONLINE("ONLINE"), LOCAL("ONLINE");
    private final String name;

    private STORE_TYPE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum OPTION_TYPE {
    COLOR("COLOR"), SIZE("SIZE"), STORAGE("STORAGE"), WIDTH("WIDTH");
    private final String name;

    private OPTION_TYPE(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  /**
   * @author x_ye
   */
  public static enum ORDER_STATUS {
    NEW("New Order"), CONFIRMED("Customer Confirmed"), WF_DOWNPAYMENT("Waiting For Downpayment"), APPROVED(
        "Approved Order"), SHIPPED("Shipped Order"), FIN("Finished Order");
    private final String name;

    private ORDER_STATUS(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }

  public static enum MEMBER_ROLE {
    MEMBER, ADMIN;
  }

  public static enum RESPONSE_STATUS {
    SUCCESS, ERROR, NEED_LOGIN, NO_PERMISSION, EMPTY_RESULT;
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
