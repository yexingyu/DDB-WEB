/**
 * 
 */
package com.dailydealsbox.web.base;

/**
 * @author x_ye
 */
public class BaseResponseData {
  private STATUS status;
  private Object data = null;

  /**
   * newInstance
   * 
   * @param status
   * @param data
   * @return
   */
  public static BaseResponseData newInstance(STATUS status, Object data) {
    return new BaseResponseData(status, data);
  }

  /*
   * Constructors
   */
  public BaseResponseData(STATUS status, Object data) {
    this.setStatus(status);
    this.setData(data);
  }

  /**
   * @return the status
   */
  public STATUS getStatus() {
    return this.status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(STATUS status) {
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

  public enum STATUS {
    SUCCESS, FAIL, NEED_LOGIN
  }
}
