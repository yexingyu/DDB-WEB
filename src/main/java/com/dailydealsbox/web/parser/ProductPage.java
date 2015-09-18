package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;

import com.dailydealsbox.web.configuration.BaseEnum.CURRENCY;

public class ProductPage {
  //product status
  public boolean                   active;

  //page info
  public String                    url;
  public String                    domainName;
  public String                    key;
  private org.jsoup.nodes.Document doc;
  public Date                      expirition;

  //store info
  public int                       storeId;
  public CURRENCY                  currency;

  //HTML path - product info
  public String                    htmlPathName;
  public String                    htmlPathDescription;
  public String                    htmlPathImage;
  public String                    htmlPathPrice;

  //HTML path - fees
  public String                    htmlPathShipping;
  public String                    htmlPathEco;
  public String                    htmlPathImport;

  //parsing result
  public String                    name;
  public String                    description;
  public String                    image;
  public String                    price;

  public double                    shippingFee;
  public double                    ecoFee;
  public double                    importFee;

  public ProductPage() {

  }

  public ProductPage(int storeId, CURRENCY currency) {
    this.storeId = storeId;
    this.currency = currency;
  }

  // URL  
  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // domainName  
  public String getDomainName() {
    return this.domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  // key  
  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  // doc  
  public org.jsoup.nodes.Document getDoc() {
    return this.doc;
  }

  public void setDoc(org.jsoup.nodes.Document doc) throws IOException {
    this.doc = Jsoup.connect(this.url).get();
    ;
  }

  // htmlPathName  
  public String getHtmlPathName() {
    return this.htmlPathName;
  }

  public void setHtmlPathName(String htmlPathName) {
    this.htmlPathName = htmlPathName;
  }

  // htmlPathDescription  
  public String getHtmlPathDescription() {
    return this.htmlPathDescription;
  }

  public void setHtmlPathDescription(String htmlPathDescription) {
    this.htmlPathDescription = htmlPathDescription;
  }

  // htmlPathImage  
  public String getHtmlPathImage() {
    return this.htmlPathImage;
  }

  public void setHtmlPathImage(String htmlPathImage) {
    this.htmlPathImage = htmlPathImage;
  }

  // htmlPathPrice  
  public String getHtmlPathPrice() {
    return this.htmlPathPrice;
  }

  public void setHtmlPathPrice(String htmlPathPrice) {
    this.htmlPathPrice = htmlPathPrice;
  }

  // name  
  public String getName() {
    return this.name;
  }

  public void setName() throws IOException {}

  // description  
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  // images 
  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  // price  
  public String getPirce() {
    return this.price;
  }

  public void setPirce(String price) {
    this.price = price;
  }
}