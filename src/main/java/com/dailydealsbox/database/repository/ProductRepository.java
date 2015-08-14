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

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.Store;

/**
 * @author x_ye
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {

  //  @Query(value = "select p from Product p where store_id = :storeId and status = 0 order by id desc",
  //    countProjection = ":offset",
  //    countQuery = ":cnt")
  //  List<Product> findByStoreId(@Param("storeId") int storeId, @Param("offset") int offset, @Param("cnt") int cnt);

  /**
   * findByStoresAndTagsAndDeletedAndDisabled
   *
   * @param tags
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  @Query(value = "select p from Product p join p.tags t where p.store in ?1 and t.value in ?2 and p.deleted = ?3 and p.disabled = ?4")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> findByStoresAndTagsAndDeletedAndDisabled(Set<Store> stores, Set<String> tags, boolean deleted, boolean disabled, Pageable pageable);

  /**
   * findByStoresAndDeletedAndDisabled
   *
   * @param stores
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  @Query(value = "select p from Product p where p.store in ?1 and p.deleted = ?2 and p.disabled = ?3")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> findByStoresAndDeletedAndDisabled(Set<Store> stores, boolean deleted, boolean disabled, Pageable pageable);

  /**
   * findByTagsAndDeletedAndDisabled
   *
   * @param tags
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  @Query(value = "select p from Product p join p.tags t where t.value in ?1 and p.deleted = ?2 and p.disabled = ?3")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> findByTagsAndDeletedAndDisabled(Set<String> tags, boolean deleted, boolean disabled, Pageable pageable);

  /**
   * findByStoreIdAndDisabledAndDeleted
   *
   * @param storeId
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> findByStoreIdAndDisabledAndDeleted(int storeId, boolean deleted, boolean disabled, Pageable pageable);

  /**
   * findByDisabledAndDeleted
   *
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> findByDisabledAndDeleted(boolean deleted, boolean disabled, Pageable pageable);

  /**
   * increaseCountLikes
   *
   * @param productId
   */
  @Modifying
  @Query("update Product p set p.countLikes = p.countLikes + 1 where p.id = ?1")
  public void increaseCountLikes(int productId);

  /**
   * increaseCountReviews
   *
   * @param productId
   */
  @Modifying
  @Query("update Product p set p.countReviews = p.countReviews + 1 where p.id = ?1")
  public void increaseCountReviews(int productId);
}
