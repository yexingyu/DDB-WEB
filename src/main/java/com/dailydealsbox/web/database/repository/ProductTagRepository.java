/**
 *
 */
package com.dailydealsbox.web.database.repository;

import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.web.database.model.ProductTag;

/**
 * @author x_ye
 */
public interface ProductTagRepository extends CrudRepository<ProductTag, Integer> {

  /**
   * findFirstByValue
   *
   * @param language
   * @param value
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public ProductTag findFirstByValue(String value);

  /*
   * (non-Javadoc)
   * @see org.springframework.data.repository.CrudRepository#findAll()
   */
  @Override
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<ProductTag> findAll();
}
