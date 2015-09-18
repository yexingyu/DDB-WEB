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
  public String                    htmlPathShippingFee;
  public String                    htmlPathEcoFee;
  public String                    htmlPathImportFee;

  //parsing result
  public String                    name;
  public String                    description;
  public String                    image;
  public String                    price;

  public double                    shippingFee;
  public double                    ecoFee;
  public double                    importFee;

  //jsoup object
  private org.jsoup.nodes.Document doc;
  private org.jsoup.nodes.Element  elementName;
  private org.jsoup.nodes.Element  elementDescription;
  private org.jsoup.nodes.Element  elementPrice;
  private org.jsoup.nodes.Element  elementImage;
  private org.jsoup.nodes.Element  elementShippingFee;
  private org.jsoup.nodes.Element  elementEcoFee;
  private org.jsoup.nodes.Element  elementImportFee;

  private int                      indexName;
  private int                      indexDescription;
  private int                      indexPrice;
  private int                      indexImage;
  private int                      indexShippingFee;
  private int                      indexEcoFee;
  private int                      indexImportFee;

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

  public void setName() throws IOException {
    this.doc.select(this.htmlPathName).first().select("h1").first().text();

  }

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

  // doc  
  public org.jsoup.nodes.Document getDoc() {
    return this.doc;
  }

  public void setDoc(org.jsoup.nodes.Document doc) throws IOException {
    this.doc = Jsoup.connect(this.url).get();
    ;
  }

  // elementName 
  public org.jsoup.nodes.Element getElementName() {
    return this.elementName;
  }

  public void setElementName() throws IOException {
    this.elementName = this.doc.select(this.htmlPathName).get(this.indexName);
  }

  // elementDescription 
  public org.jsoup.nodes.Element getElementDescription() {
    return this.elementDescription;
  }

  public void setElementDescription() throws IOException {
    this.elementDescription = this.doc.select(this.htmlPathDescription).get(this.indexDescription);
  }

  // elementPrice 
  public org.jsoup.nodes.Element getElementPrice() {
    return this.elementPrice;
  }

  public void setElementPrice() throws IOException {
    this.elementPrice = this.doc.select(this.htmlPathPrice).get(this.indexPrice);
  }

  // elementImage 
  public org.jsoup.nodes.Element getElementImage() {
    return this.elementImage;
  }

  public void setElementImage() throws IOException {
    this.elementImage = this.doc.select(this.htmlPathImage).get(this.indexImage);
  }

  // elementShippingFee 
  public org.jsoup.nodes.Element getElementShippingFee() {
    return this.elementShippingFee;
  }

  public void setElementShippingFee() throws IOException {
    this.elementShippingFee = this.doc.select(this.htmlPathShippingFee).get(this.indexShippingFee);
  }

  // elementEcoFee 
  public org.jsoup.nodes.Element getElementEcoFee() {
    return this.elementEcoFee;
  }

  public void setElementEcoFee() throws IOException {
    this.elementEcoFee = this.doc.select(this.htmlPathEcoFee).get(this.indexEcoFee);
  }

  // elementImportFee 
  public org.jsoup.nodes.Element getElementImportFee() {
    return this.elementImportFee;
  }

  public void setElementImportFee() throws IOException {
    this.elementImportFee = this.doc.select(this.htmlPathImportFee).get(this.indexImportFee);
  }

}