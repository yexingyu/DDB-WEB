/**
 *
 */
package com.dailydealsbox.web.database.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "local_item")
@SQLDelete(sql = "update local_item set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalItem extends BaseEntityModel {

  @NotNull
  @Column(name = "add_by")
  private int addBy;

  @NotNull
  @Column(name = "add_by_name")
  private String addByName;

  @NotNull
  @Column(name = "image_url")
  private String imageUrl;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<LocalItemText> texts;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  /**
   * validate
   *
   * @return
   */
  public boolean validate() {
    return true;
  }

  /**
   * @return the store
   */
  public Store getStore() {
    return this.store;
  }

  /**
   * @param store the store to set
   */
  public void setStore(Store store) {
    this.store = store;
  }

  /**
   * @return the addBy
   */
  public int getAddBy() {
    return this.addBy;
  }

  /**
   * @param addBy the addBy to set
   */
  public void setAddBy(int addBy) {
    this.addBy = addBy;
  }

  /**
   * @return the addByName
   */
  public String getAddByName() {
    return this.addByName;
  }

  /**
   * @param addByName the addByName to set
   */
  public void setAddByName(String addByName) {
    this.addByName = addByName;
  }

  /**
   * @return the imageUrl
   */
  public String getImageUrl() {
    return this.imageUrl;
  }

  /**
   * @param imageUrl the imageUrl to set
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /**
   * @return the texts
   */
  public Set<LocalItemText> getTexts() {
    return this.texts;
  }

  /**
   * @param texts the texts to set
   */
  public void setTexts(Set<LocalItemText> texts) {
    this.texts = texts;
  }

}
