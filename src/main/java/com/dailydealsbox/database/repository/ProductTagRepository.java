/**
 *
 */
package com.dailydealsbox.database.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.ProductTag;

/**
 * @author x_ye
 */
public interface ProductTagRepository extends CrudRepository<ProductTag, Integer> {

  /**
   * findFirstByLanguageAndValue
   *
   * @param language
   * @param value
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public ProductTag findFirstByLanguageAndValue(String language, String value);
}
