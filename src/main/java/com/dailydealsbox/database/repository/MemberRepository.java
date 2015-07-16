/**
 *
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.base.BaseEnum;

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
   * findByDeletedOrderByCreatedAtDesc
   *
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<Member> findByDeletedOrderByCreatedAtDesc(int deleted, Pageable pageable);

  /**
   * findByDeletedAndRoleOrderByCreatedAtAsc
   * 
   * @param deleted
   * @param role
   * @param pageable
   * @return
   */
  public Page<Member> findByDeletedAndRoleOrderByCreatedAtAsc(int deleted, BaseEnum.MEMBER_ROLE role, Pageable pageable);
}
