/**
 *
 */
package com.dailydealsbox.web.database.repository;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.web.database.model.LocalItem;

/**
 * @author x_ye
 */
public interface LocalItemRepository extends CrudRepository<LocalItem, Integer> {

  //  @Query(value = "from LocalItem l where l.id in :ids and l.store in ")
  //  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  //  public Page<LocalItem> findByIdsAndStoresAndDeleted(Set<Integer> ids, Set<Store> stores, boolean deleted, Pageable pageable);

  /**
   * findByDeleted
   *
   * @param deleted
   * @param pageable
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<LocalItem> findByDeleted(boolean deleted, Pageable pageable);

}
