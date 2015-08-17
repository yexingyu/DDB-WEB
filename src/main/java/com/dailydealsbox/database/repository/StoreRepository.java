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
   * findByIdsAndDeleted
   *
   * @param ids
   * @param deleted
   * @param pageable
   * @return
   */
  @Query(value = "select s from Store s where s.id in ?1 and deleted = ?2")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Store> findByIdsAndDeleted(Set<Integer> ids, boolean deleted, Pageable pageable);

  /**
   * findByCountriesAndDeleted
   *
   * @param countries
   * @param deleted
   * @param pageable
   * @return
   */
  @Query(value = "select distinct s from Store s where s.country in ?1 and deleted = ?2")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Store> findByCountriesAndDeleted(Set<COUNTRY> countries, boolean deleted, Pageable pageable);

  /**
   * findByIdsAndCountriesAndDeleted
   *
   * @param ids
   * @param countries
   * @param deleted
   * @param pageable
   * @return
   */
  @Query(value = "select distinct s from Store s where s.id in ?1 and s.country in ?2 and deleted = ?3")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Store> findByIdsAndCountriesAndDeleted(Set<Integer> ids, Set<COUNTRY> countries, boolean deleted, Pageable pageable);

  /**
   * findAllByDefaultFollowedTrueAndDeletedFalseOrderByIdAsc
   *
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByDefaultFollowedTrueAndDeletedFalseOrderByIdAsc();

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
  @Query(value = "select distinct s from Store s where s.id in ?1 and s.country in ?2 and deleted = ?3")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByIdsAndCountriesAndDeleted(Set<Integer> ids, Set<COUNTRY> countries, boolean deleted);

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
   * findAllByCountriesAndDeleted
   *
   * @param countries
   * @param deleted
   * @return
   */
  @Query(value = "select distinct s from Store s where s.country in ?1 and deleted = ?2")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByCountriesAndDeleted(Set<COUNTRY> countries, boolean deleted);

  /**
   * findAllByDeleted
   *
   * @param deleted
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByDeleted(boolean deleted);
}
