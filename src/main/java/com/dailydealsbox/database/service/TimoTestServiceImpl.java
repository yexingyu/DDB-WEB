/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.database.dao.TimoTestDao;
import com.dailydealsbox.database.model.TimoTest;

/**
 * @author x_ye
 */
@Service("timo_test_service")
@Transactional
public class TimoTestServiceImpl implements TimoTestService {

  @Autowired
  private TimoTestDao dao;

  @Override
  public void save(TimoTest test) {
    dao.save(test);
  }

  @Override
  public List<TimoTest> findAll() {
    return dao.findAll();
  }

  @Override
  public void delete(int id) {
    dao.delete(id);
  }

}
