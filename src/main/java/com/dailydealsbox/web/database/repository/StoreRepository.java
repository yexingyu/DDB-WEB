/**
 *
 */
package com.dailydealsbox.web.database.repository;

import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dailydealsbox.web.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.web.configuration.BaseEnum.STORE_TYPE;
import com.dailydealsbox.web.database.model.Store;

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
   * findByIdsAndCountriesAndTypeAndDeleted
   *
   * @param ids
   * @param countries
   * @param type
   * @param deleted
   * @param pageable
   * @return
   */
  @Query(value = "select distinct s from Store s where s.id in :ids and s.country in :countries and type = :type and deleted = :deleted")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Store> findByIdsAndCountriesAndTypeAndDeleted(@Param("ids") Set<Integer> ids, @Param("countries") Set<COUNTRY> countries, @Param("type") STORE_TYPE type,
      @Param("deleted") boolean deleted, Pageable pageable);

  /**
   * findByCountriesAndTypeAndDeleted
   *
   * @param countries
   * @param type
   * @param deleted
   * @param pageable
   * @return
   */
  @Query(value = "select distinct s from Store s where s.country in :countries and type = :type and deleted = :deleted")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Store> findByCountriesAndTypeAndDeleted(@Param("countries") Set<COUNTRY> countries, @Param("type") STORE_TYPE type, @Param("deleted") boolean deleted, Pageable pageable);

  /**
   * findByTypeAndDeleted
   * 
   * @param type
   * @param deleted
   * @param pageable
   * @return
   */
  @Query(value = "select distinct s from Store s where type = :type and deleted = :deleted")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Store> findByTypeAndDeleted(@Param("type") STORE_TYPE type, @Param("deleted") boolean deleted, Pageable pageable);

  /**
   * findByIdsAndTypeAndDeleted
   *
   * @param ids
   * @param type
   * @param deleted
   * @param pageable
   * @return
   */
  @Query(value = "select distinct s from Store s where s.id in :ids and type = :type and deleted = :deleted")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Store> findByIdsAndTypeAndDeleted(@Param("ids") Set<Integer> ids, @Param("type") STORE_TYPE type, @Param("deleted") boolean deleted, Pageable pageable);

  /**
   * findAllByDefaultFollowedTrueAndDeletedFalseOrderByIdAsc
   *
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Set<Store> findAllByDefaultFollowedTrueAndDeletedFalseOrderByIdAsc();

  /**
   * increaseCountLikes
   *
   * @param storeId
   */
  @Modifying
  @Query("update Store s set s.countLikes = s.countLikes + 1 where s.id = ?1")
  public void increaseCountLikes(int storeId);

  /**
   * increaseCountFollowings
   *
   * @param storeId
   */
  @Modifying
  @Query("update Store s set s.countFollowings = s.countFollowings + 1 where s.id = ?1")
  public void increaseCountFollowings(int storeId);

  /**
   * decreaseCountFollowings
   *
   * @param storeId
   */
  @Modifying
  @Query("update Store s set s.countFollowings = s.countFollowings - 1 where s.id = ?1")
  public void decreaseCountFollowings(int storeId);
}
