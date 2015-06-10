/**
 * 
 */
package com.dailydealsbox.service;

import java.util.List;

import com.dailydealsbox.database.model.Member;

/**
 * @author x_ye
 */
public interface MemberService {
  /**
   * get
   * 
   * @param id
   * @return
   */
  public Member get(int id);

  /**
   * getByAccount
   * 
   * @param account
   * @return
   */
  public Member getByAccount(String account);

  /**
   * getAll
   * 
   * @return
   */
  public List<Member> getAll();
}
