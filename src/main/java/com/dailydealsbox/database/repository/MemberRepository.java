/**
 *
 */
package com.dailydealsbox.database.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.database.model.Member;

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
   * findByDelete
   *
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Member> findByDelete(boolean deleted, Pageable pageable);

}
