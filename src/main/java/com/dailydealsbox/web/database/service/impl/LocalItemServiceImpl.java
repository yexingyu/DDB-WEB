/**
 *
 */
package com.dailydealsbox.web.database.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dailydealsbox.web.database.model.LocalItem;
import com.dailydealsbox.web.database.repository.LocalItemRepository;
import com.dailydealsbox.web.database.service.LocalItemService;

/**
 * @author TimoYe
 */
@Service
public class LocalItemServiceImpl implements LocalItemService {

  @Autowired
  private LocalItemRepository repo;

  /**
   * fix
   *
   * @param item
   */
  private void fix(LocalItem item) {
    if (item.getTexts() != null) {
      item.getTexts().forEach(text -> {
        text.setItem(item);
      });
    }
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.web.database.service.LocalItemService#insert(com.dailydealsbox.web.database.model.LocalItem)
   */
  @Override
  public LocalItem insert(LocalItem item) {
    this.fix(item);
    return repo.save(item);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.web.database.service.LocalItemService#update(com.dailydealsbox.web.database.model.LocalItem)
   */
  @Override
  public LocalItem update(LocalItem item) {
    this.fix(item);
    return repo.save(item);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.web.database.service.LocalItemService#list(java.util.Set, java.util.Set, boolean, org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<LocalItem> list(Set<Integer> ids, Set<Integer> storeIds, boolean deleted, Pageable pageable) {

    return null;
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.web.database.service.LocalItemService#get(int)
   */
  @Override
  public LocalItem get(int id) {
    return this.repo.findOne(id);
  }

}
