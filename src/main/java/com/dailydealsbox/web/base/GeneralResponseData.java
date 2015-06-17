/**
 * 
 */
package com.dailydealsbox.web.base;

import com.dailydealsbox.database.model.base.BaseEnum.RESPONSE_STATUS;

/**
 * @author x_ye
 */
public class GeneralResponseData extends BaseResponseData {

  /**
   * @param status
   * @param data
   */
  public GeneralResponseData(RESPONSE_STATUS status, Object data) {
    super(status, data);
  }

  /**
   * newInstance
   * 
   * @param status
   * @param data
   * @return
   */
  public static GeneralResponseData newInstance(RESPONSE_STATUS status, Object data) {
    return new GeneralResponseData(status, data);
  }

}
