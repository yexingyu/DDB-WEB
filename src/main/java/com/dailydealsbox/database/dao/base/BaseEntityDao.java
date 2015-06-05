/**
 * 
 */
package com.dailydealsbox.database.dao.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 * @param <T>
 */
public abstract class BaseEntityDao<T extends BaseEntityModel> {

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
   * @param id
   */
  public void delete(T entity) {
    entity.setStatus(BaseEntityModel.STATUS.DELETED);
    this.getSession().update(entity);
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
