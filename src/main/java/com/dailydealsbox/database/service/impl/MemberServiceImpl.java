/**
 * 
 */
package com.dailydealsbox.database.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.repository.MemberRepository;
import com.dailydealsbox.database.service.MemberService;

/**
 * @author x_ye
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

  @Resource
  private MemberRepository repo;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#get(int)
   */
  @Override
  public Member get(int id) {
    return repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#getByAccount(java.lang.String)
   */
  @Override
  public Member getByAccount(String account) {
    return repo.findByAccount(account);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#getAll()
   */
  @Override
  public List<Member> getAll() {
    return (List<Member>) repo.findAll();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#update(com.dailydealsbox.database.model.Member)
   */
  @Override
  public Member update(Member member) {
    member.setMemberForChildren();
    return repo.save(member);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#insert(com.dailydealsbox.database.model.Member)
   */
  @Override
  public Member insert(Member member) {
    return this.update(member);
  }

}