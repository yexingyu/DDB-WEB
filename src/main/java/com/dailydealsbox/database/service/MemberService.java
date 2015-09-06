/**
 *
 */
package com.dailydealsbox.database.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailydealsbox.configuration.BaseEnum.MEMBER_ROLE;
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
   * list
   * 
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Member> list(boolean deleted, Pageable pageable);

  /**
   * update
   *
   * @param member
   * @return
   */
  public Member update(Member member);

  /**
   * insert
   *
   * @param member
   * @return
   */
  public Member insert(Member member);

  /**
   * listByRole
   *
   * @param role
   * @return
   */
  public Set<Member> listByRole(MEMBER_ROLE role);
}
