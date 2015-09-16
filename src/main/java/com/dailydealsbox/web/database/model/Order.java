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

import com.dailydealsbox.web.configuration.BaseEnum.ORDER_STATUS;
import com.dailydealsbox.web.database.model.base.BaseEntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author x_ye
 */
@Entity
@Table(name = "`order`")
@SQLDelete(sql = "update `order` set deleted = true where id = ?")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends BaseEntityModel {
  //customer info
  @NotNull
  @Column(name = "member_id")
  private int memberId;

  @NotNull
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Column(name = "last_name")
  private String lastName;

  @NotNull
  @Column(name = "email")
  private String email;

  @NotNull
  @Column(name = "company_email")
  private String companyEmail;

  @NotNull
  @Column(name = "phone")
  private String phone;

  @NotNull
  @Column(name = "product_id")
  private int productId;

  @NotNull
  @Column(name = "product_url")
  private String proudctUrl;

  @NotNull
  @Column(name = "product_image")
  private String proudctImage;

  @NotNull
  @Column(name = "product_price")
  private double productPrice;

  @NotNull
  @Column(name = "product_name")
  private String productName;

  @NotNull
  @Column(name = "product_descritpion")
  private String productDescription;

  @NotNull
  @Column(name = "product_option_value")
  private String productOptionValue;

  @NotNull
  @Column(name = "product_quantity")
  private int productQuantity;

  //order info
  @NotNull
  @Column(name = "sub_total")
  private double subTotal;

  @NotNull
  @Column(name = "tax_type")
  private String taxType;

  @NotNull
  @Column(name = "tax_rate")
  private double taxRate;

  @NotNull
  @Column(name = "tax_amount")
  private double taxAmount;

  @NotNull
  @Column(name = "shipping")
  private double shipping;

  @NotNull
  @Column(name = "fee_type")
  private String feeType;

  @NotNull
  @Column(name = "fee_amout")
  private double feeAmout;

  @NotNull
  @Column(name = "total")
  private double total;

  @NotNull
  @Column(name = "down_payment")
  private double downPayment;

  @NotNull
  @Column(name = "principal")
  private double principal;

  @NotNull
  @Column(name = "apr")
  private double apr;

  @NotNull
  @Column(name = "number_of_payment")
  private int numberOfPayment;

  @NotNull
  @Column(name = "monthly_payment")
  private double monthlyPayment;

  @NotNull
  @Column(name = "interest")
  private double interest;

  @NotNull
  @Column(name = "paid_sequence_number")
  private int paiedSequenceNumber;

  @NotNull
  @Column(name = "paid_amount")
  private double paidAmount;

  @NotNull
  @Column(name = "erned_interest")
  private double ernedInterest;

  @NotNull
  @Column(name = "payment_recievable")
  private double paymentRecievable;

  @NotNull
  @Column(name = "remaining_balance")
  private double remainingBalance;

  @NotNull
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ORDER_STATUS status;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = { CascadeType.ALL }, orphanRemoval = true)
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

  //member info
  /**
   * @return the memberId
   */
  public int getMemberId() {
    return this.memberId;
  }

  /**
   * @param memberId the memberId to set
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
   * @param firstName the firstName to set
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
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the companyEmail
   */
  public String getCompanyEmail() {
    return this.companyEmail;
  }

  /**
   * @param companyEmail the companyEmail to set
   */
  public void setCompanyEmail(String companyEmail) {
    this.companyEmail = companyEmail;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return this.phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  // product info
  /**
   * @return the productId
   */
  public int getProductId() {
    return this.productId;
  }

  /**
   * @param productId the productId to set
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
   * @param proudctUrl the proudctUrl to set
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
   * @param proudctImage the proudctImage to set
   */
  public void setProudctImage(String proudctImage) {
    this.proudctImage = proudctImage;
  }

  /**
   * @return the productPrice
   */
  public double getProductPrice() {
    return this.productPrice;
  }

  /**
   * @param productPrice the productPrice to set
   */
  public void setProductPrice(double productPrice) {
    this.productPrice = productPrice;
  }

  /**
   * @return the proudctName
   */
  public String getProudctName() {
    return this.productName;
  }

  /**
   * @param proudctName the proudctName to set
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
   * @param proudctDescription the proudctDescription to set
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
   * @param productOptionValue the productOptionValue to set
   */
  public void setProductOptionValue(String productOptionValue) {
    this.productOptionValue = productOptionValue;
  }

  /**
   * @return the productQuantity
   */
  public int getProductQuantity() {
    return this.productQuantity;
  }

  /**
   * @param productQuantity the productQuantity to set
   */
  public void setProductQuantity(int productQuantity) {
    this.productQuantity = productQuantity;
  }

  //order info

  /**
   * @return the subTotal
   */
  public double getSubTotal() {
    return this.subTotal;
  }

  /**
   * @param subTotal the subTotal to set
   */
  public void setSubTotal(double subTotal) {
    this.subTotal = subTotal;
  }

  /**
   * @return the taxType
   */
  public String getTaxType() {
    return this.taxType;
  }

  /**
   * @param taxType the taxType to set
   */
  public void setTaxType(String taxType) {
    this.taxType = taxType;
  }

  /**
   * @return the productName
   */
  public String getProductName() {
    return this.productName;
  }

  /**
   * @param productName the productName to set
   */
  public void setProductName(String productName) {
    this.productName = productName;
  }

  /**
   * @return the paiedSequenceNumber
   */
  public int getPaiedSequenceNumber() {
    return this.paiedSequenceNumber;
  }

  /**
   * @return the taxRate
   */
  public double getTaxRate() {
    return this.taxRate;
  }

  /**
   * @param taxRate the taxRate to set
   */
  public void setTaxRate(double taxRate) {
    this.taxRate = taxRate;
  }

  /**
   * @return the taxAmount
   */
  public double getTaxAmount() {
    return this.taxAmount;
  }

  /**
   * @param taxAmount the taxAmount to set
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
   * @param shipping the shipping to set
   */
  public void setShipping(double shipping) {
    this.shipping = shipping;
  }

  /**
   * @return the feeType
   */
  public String getFeeType() {
    return this.feeType;
  }

  /**
   * @param feeType the feeType to set
   */
  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }

  /**
   * @return the feeAmout
   */
  public double getFeeAmout() {
    return this.feeAmout;
  }

  /**
   * @param fee_amout the feeAmout to set
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
   * @param total the total to set
   */
  public void setTotal(double total) {
    this.total = total;
  }

  //financial info
  /**
   * @return the downPayment
   */
  public double getDownPayment() {
    return this.downPayment;
  }

  /**
   * @param downPayment the downPaymentto set
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
   * @param principal the principal to set
   */
  public void setPrincipal(double principal) {
    this.principal = principal;
  }

  /**
   * @return the apr
   */
  public double getApr() {
    return this.apr;
  }

  /**
   * @param apr the apr to set
   */
  public void setApr(double apr) {
    this.apr = apr;
  }

  /**
   * @return the monthlyPayment
   */
  public double getMonthlyPayment() {
    return this.monthlyPayment;
  }

  /**
   * @param monthlyPayment the monthlyPayment to set
   */
  public void setMonthlyPayment(double monthly_payment) {
    this.monthlyPayment = monthly_payment;
  }

  /**
   * @return the numberOfPayment
   */
  public int getNumberOfPayment() {
    return this.numberOfPayment;
  }

  /**
   * @param numberOfPayment the numberOfPayment to set
   */
  public void setNumberOfPayment(int numberOfPayment) {
    this.numberOfPayment = numberOfPayment;
  }

  /**
   * @return the interest
   */
  public double getInterest() {
    return this.interest;
  }

  /**
   * @param interest the interest to set
   */
  public void setInterest(double interest) {
    this.interest = interest;
  }

  /**
   * @return the paiedSequenceNumber
   */
  public int getPaiedSequenceNumbert() {
    return this.paiedSequenceNumber;
  }

  /**
   * @param paiedSequenceNumber the paiedSequenceNumber to set
   */
  public void setPaiedSequenceNumber(int paiedSequenceNumber) {
    this.paiedSequenceNumber = paiedSequenceNumber;
  }

  /**
   * @return the paidAmount
   */
  public double getPaidAmount() {
    return this.paidAmount;
  }

  /**
   * @param paidAmount the paidAmount to set
   */
  public void setPaidAmount(double paidAmount) {
    this.paidAmount = paidAmount;
  }

  /**
   * @return the ernedInterest
   */
  public double getErnedInterest() {
    return this.ernedInterest;
  }

  /**
   * @param ernedInterest the ernedInterest to set
   */
  public void setErnedInterest(double ernedInterest) {
    this.ernedInterest = ernedInterest;
  }

  /**
   * @return the paymentRecievable
   */
  public double getPaymentRecievable() {
    return this.paymentRecievable;
  }

  /**
   * @param paymentRecievable the paymentRecievable to set
   */
  public void setPaymentRecievable(double paymentRecievable) {
    this.paymentRecievable = paymentRecievable;
  }

  /**
   * @return the remainingBalance
   */
  public double getRemainingBalance() {
    return this.remainingBalance;
  }

  /**
   * @param remainingBalance the remainingBalance to set
   */
  public void setRemainingBalance(double remainingBalance) {
    this.remainingBalance = remainingBalance;
  }

  // address
  /**
   * @return the addresses
   */
  public Set<OrderAddress> getAddresses() {
    return this.addresses;
  }

  /**
   * @param addresses the addresses to set
   */
  public void setAddresses(Set<OrderAddress> addresses) {
    this.addresses = addresses;
  }

  //status
  /**
   * @return the status
   */
  public ORDER_STATUS getStatus() {
    return this.status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(ORDER_STATUS status) {
    this.status = status;
  }
}
