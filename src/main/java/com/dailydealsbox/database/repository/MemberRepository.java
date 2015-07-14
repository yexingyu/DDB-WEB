/**
 * 
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.base.BaseEntityModel;
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
   * findByStatusOrderByCreateAtDesc
   * 
   * @param status
   * @param pageable
   * @return
   */
  public Page<Member> findByStatusOrderByCreatedAtDesc(BaseEntityModel.STATUS status, Pageable pageable);

  /**
   * findByStatusAndRoleOrderByCreateAtAsc
   * 
   * @param status
   * @param role
   * @param pageable
   * @return
   */
  public Page<Member> findByStatusAndRoleOrderByCreatedAtAsc(BaseEntityModel.STATUS status, BaseEnum.MEMBER_ROLE role, Pageable pageable);
}
