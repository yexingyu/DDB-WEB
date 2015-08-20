/**
 *
 */
package com.dailydealsbox.database.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "local_item")
@SQLDelete(sql = "update local_item set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocalItem extends BaseEntityModel {

  @NotNull
  @Column(name = "image_url")
  private String imageUrl;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<LocalItemText> texts;

  /**
   * @return the imageUrl
   */
  public String getImageUrl() {
    return this.imageUrl;
  }

  /**
   * @param imageUrl
   *          the imageUrl to set
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
   * @param texts
   *          the texts to set
   */
  public void setTexts(Set<LocalItemText> texts) {
    this.texts = texts;
  }

}
