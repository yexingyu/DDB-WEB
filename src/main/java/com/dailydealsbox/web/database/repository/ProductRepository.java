/**
 *
 */
package com.dailydealsbox.web.database.repository;

import java.util.Date;
import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dailydealsbox.web.database.model.Product;
import com.dailydealsbox.web.database.model.Store;

/**
 * @author x_ye
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {

  /**
   * findByStoresAndTagsAndDeletedAndDisabled
   *
   * @param stores
   * @param tags
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  @Query(value = "select distinct p from Product p join p.tags t where p.store in :stores and t.value in :tags and p.deleted = :deleted and p.disabled = :disabled")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> findByStoresAndTagsAndDeletedAndDisabled(@Param("stores") Set<Store> stores, @Param("tags") Set<String> tags, @Param("deleted") boolean deleted,
      @Param("disabled") boolean disabled, Pageable pageable);

  /**
   * findByStoresAndDeletedAndDisabled
   *
   * @param stores
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  @Query(value = "select distinct p from Product p where p.store in :stores and p.deleted = :deleted and p.disabled = :disabled")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> findByStoresAndDeletedAndDisabled(@Param("stores") Set<Store> stores, @Param("deleted") boolean deleted, @Param("disabled") boolean disabled, Pageable pageable);

  /**
   * findByTagsAndDeletedAndDisabled
   *
   * @param tags
   * @param deleted
   * @param disabled
   * @param pageable
   * @return
   */
  @Query(value = "select distinct p from Product p join p.tags t where t.value in :tags and p.deleted = :deleted and p.disabled = :disabled")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> findByTagsAndDeletedAndDisabled(@Param("tags") Set<String> tags, @Param("deleted") boolean deleted, @Param("disabled") boolean disabled, Pageable pageable);

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
   * search
   *
   * @param keyword
   * @param pageable
   * @return
   */
  @Query(value = "select distinct p from Product p join p.texts t where t.name like %:keyword% and p.deleted = false and p.disabled = false")
  //@Query(value = "select distinct p from Product p join p.texts t where SQL('t.name like ?', :keyword) and p.deleted = false and p.disabled = false")
  @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
  public Page<Product> search(@Param("keyword") String keyword, Pageable pageable);

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

  /**
   * findAllByModifiedAtGreaterThanAndDeletedFalseAndDisabledFalse
   *
   * @param stamp
   * @return
   */
  public Set<Product> findAllByModifiedAtGreaterThanAndDeletedFalseAndDisabledFalse(Date stamp);
}
