/**
 *
 */
package com.dailydealsbox.database.repository;

import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dailydealsbox.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.configuration.BaseEnum.STORE_TYPE;
import com.dailydealsbox.database.model.Store;

/**
 * @author x_ye
 */
public interface StoreRepository extends CrudRepository<Store, Integer> {

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
