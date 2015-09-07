/**
 *
 */
package com.dailydealsbox.web.database.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.web.database.model.Member;

/**
 * @author x_ye
 */
public interface MemberRepository extends CrudRepository<Member, Integer> {

  /**
   * findByAccount
   *
   * @param account
   * @return
   */
  public Member findByAccount(String account);

  /**
   * findByRoleAndDeletedFalse
   *
   * @param role
   * @return
   */
  public Set<Member> findByRole(MEMBER_ROLE role);

  /**
   * findByDeleted
   *
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Member> findByDeleted(boolean deleted, Pageable pageable);

}
