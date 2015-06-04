/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import com.dailydealsbox.database.model.Member;

/**
 * @author x_ye
 */
public interface MemberService {

  /**
   * save
   * 
   * @param test
   */
  public void save(Member member);

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
   * findAll
   * 
   * @return
   */
  public List<Member> all();

  /**
   * delete
   * 
   * @param account
   */
  public void delete(Member member);
}
