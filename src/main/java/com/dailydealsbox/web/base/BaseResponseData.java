/**
 *
 */
package com.dailydealsbox.web.base;

import com.dailydealsbox.web.configuration.BaseEnum.RESPONSE_STATUS;

/**
 * @author x_ye
 */
public class BaseResponseData {
  private RESPONSE_STATUS status;
  private Object          data = null;

  /*
   * Constructors
   */
  public BaseResponseData(RESPONSE_STATUS status, Object data) {
    this.setStatus(status);
    this.setData(data);
  }

  /**
   * @return the status
   */
  public RESPONSE_STATUS getStatus() {
    return this.status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(RESPONSE_STATUS status) {
    this.status = status;
  }

  /**
   * @return the data
   */
  public Object getData() {
    return this.data;
  }

  /**
   * @param data
   *          the data to set
   */
  public void setData(Object data) {
    this.data = data;
  }

}
