package com.dailydealsbox.web.parser;

public class ProductPage {
  //page info
  private String url;
  private String domainName;
  private String key;
  private String doc;

  //html path info
  private String htmlPathName;
  private String htmlPathDescription;
  private String htmlPathImage;
  private String htmlPathPrice;

  //parsing result
  private String name;
  private String description;
  private String iamge;
  private String price;

  // url  
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
  public String getDoc() {
    return this.doc;
  }

  public void setDoc(String doc) {
    this.doc = doc;
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

  public void setName(String name) {
    this.name = name;
  }

  // description  
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  // iamge  
  public String getIamge() {
    return this.iamge;
  }

  public void setIamge(String iamge) {
    this.iamge = iamge;
  }

  // price  
  public String getPirce() {
    return this.price;
  }

  public void setPirce(String price) {
    this.price = price;
  }
}