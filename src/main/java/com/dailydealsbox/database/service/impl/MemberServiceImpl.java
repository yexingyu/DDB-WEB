/**
 *
 */
package com.dailydealsbox.database.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.model.Member;
import com.dailydealsbox.database.model.MemberAddress;
import com.dailydealsbox.database.model.MemberEmail;
import com.dailydealsbox.database.model.MemberPhone;
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
    return this.repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#getByAccount(java.lang.String)
   */
  @Override
  public Member getByAccount(String account) {
    return this.repo.findByAccount(account);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#update(com.dailydealsbox.database.model.Member)
   */
  @Override
  public Member update(Member member) {
    this.fixMember(member);
    return this.repo.save(member);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#insert(com.dailydealsbox.database.model.Member)
   */
  @Override
  public Member insert(Member member) {
    this.fixMember(member);
    return this.update(member);
  }

  /**
   * fixMember
   *
   * @param member
   * @return
   */
  private Member fixMember(Member member) {
    for (MemberAddress o : member.getAddresses()) {
      o.setMember(member);
    }
    for (MemberEmail o : member.getEmails()) {
      o.setMember(member);
    }
    for (MemberPhone o : member.getPhones()) {
      o.setMember(member);
    }
    return member;
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MemberService#list(boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Member> list(boolean deleted, Pageable pageable) {
    return this.repo.findByDeleted(deleted, pageable);
  }

}
