/**
 * 
 */
package com.dailydealsbox.database.dao;

import java.util.List;

import com.dailydealsbox.database.model.TimoTest;

/**
 * @author x_ye
 */
public interface TimoTestDao {

  void save(TimoTest test);

  List<TimoTest> findAll();

  void delete(int id);
}
