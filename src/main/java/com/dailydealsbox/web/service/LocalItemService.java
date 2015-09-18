/**
 *
 */
package com.dailydealsbox.web.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dailydealsbox.web.database.model.LocalItem;

/**
 * @author TimoYe
 */
public interface LocalItemService {

  /**
   * insert
   *
   * @param item {@link LocalItem}
   * @return
   */
  public LocalItem insert(LocalItem item);

  /**
   * update
   *
   * @param item {@link LocalItem}
   * @return
   */
  public LocalItem update(LocalItem item);

  /**
   * get
   * 
   * @param id
   * @return
   */
  public LocalItem get(int id);

  /**
   * list
   *
   * @param ids
   * @param storeIds
   * @param deleted
   * @param pageable
   * @return
   */
  public Page<LocalItem> list(Set<Integer> ids, Set<Integer> storeIds, boolean deleted, Pageable pageable);
}
