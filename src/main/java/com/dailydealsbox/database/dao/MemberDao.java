/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import com.dailydealsbox.database.model.Member;

/**
 * @author x_ye
 */
public interface MemberDao {

  /**
   * save
   * 
   * @param member
   */
  public void save(Member member);

  /**
   * findAll
   * 
   * @return
   */
  public List<Member> all();

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
   * delete
   * 
   * @param account
   */
  public void delete(Member account);
}
