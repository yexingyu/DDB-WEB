/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import com.dailydealsbox.database.model.MemberEmail;

/**
 * @author x_ye
 */
public interface MemberEmailDao {
  /**
   * get
   * 
   * @param id
   * @return
   */
  public MemberEmail get(int id);

  /**
   * all
   * 
   * @return
   */
  public List<MemberEmail> all();
}
