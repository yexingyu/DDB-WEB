/**
 * 
 */
package com.dailydealsbox.database.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author x_ye
 * @param <T>
 */
public abstract class AbstractDao<T> {

  @Autowired
  private SessionFactory sessionFactory;

  /**
   * getSession
   * 
   * @return
   */
  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  /**
   * persist
   * 
   * @param entity
   */
  public void persist(T entity) {
    getSession().persist(entity);
  }

  /**
   * delete
   * 
   * @param entity
   */
  public void delete(T entity) {
    getSession().delete(entity);
  }

  /**
   * delete
   * 
   * @param id
   */
  public void delete(int id) {
    this.getSession().delete("id", id);
  }

  /**
   * get
   * 
   * @param clazz
   * @param id
   * @return
   */
  @SuppressWarnings("unchecked")
  public T get(Class<T> clazz, int id) {
    return (T) this.getSession().get(clazz, id);
  }
}
