/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dailydealsbox.database.model.TimoTest;

/**
 * @author x_ye
 */
@Repository("timo_test_dao")
public class TimoTestDaoImpl extends AbstractDao<TimoTest> implements TimoTestDao {

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TimoTestDao#save(com.dailydealsbox.database.model.TimoTest)
   */
  @Override
  public void save(TimoTest test) {
    this.persist(test);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TimoTestDao#findAll()
   */
  @Override
  public List<TimoTest> findAll() {
    Criteria criteria = getSession().createCriteria(TimoTest.class);
    return criteria.list();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.dao.TimoTestDao#delete(int)
   */
  @Override
  public void delete(int id) {
    Query query = getSession().createSQLQuery("delete from timo_test where id = :id");
    query.setInteger("id", id);
    query.executeUpdate();

  }
}
