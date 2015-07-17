/**
 * 
 */
package com.dailydealsbox.web.base;

import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;

/**
 * @author x_ye
 */
public class GenericResponseData extends BaseResponseData {

  /**
   * @param status
   * @param data
   */
  public GenericResponseData(RESPONSE_STATUS status, Object data) {
    super(status, data);
  }

  /**
   * newInstance
   * 
   * @param status
   * @param data
   * @return
   */
  public static GenericResponseData newInstance(RESPONSE_STATUS status, Object data) {
    return new GenericResponseData(status, data);
  }

}
