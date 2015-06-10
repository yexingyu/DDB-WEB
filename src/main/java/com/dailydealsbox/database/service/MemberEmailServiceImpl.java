/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailydealsbox.database.dao.MemberEmailDao;
import com.dailydealsbox.database.model.MemberEmail;

/**
 * @author x_ye
 */
@Service
public class MemberEmailServiceImpl implements MemberEmailService {

  @Autowired
  private MemberEmailDao dao;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MemberEmailService#get(int)
   */
  @Override
  public MemberEmail get(int id) {
    return dao.get(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.MemberEmailService#all()
   */
  @Override
  public List<MemberEmail> all() {
    return dao.all();
  }

}
