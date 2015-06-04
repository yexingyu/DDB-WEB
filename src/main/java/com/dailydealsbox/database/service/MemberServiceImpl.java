/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.dao.MemberDao;
import com.dailydealsbox.database.model.Member;

/**
 * @author x_ye
 * @param <MemberDao>
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberDao dao;

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.database.service.MemberService#save(com.dailydealsbox.database.model.Member)
   */
  @Override
  public void save(Member member) {
    dao.save(member);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MemberService#all()
   */
  @Override
  public List<Member> all() {
    return dao.all();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.TestService#get(int)
   */
  @Override
  public Member get(int id) {
    return dao.get(id);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.database.service.MemberService#delete(com.dailydealsbox.database.model.Member
   * )
   */
  @Override
  public void delete(Member member) {
    dao.delete(member);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.AccountsService#getByAccount(java.lang.String)
   */
  @Override
  public Member getByAccount(String account) {
    return dao.getByAccount(account);
  }

}
