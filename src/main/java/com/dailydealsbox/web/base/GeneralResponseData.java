/**
 * 
 */
package com.dailydealsbox.web.base;

/**
 * @author x_ye
 */
public class GeneralResponseData extends BaseResponseData {

  /**
   * @param status
   * @param data
   */
  public GeneralResponseData(STATUS status, Object data) {
    super(status, data);
  }

  /**
   * newInstance
   * 
   * @param status
   * @param data
   * @return
   */
  public static GeneralResponseData newInstance(STATUS status, Object data) {
    return new GeneralResponseData(status, data);
  }

}
