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

import com.dailydealsbox.configuration.BaseEnum.COUNTRY;
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
   * findAllByIdsAndDeleted
   *
   * @param ids
   * @param deleted
   * @return
   */
  @Query(value = "select s from Store s where s.id in ?1 and deleted = ?2")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByIdsAndDeleted(Set<Integer> ids, boolean deleted);

  /**
   * findAllByIdsAndCountriesAndDeleted
   *
   * @param ids
   * @param countries
   * @param deleted
   * @return
   */
  @Query(value = "select s from Store s where s.id in ?1 and s.country in ?2 and deleted = ?3")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByIdsAndCountriesAndDeleted(Set<Integer> ids, Set<COUNTRY> countries, boolean deleted);

  /**
   * findByDeleted
   *
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findByDeleted(boolean deleted);

  /**
   * findAllByCountryAndDeleted
   *
   * @param country
   * @param deleted
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByCountryAndDeleted(COUNTRY country, boolean deleted);

  /**
   * findAllByCountryAndDeleted
   *
   * @param countries
   * @param deleted
   * @return
   */
  @Query(value = "select s from Store s where s.country in ?1 and deleted = ?2")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByCountryAndDeleted(Set<COUNTRY> countries, boolean deleted);
}
