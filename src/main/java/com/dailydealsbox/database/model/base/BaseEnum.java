/**
 * 
 */
package com.dailydealsbox.database.model.base;

/**
 * @author x_ye
 */
public class BaseEnum {
  /**
   * @author x_ye
   */
  public static enum MEMBER_ROLE {
    MEMBER, ADMIN
  }

  /**
   * @author x_ye
   */
  public static enum MEMBER_LOGIN_TYPE {
    DAILYDEALSBOX, FACEBOOK
  }

  /**
   * @author x_ye
   */
  public static enum MEMBER_ADDRESS_COUNTRY {
    CA, US
  }

  /**
   * @author x_ye
   */
  public static enum MEMBER_ADDRESS_TYPE {
    BILLING, LIVING, SHIPPING
  }

  /**
   * @author x_ye
   */
  public static enum MEMBER_PHONE_TYPE {
    MOBILE, HOME, WORK, FAX
  }

  /**
   * @author x_ye
   */
  public static enum LANGUAGE {
    EN, FR
  }

  /**
   * @author x_ye
   */
  public static enum CURRENCY {
    CAD, USD
  }

  /**
   * @author x_ye
   */
  public static enum PRODUCT_FEE_TITLE {
    SHIPPING
  }

  /**
   * @author x_ye
   */
  public static enum PRODUCT_FEE_TYPE {
    PERCENTAGE, DOLLER
  }

  /**
   * @author x_ye
   */
  public static enum PRODUCT_TAX_TITLE {
    FEDERAL, PROVINCE, DUTY
  }

  /**
   * @author x_ye
   */
  public static enum PRODUCT_TAX_TYPE {
    PERCENTAGE
  }

  /**
   * @author x_ye
   */
  public static enum RESPONSE_STATUS {
    SUCCESS, FAIL, NEED_LOGIN, NO_PERMISSION, EMPTY_RESULT
  }
}
