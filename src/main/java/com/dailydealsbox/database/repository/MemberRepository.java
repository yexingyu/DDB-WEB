/**
 * 
 */
package com.dailydealsbox.database.repository;

import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.Member;

/**
 * @author x_ye
 */
public interface MemberRepository extends CrudRepository<Member, Integer> {

  Member findByAccount(String account);
}
