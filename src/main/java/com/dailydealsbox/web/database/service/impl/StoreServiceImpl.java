/**
 *
 */
package com.dailydealsbox.web.database.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.web.configuration.BaseEnum.COUNTRY;
import com.dailydealsbox.web.configuration.BaseEnum.STORE_TYPE;
import com.dailydealsbox.web.database.model.Store;
import com.dailydealsbox.web.database.repository.StoreRepository;
import com.dailydealsbox.web.database.service.StoreService;

/**
 * @author x_ye
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

  @Resource
  private StoreRepository repo;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#get(int)
   */
  @Override
  public Store get(int id) {
    return this.repo.findOne(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#update(com.dailydealsbox.database.model.Store)
   */
  @Override
  public Store update(Store store) {
    return this.repo.save(store);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.service.StoreService#insert(com.dailydealsbox.database.model.Product)
   */
  @Override
  public Store insert(Store store) {
    return this.repo.save(store);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#delete(int)
   */
  @Override
  public void delete(int id) {
    this.repo.delete(id);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#listDefaultFollowed()
   */
  @Override
  public Set<Store> listDefaultFollowed() {
    return this.repo.findAllByDefaultFollowedTrueAndDeletedFalseOrderByIdAsc();
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#increaseCountLikes(int)
   */
  @Override
  public void increaseCountLikes(int storeId) {
    this.repo.increaseCountLikes(storeId);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#increaseCountFollowings(int)
   */
  @Override
  public void increaseCountFollowings(int storeId) {
    this.repo.increaseCountFollowings(storeId);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#decreaseCountFollowings(int)
   */
  @Override
  public void decreaseCountFollowings(int storeId) {
    this.repo.decreaseCountFollowings(storeId);
  }

  @Autowired
  private EntityManager em;

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#listAll(java.util.Set, java.util.Set, com.dailydealsbox.configuration.BaseEnum.STORE_TYPE, boolean)
   */
  @Override
  public Set<Store> listAll(Set<Integer> ids, Set<COUNTRY> countries, STORE_TYPE type, boolean deleted) {
    TypedQuery<Store> query = this.buildQuery(ids, countries, type, deleted);
    return new HashSet<Store>(query.getResultList());
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
  private TypedQuery<Store> buildQuery(Set<Integer> ids, Set<COUNTRY> countries, STORE_TYPE type, boolean deleted) {
    if (ids != null && ids.isEmpty()) {
      ids = null;
    }

    CriteriaBuilder builder = this.em.getCriteriaBuilder();
    CriteriaQuery<Store> criteriaQuery = builder.createQuery(Store.class);
    Root<Store> root = criteriaQuery.from(Store.class);

    // init restriction list
    List<Predicate> predicates = new ArrayList<Predicate>();

    // where id in (ids)
    if (ids != null && !ids.isEmpty()) {
      predicates.add(root.get("id").in(ids));
    }

    // where country in (countries)
    if (countries != null && !countries.isEmpty()) {
      predicates.add(root.get("country").in(countries));
    }

    // where type = type
    if (type != null) {
      predicates.add(builder.equal(root.get("type"), type));
    }

    predicates.add(builder.equal(root.get("deleted"), deleted));
    criteriaQuery.where(predicates.toArray(new Predicate[] {}));

    return this.em.createQuery(criteriaQuery);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.StoreService#list(java.util.Set, java.util.Set, com.dailydealsbox.configuration.BaseEnum.STORE_TYPE, boolean,
   * org.springframework.data.domain.Pageable)
   */
  @Override
  public Page<Store> list(Set<Integer> ids, Set<COUNTRY> countries, STORE_TYPE type, boolean deleted, Pageable pageable) {
    Page<Store> stores = null;

    if (ids != null && countries != null && type != null) {
      stores = this.repo.findByIdsAndCountriesAndTypeAndDeleted(ids, countries, type, deleted, pageable);
    } else if (ids != null && countries != null) {
      stores = this.repo.findByIdsAndCountriesAndDeleted(ids, countries, deleted, pageable);
    } else if (ids != null && type != null) {
      stores = this.repo.findByIdsAndTypeAndDeleted(ids, type, deleted, pageable);
    } else if (countries != null && type != null) {
      stores = this.repo.findByCountriesAndTypeAndDeleted(countries, type, deleted, pageable);
    } else if (ids != null) {
      stores = this.repo.findByIdsAndDeleted(ids, deleted, pageable);
    } else if (countries != null) {
      stores = this.repo.findByCountriesAndDeleted(countries, deleted, pageable);
    } else if (type != null) {
      stores = this.repo.findByTypeAndDeleted(type, deleted, pageable);
    } else {
      stores = this.repo.findByDeleted(deleted, pageable);
    }

    return stores;
  }
}
