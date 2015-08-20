/**
 *
 */
package com.dailydealsbox.database.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
    this.fix(member);
    return this.repo.save(member);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.MemberService#insert(com.dailydealsbox.database.model.Member)
   */
  @Override
  public Member insert(Member member) {
    this.fix(member);
    return this.repo.save(member);
  }

  /**
   * fix
   *
   * @param member
   * @return
   */
  private Member fix(Member member) {
    // fix addresses
    if (member.getAddresses() != null) {
      for (MemberAddress o : member.getAddresses()) {
        o.setMember(member);
      }
    }

    // fix emails
    if (member.getEmails() != null) {
      boolean hasPrimary = false;
      for (MemberEmail o : member.getEmails()) {
        if (StringUtils.equalsIgnoreCase(o.getEmail(), member.getAccount())) {
          o.setPrimary(true);
          hasPrimary = true;
          break;
        }
      }
      if (!hasPrimary) {
        MemberEmail primaryEmail = new MemberEmail();
        primaryEmail.setEmail(member.getAccount());
        primaryEmail.setPrimary(true);
        member.getEmails().add(primaryEmail);
      }
      for (MemberEmail o : member.getEmails()) {
        if (StringUtils.isBlank(o.getHashCode())) {
          o.generateHashCode();
        }
        o.setMember(member);
      }
    }

    // fix phones
    if (member.getPhones() != null) {
      for (MemberPhone o : member.getPhones()) {
        o.setMember(member);
      }
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
