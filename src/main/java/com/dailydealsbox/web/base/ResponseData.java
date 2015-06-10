/**
 * 
 */
package com.dailydealsbox.web.base;

/**
 * @author x_ye
 */
public class ResponseData extends BaseResponseData {

  /**
   * @param status
   * @param data
   */
  public ResponseData(STATUS status, Object data) {
    super(status, data);
  }

  /**
   * newInstance
   * 
   * @param status
   * @param data
   * @return
   */
  public static ResponseData newInstance(STATUS status, Object data) {
    return new ResponseData(status, data);
  }

}
