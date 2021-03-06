package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;

import com.dailydealsbox.web.configuration.BaseEnum.CURRENCY;

public class ProductPage {
  //product status
  public boolean                  active;
  public boolean                  existing;

  //page info
  public String                   url;
  public String                   domainName;
  public String                   key;
  public Date                     expiration;

  //store info
  public int                      storeId;
  public CURRENCY                 currency;

  public org.jsoup.nodes.Document doc;

  //parsing result **********************************************

  //parsing result - meta
  public String                   metaName;
  public String                   metaDescription;
  public String                   metaKeywords;

  //parsing result - product info
  public String                   name;
  public String                   description;
  public String                   image;
  public double                   price;

  //parsing result - rating
  public double                   rating;
  public int                      reviewsCount;

  //parsing result - fees
  public double                   shippingFee;
  public double                   ecoFee;
  public double                   importFee;

  public ProductPage() {

  }

  //constructor
  public ProductPage(String productPageUrl, int storeId, CURRENCY currency) {
    this.setUrl(productPageUrl);
    this.storeId = storeId;
    this.currency = currency;
  }

  // active  
  public boolean getActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  // existing  
  public boolean getExisting() {
    return this.existing;
  }

  public void setExisting(boolean existing) {
    this.existing = existing;
  }

  //page info - URL  
  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  //page info - domainName  
  public String getDomainName() {
    return this.domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  //page info - key  
  public String getKey() {
    return this.key;
  }

  public void setKey() {
    this.key = "";
  }

  //page info - expiration  
  public Date getExpiration() {
    return this.expiration;
  }

  public void setExpiration() {
    //set expiration to next 2 days
    Calendar now = Calendar.getInstance();
    int days = 2;
    now.add(Calendar.DAY_OF_YEAR, days);

    Date expiredDate = now.getTime();
    this.expiration = expiredDate;
  }

  //page info - rating  
  public double getRating() {
    return this.rating;
  }

  public void setRating() {
    this.rating = 0;
  }

  //page info - reviewsCount 
  public int getReviewsCount() {
    return this.reviewsCount;
  }

  public void setReviewsCount() {
    this.reviewsCount = 0;
  }

  //store info - storeId  
  public int getStoreId() {
    return this.storeId;
  }

  public void setStoreId() {
    this.storeId = 0;
  }

  //store info - currency  
  public CURRENCY getCurrency() {
    return this.currency;
  }

  public void setCurrency(CURRENCY currency) {
    this.currency = currency;
  }

  // doc  
  public org.jsoup.nodes.Document getDoc() {
    return this.doc;
  }

  public void setDoc() throws IOException {

    try {
      this.doc = Jsoup
          .connect(this.url)
          .userAgent(
              "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30")
          .get();
    } catch (IOException e) {
      e.printStackTrace();
      this.doc = null;
    }
    ;
  }

  //parsing result **********************************************
  // result - metaName 
  public String getMetaName() {
    return this.description;
  }

  public void setMetaName(String metaName) {
    this.metaName = metaName;
  }

  // result - metaDescription 
  public String getMetaDescription() {
    return this.description;
  }

  public void setMetaDescription(String metaDescription) {
    this.metaDescription = metaDescription;
  }

  // result - metaKeywords 
  public String getMetaKeywords() {
    return this.description;
  }

  public void setMetaKeywords(String metaKeywords) {
    this.metaKeywords = metaKeywords;
  }

  // name  
  public String getName() {
    return this.name;
  }

  public void setName() throws IOException {
    this.name = "";
  }

  // result - description  
  public String getDescription() {
    return this.description;
  }

  public void setDescription() throws IOException {
    this.description = "";
  }

  //  result - images 
  public String getImage() {
    return this.image;
  }

  public void setImage() throws IOException {
    this.image = "";
  }

  //  result - price  
  public double getPrice() {
    return this.price;
  }

  public void setPrice() throws IOException {
    this.price = 0.00;
  }

}