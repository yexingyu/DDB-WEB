/**
 *
 */
package com.dailydealsbox.web.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import com.dailydealsbox.web.database.model.ProductTag;

/**
 * @author x_ye
 */
public interface ProductTagService {
  /**
   * get
   *
   * @param id
   * @return
   */
  public ProductTag get(int id);

  /**
   * list
   *
   * @param ids
   * @param deleted
   *          @return
   */
  public Page<ProductTag> list(Set<Integer> ids, boolean deleted);

  /**
   * listAll
   *
   * @param ids
   * @param deleted
   * @return
   */
  public Set<ProductTag> listAll(Set<Integer> ids, boolean deleted);

  public ProductTag update(ProductTag productTag);

  /**
   * insert
   *
   * @param productTag
   * @return
   */
  public ProductTag insert(ProductTag productTag);

  /**
   * delete
   *
   * @param id
   */
  public void delete(int id);

}
