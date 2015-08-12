/**
 *
 */
package com.dailydealsbox.database.repository;

import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.dailydealsbox.database.model.Store;

/**
 * @author x_ye
 */
public interface StoreRepository extends CrudRepository<Store, Integer> {

  /**
   * findByDeleted
   *
   * @param deleted
   * @param pageable
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Store> findByDeleted(boolean deleted, Pageable pageable);

  /**
   * findByDefaultFollowedTrueAndDeletedFalseOrderByIdAsc
   *
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findByDefaultFollowedTrueAndDeletedFalseOrderByIdAsc();

  /**
   * findByIds
   *
   * @param ids
   * @return
   */
  @Query(value = "select s from Store s where s.id in ?1")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findByIds(Set<Integer> ids);

  /**
   * findByDeleted
   *
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findByDeleted(boolean deleted);
}
