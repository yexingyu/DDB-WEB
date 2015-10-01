/**
 *
 */
package com.dailydealsbox.web.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.web.database.model.ProductTag;
import com.dailydealsbox.web.database.repository.ProductTagRepository;
import com.dailydealsbox.web.service.ProductTagService;

/**
 * @author x_ye
 */
@Service
@Transactional
public class ProductTagServiceImpl implements ProductTagService {

  @Resource
  private ProductTagRepository repo;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.ProductTagService#get(int)
   */
  @Override
  public ProductTag get(int id) {
    return this.repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.service.ProductTagService#update(com.dailydealsbox.database.model.ProductTag)
   */
  @Override
  public ProductTag update(ProductTag store) {
    return this.repo.save(store);
  }

  /*
   * (non-Javadoc)
   * @see
   * com.dailydealsbox.service.ProductTagService#insert(com.dailydealsbox.database.model.Product)
   */
  @Override
  public ProductTag insert(ProductTag store) {
    return this.repo.save(store);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductTagService#delete(int)
   */
  @Override
  public void delete(int id) {
    this.repo.delete(id);
  }

  @Autowired
  private EntityManager em;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductTagService#listAll(java.util.Set, java.util.Set,
   * com.dailydealsbox.configuration.BaseEnum.STORE_TYPE, boolean)
   */
  @Override
  public Set<ProductTag> listAll(Set<Integer> ids, boolean deleted) {
    TypedQuery<ProductTag> query = this.buildQuery(ids, deleted);
    query.setHint("org.hibernate.cacheable", true);
    return new HashSet<ProductTag>(query.getResultList());
  }

  /**
   * buildQuery
   *
   * @param ids
   * @param countries
   * @param type
   * @param deleted
   * @return
   */
  private TypedQuery<ProductTag> buildQuery(Set<Integer> ids, boolean deleted) {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.ProductTagService#list(java.util.Set, java.util.Set,
   * com.dailydealsbox.configuration.BaseEnum.STORE_TYPE, boolean,
   * org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<ProductTag> list(Set<Integer> ids, boolean deleted) {
    Page<ProductTag> productTags = null;

    return productTags;
  }
}
