/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.dailydealsbox.database.model.MemberEmail;

/**
 * @author x_ye
 */
@Repository
public class MemberEmailDaoImpl extends HibernateDaoSupport implements MemberEmailDao {

  @Autowired
  public void init(SessionFactory factory) {
    this.setSessionFactory(factory);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.MemberEmailDao#get(int)
   */
  @Override
  public MemberEmail get(int id) {
    return this.getHibernateTemplate().get(MemberEmail.class, id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.MemberEmailDao#all()
   */
  @Override
  public List<MemberEmail> all() {
    return (List<MemberEmail>) this.getHibernateTemplate().find("from MemberEmail");
  }
}
