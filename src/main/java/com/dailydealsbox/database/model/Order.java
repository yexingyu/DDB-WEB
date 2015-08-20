/**
 *
 */
package com.dailydealsbox.database.model;

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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import com.dailydealsbox.configuration.BaseEnum.ORDER_STATUS;
import com.dailydealsbox.database.model.base.BaseEntityModel;

/**
 * @author x_ye
 */
@Entity
@Table(name = "`order`")
@SQLDelete(sql = "update `order` set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order extends BaseEntityModel {
  //customer info
  @NotNull
  @Column(name = "member_id", nullable = false)
  private int               memberId;

  @NotNull
  @Size(min = 4, max = 100)
  @Column(name = "first_name", nullable = false, length = 45)
  private String            firstName;

  @NotNull
  @Size(min = 4, max = 100)
  @Column(name = "last_name", nullable = false, length = 45)
  private String            lastName;

  @NotNull
  @Size(min = 4, max = 100)
  @Column(name = "email", nullable = false, length = 45)
  private String            email;

  @NotNull
  @Size(min = 4, max = 100)
  @Column(name = "phone", nullable = false, length = 45)
  private String            phone;
  //product info
  @NotNull
  @Column(name = "product_id", nullable = false)
  private int               productId;

  @NotNull
  @Size(min = 4, max = 512)
  @Column(name = "product_url", nullable = false, length = 512)
  private String            proudctUrl;

  @NotNull
  @Size(min = 4, max = 512)
  @Column(name = "product_image", nullable = false, length = 512)
  private String            proudctImage;

  @NotNull
  @Size(min = 4, max = 512)
  @Column(name = "product_name", nullable = false, length = 512)
  private String            productName;

  @NotNull
  @Size(min = 4, max = 2048)
  @Column(name = "product_descritpion", nullable = false, length = 2048)
  private String            productDescription;

  @NotNull
  @Size(min = 4, max = 2048)
  @Column(name = "product_option_value", nullable = false, length = 2048)
  private String            productOptionValue;

  @NotNull
  @Column(name = "product_price", nullable = false)
  private double            productPrice;

  @NotNull
  @Size(min = 4, max = 2048)
  @Column(name = "product_quantity", nullable = false, length = 2048)
  private int               productQuantity;

  //order info
  @NotNull
  @Column(name = "sub_total", nullable = false)
  private double            subTotal;

  //tax info
  @NotNull
  @Column(name = "tax_amount", nullable = false)
  private double            taxAmount;

  //fee info    
  @NotNull
  @Column(name = "shipping", nullable = false)
  private double            shipping;

  @NotNull
  @Column(name = "fee_amout", nullable = false)
  private double            feeAmout;

  @NotNull
  @Column(name = "total", nullable = false)
  private double            total;

  //financial info
  @NotNull
  @Column(name = "down_payment", nullable = false)
  private double            downPayment;

  @NotNull
  @Column(name = "principal", nullable = false)
  private double            principal;

  @NotNull
  @Column(name = "apr", nullable = false)
  private double            apr;

  @NotNull
  @Column(name = "monthly_payment", nullable = false)
  private double            monthlyPayment;

  @NotNull
  @Column(name = "interest", nullable = false)
  private double            interest;

  //payment info
  @NotNull
  @Column(name = "paid_amount", nullable = false)
  private double            paidAmount;

  @NotNull
  @Column(name = "paymentRecievable", nullable = false)
  private double            paymentRecievable;

  @NotNull
  @Column(name = "erned_interest", nullable = false)
  private double            ernedInterest;

  @NotNull
  @Column(name = "remaining_balance", nullable = false)
  private double            remainingBalance;

  @NotNull
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ORDER_STATUS      status;

  @OneToMany(fetch = FetchType.LAZY,
    mappedBy = "order",
    cascade = { CascadeType.ALL },
    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<OrderAddress> addresses;

  /**
   * validate
   *
   * @return
   */
  public boolean validate() {
    return true;
  }

  /**
   * @return the status
   */
  public ORDER_STATUS getStatus() {
    return this.status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(ORDER_STATUS status) {
    this.status = status;
  }

  // product info getter, setter
  /**
   * @return the productId
   */
  public int getProductId() {
    return this.productId;
  }

  /**
   * @param productId
   *          the productId to set
   */
  public void setProductId(int productId) {
    this.productId = productId;
  }

  /**
   * @return the proudctUrl
   */
  public String getProudctUrl() {
    return this.proudctUrl;
  }

  /**
   * @param proudctUrl
   *          the proudctUrl to set
   */
  public void setProudctUrl(String proudctUrl) {
    this.proudctUrl = proudctUrl;
  }

  /**
   * @return the proudctImage
   */
  public String getProudctImage() {
    return this.proudctImage;
  }

  /**
   * @param proudctImage
   *          the proudctImage to set
   */
  public void setProudctImage(String proudctImage) {
    this.proudctImage = proudctImage;
  }

  /**
   * @return the proudctName
   */
  public String getProudctName() {
    return this.productName;
  }

  /**
   * @param proudctName
   *          the proudctName to set
   */
  public void setProudctName(String proudctName) {
    this.productName = proudctName;
  }

  /**
   * @return the proudctDescription
   */
  public String getProductDescription() {
    return this.productDescription;
  }

  /**
   * @param proudctDescription
   *          the proudctDescription to set
   */
  public void setProductDescription(String proudctDescription) {
    this.productDescription = proudctDescription;
  }

  /**
   * @return the productOptionValue
   */
  public String getProductOptionValue() {
    return this.productOptionValue;
  }

  /**
   * @param productOptionValue
   *          the productOptionValue to set
   */
  public void setProductOptionValue(String productOptionValue) {
    this.productOptionValue = productOptionValue;
  }

  /**
   * @return the productPrice
   */
  public double getProductPrice() {
    return this.productPrice;
  }

  /**
   * @param productPrice
   *          the productPrice to set
   */
  public void setProductPrice(double productPrice) {
    this.productPrice = productPrice;
  }

  /**
   * @return the type
   */
  public int getProductQuantity() {
    return this.productQuantity;
  }

  /**
   * @param productQuantity
   *          the productQuantity to set
   */
  public void setProductQuantity(int productQuantity) {
    this.productQuantity = productQuantity;
  }

  //member info getter, setter
  /**
   * @return the memberId
   */
  public int getMemberId() {
    return this.memberId;
  }

  /**
   * @param memberId
   *          the memberId to set
   */
  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * @param firstName
   *          the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return this.phone;
  }

  /**
   * @param phone
   *          the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  // address
  /**
   * @return the addresses
   */
  public Set<OrderAddress> getAddresses() {
    return this.addresses;
  }

  /**
   * @param addresses
   *          the addresses to set
   */
  public void setAddresses(Set<OrderAddress> addresses) {
    this.addresses = addresses;
  }

  //order info

  /**
   * @return the subtotal
   */
  public double getSubTotal() {
    return this.subTotal;
  }

  /**
   * @param subtotal
   *          the subtotal to set
   */
  public void setSubTotal(double subTotal) {
    this.subTotal = subTotal;
  }

  /**
   * @return the tax_amount
   */
  public double getTaxAmount() {
    return this.taxAmount;
  }

  /**
   * @param tax_amount
   *          the tax_amount to set
   */
  public void setTaxAmount(double taxAmount) {
    this.taxAmount = taxAmount;
  }

  /**
   * @return the shipping
   */
  public double getShipping() {
    return this.shipping;
  }

  /**
   * @param shipping
   *          the shipping to set
   */
  public void setShipping(double shipping) {
    this.shipping = shipping;
  }

  /**
   * @return the fee_amout
   */
  public double getFeeAmout() {
    return this.feeAmout;
  }

  /**
   * @param fee_amout
   *          the fee_amout to set
   */
  public void setFeeAmout(double feeAmout) {
    this.feeAmout = feeAmout;
  }

  /**
   * @return the total
   */
  public double getTotal() {
    return this.total;
  }

  /**
   * @param total
   *          the total to set
   */
  public void setTotal(double total) {
    this.total = total;
  }

  //financial info
  /**
   * @return the down_payment
   */
  public double getDownPayment() {
    return this.downPayment;
  }

  /**
   * @param down_payment
   *          the down_payment to set
   */
  public void setDownPayment(double downayment) {
    this.downPayment = downayment;
  }

  /**
   * @return the principal
   */
  public double getPrincipal() {
    return this.principal;
  }

  /**
   * @param principal
   *          the principal to set
   */
  public void setprincipal(double principal) {
    this.principal = principal;
  }

  /**
   * @return the apr
   */
  public double getApr() {
    return this.apr;
  }

  /**
   * @param apr
   *          the apr to set
   */
  public void setApr(double apr) {
    this.apr = apr;
  }

  /**
   * @return the monthly_payment
   */
  public double getMonthlyPayment() {
    return this.monthlyPayment;
  }

  /**
   * @param monthly_payment
   *          the monthly_payment to set
   */
  public void setMonthlyPayment(double monthly_payment) {
    this.monthlyPayment = monthly_payment;
  }

  /**
   * @return the interest
   */
  public double getInterest() {
    return this.interest;
  }

  /**
   * @param interest
   *          the interest to set
   */
  public void setInterest(double interest) {
    this.interest = interest;
  }

  /**
   * @return the paid_amount
   */
  public double getPaidAmount() {
    return this.paidAmount;
  }

  /**
   * @param paid_amount
   *          the paid_amount to set
   */
  public void setPaidAmount(double paidAmount) {
    this.paidAmount = paidAmount;
  }

  /**
   * @return the erned_interest
   */
  public double getErnedInterest() {
    return this.ernedInterest;
  }

  /**
   * @param erned_interest
   *          the erned_interest to set
   */
  public void seternedInterest(double ernedInterest) {
    this.ernedInterest = ernedInterest;
  }

  /**
   * @return the remaining_balance
   */
  public double getRemainingBalance() {
    return this.remainingBalance;
  }

  /**
   * @param remaining_balance
   *          the remaining_balance to set
   */
  public void setRemainingBalance(double remainingBalance) {
    this.remainingBalance = remainingBalance;
  }

}
