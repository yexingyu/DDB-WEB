/**
 *
 */
package com.dailydealsbox.web.database.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.dailydealsbox.web.configuration.BaseEnum.MEMBER_ROLE;
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
public class Poster extends BaseModel {

  @NotNull
  @Column(name = "nick_name")
  private String      nickName  = "";

  @Column(name = "signature")
  private String      signature = "";

  @Column(name = "avatar")
  private String      avatar    = "";

  @Column(name = "picture")
  private String      picture   = "";

  @Column(name = "level")
  private String      level     = "";

  @Column(name = "point")
  private int         point;

  @Column(name = "point_redeemed")
  private int         pointRedeemed;

  @NotNull
  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private MEMBER_ROLE role      = MEMBER_ROLE.MEMBER;
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "poster", cascade = { CascadeType.ALL }, orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<Product> products;

  /**
   * constructor
   */
  public Poster() {}

  /**
   * constructor
   *
   * @param member
   */
  public Poster(Member member) {
    this.setId(member.getId());
    this.setAvatar(member.getAvatar());
    this.setLevel(member.getLevel());
    this.setNickName(member.getNickName());
    this.setPoint(member.getPoint());
    this.setPointRedeemed(member.getPointRedeemed());
    this.setRole(member.getRole());
    this.setSignature(member.getSignature());
  }

  /**
   * @return the nickName
   */
  public String getNickName() {
    return this.nickName;
  }

  /**
   * @param nickName
   *          the nickName to set
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
   * @param signature
   *          the signature to set
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
   * @param avatar
   *          the avatar to set
   */
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  /**
   * @return the picture
   */
  public String getPicture() {
    return this.picture;
  }

  /**
   * @param picture
   *          the picture to set
   */
  public void setPicture(String picture) {
    this.picture = picture;
  }

  /**
   * @return the level
   */
  public String getLevel() {
    return this.level;
  }

  /**
   * @param level
   *          the level to set
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
   * @param point
   *          the point to set
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
   * @param point
   *          the point to set
   */
  public void setPointRedeemed(int pointredeemed) {
    this.pointRedeemed = pointredeemed;
  }

  /**
   * @return the role
   */
  public MEMBER_ROLE getRole() {
    return this.role;
  }

  /**
   * @param role
   *          the role to set
   */
  public void setRole(MEMBER_ROLE role) {
    this.role = role;
  }

}
