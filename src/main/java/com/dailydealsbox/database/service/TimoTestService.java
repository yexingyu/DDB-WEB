/**
 * 
 */
package com.dailydealsbox.database.service;

import java.util.List;

import com.dailydealsbox.database.model.TimoTest;

/**
 * @author x_ye
 */
public interface TimoTestService {

  /**
   * save
   * 
   * @param test
   */
  void save(TimoTest test);

  List<TimoTest> findAll();

  void delete(int id);
}
