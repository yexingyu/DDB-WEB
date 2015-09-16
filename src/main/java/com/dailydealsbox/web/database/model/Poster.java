/**
 *
 */
package com.dailydealsbox.web.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
import com.dailydealsbox.web.database.model.base.BaseEntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "member")
@SQLDelete(sql = "update member set deleted = true where id = ?")
@Where(clause = "deleted = 0")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Poster extends BaseEntityModel {

  //discuss forum attributes
  @NotNull
  @Column(name = "nick_name", nullable = false, length = 100)
  private String nickName = "";

  @Column(name = "signature", length = 160)
  private String signature = "";

  @Column(name = "avatar", length = 45)
  private String avatar = "";

  @Column(name = "level", length = 45)
  private String level = "";

  @Column(name = "point")
  private int point;

  @Column(name = "point_redeemed")
  private int pointRedeemed;

  @NotNull
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private MEMBER_ROLE role = MEMBER_ROLE.MEMBER;

  // forum attributes
  /**
   * @return the nickName
   */
  public String getNickName() {
    return this.nickName;
  }

  /**
   * @param nickName the nickName to set
   */
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * @return the signature
   */
  public String getSignature() {
    return this.signature;
  }

  /**
   * @param signature the signature to set
   */
  public void setSignature(String signature) {
    this.signature = signature;
  }

  /**
   * @return the avatar
   */
  public String getAvatar() {
    return this.avatar;
  }

  /**
   * @param avatar the avatar to set
   */
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  /**
   * @return the level
   */
  public String getLevel() {
    return this.level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(String level) {
    this.level = level;
  }

  /**
   * @return the point
   */
  public int getPoint() {
    return this.point;
  }

  /**
   * @param point the point to set
   */
  public void setPoint(int point) {
    this.point = point;
  }

  /**
   * @return the point_redeemed
   */
  public int getPointRedeemed() {
    return this.pointRedeemed;
  }

  /**
   * @param point the point to set
   */
  public void setPointRedeemed(int pointredeemed) {
    this.pointRedeemed = pointredeemed;
  }

}
