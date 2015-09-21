package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import com.dailydealsbox.web.configuration.BaseEnum.CURRENCY;

public class ProductPage {
  //product status
  public boolean                   active;
  public boolean                   existing;

  //page info
  public URL                       url;
  public String                    domainName;
  public String                    key;
  public Date                      expiration;

  //store info
  public int                       storeId;
  public CURRENCY                  currency;
  //HTML path **********************************************
  //HTML path - meta
  public String                    htmlPathMetaName;
  public String                    htmlPathMetaDescription;
  public String                    htmlPathMetaKeywords;

  //HTML path - product info
  public String                    htmlPathName;
  public String                    htmlPathDescription;
  public String                    htmlPathImage;
  public String                    htmlPathPrice;

  //HTML path - fees
  public String                    htmlPathShippingFee;
  public String                    htmlPathEcoFee;
  public String                    htmlPathImportFee;

  //jsoup object **********************************************
  //jsoup object - meta
  private org.jsoup.nodes.Element  elementMetaName;
  private org.jsoup.nodes.Element  elementMetaDescription;
  private org.jsoup.nodes.Element  elementMetaKeywords;

  //jsoup object - product info
  private org.jsoup.nodes.Document doc;
  private org.jsoup.nodes.Element  elementName;
  private org.jsoup.nodes.Element  elementDescription;
  private org.jsoup.nodes.Element  elementPrice;
  private org.jsoup.nodes.Element  elementImage;

  //jsoup object - fees
  private org.jsoup.nodes.Element  elementShippingFee;
  private org.jsoup.nodes.Element  elementEcoFee;
  private org.jsoup.nodes.Element  elementImportFee;

  // index **********************************************
  //index - meta
  private int                      indexMetaName;
  private int                      indexMetaDescription;
  private int                      indexMetaKeywords;

  //index - product info
  private int                      indexName;
  private int                      indexDescription;
  private int                      indexPrice;
  private int                      indexImage;

  //index - fees
  private int                      indexShippingFee;
  private int                      indexEcoFee;
  private int                      indexImportFee;
  //parsing result **********************************************
  //parsing result - meta
  public String                    metaName;
  public String                    metaDescription;
  public String                    metaKeywords;

  //parsing result - product info
  public String                    name;
  public String                    description;
  public String                    image;
  public String                    price;

  //parsing result - fees
  public double                    shippingFee;
  public double                    ecoFee;
  public double                    importFee;

  public ProductPage() {

  }

  //constructor
  public ProductPage(URL pageUrl, int storeId, CURRENCY currency) {
    this.setUrl(pageUrl);
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
  public URL getUrl() {
    return this.url;
  }

  public void setUrl(URL url) {
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
    // String to be scanned to find the pattern.
    String pattern = "\\/product\\/(\\w+)\\/ref";

    // Create a Pattern object
    Pattern r = Pattern.compile(pattern);

    // Now create matcher object.
    Matcher m = r.matcher(url.toString());
    if (m.find()) {
      this.key = m.group(1);

    } else {
      this.key = null;
    }
  }

  //page info - expiration  
  public Date getExpiration() {
    return this.expiration;
  }

  public void setExpiration(Date expiration) {
    this.expiration = expiration;
  }

  //store info - storeId  
  public int getStoreId() {
    return this.storeId;
  }

  public void setStoreId(int storeId) {
    this.storeId = storeId;
  }

  //store info - currency  
  public CURRENCY getCurrency() {
    return this.currency;
  }

  public void setCurrency(CURRENCY currency) {
    this.currency = currency;
  }

  // html get set **********************************************
  // htmlPathMetaName  
  public String getHtmlPathMetaName() {
    return this.htmlPathMetaName;
  }

  public void setHtmlPathMetaName(String htmlPathMetaName) {
    this.htmlPathMetaName = htmlPathMetaName;
  }

  // htmlPathMetaDescription  
  public String getHtmlPathMetaDescription() {
    return this.htmlPathMetaDescription;
  }

  public void setHtmlPathMetaDescription(String htmlPathMetaDescription) {
    this.htmlPathMetaDescription = htmlPathMetaDescription;
  }

  // htmlPathMetaKeywords  
  public String getHtmlPathMetaKeywords() {
    return this.htmlPathMetaKeywords;
  }

  public void setHtmlPathMetaKeywords(String htmlPathMetaKeywords) {
    this.htmlPathMetaKeywords = htmlPathMetaKeywords;
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

  // **********************************************
  // index - metaName  
  public int getIndexMetaName() {
    return this.indexMetaName;
  }

  public void setIndexMetaName(int indexMetaName) {
    this.indexMetaName = indexMetaName;
  }

  // index - metaDescription  
  public int getIndexMetaDescription() {
    return this.indexMetaDescription;
  }

  public void setIndexMetaDescription(int indexMetaDescription) {
    this.indexMetaDescription = indexMetaDescription;
  }

  // index - metaKeywords  
  public int getIndexMetaKeywords() {
    return this.indexMetaKeywords;
  }

  public void setIndexMetaKeywords(int indexMetaKeywords) {
    this.indexMetaKeywords = indexMetaKeywords;
  }

  // index - name  
  public int getIndexName() {
    return this.indexName;
  }

  public void setIndexName(int indexName) {
    this.indexName = indexName;
  }

  // index - description  
  public int getIndexDescription() {
    return this.indexDescription;
  }

  public void setIndexDescription(int indexDescription) {
    this.indexDescription = indexDescription;
  }

  // index - price  
  public int getIndexPrice() {
    return this.indexPrice;
  }

  public void setIndexPrice(int indexPrice) {
    this.indexPrice = indexPrice;
  }

  // index - image 
  public int getIndexImage() {
    return this.indexImage;
  }

  public void setIndexImage(int indexImage) {
    this.indexImage = indexImage;
  }

  // index - shippingFee 
  public int getIndexShippingFee() {
    return this.indexShippingFee;
  }

  public void setIndexShippingFee(int indexShippingFee) {
    this.indexShippingFee = indexShippingFee;
  }

  // index - ecoFee 
  public int getIndexEcoFee() {
    return this.indexEcoFee;
  }

  public void setIndexEcoFee(int indexEcoFee) {
    this.indexEcoFee = indexEcoFee;
  }

  // index - importFee 
  public int getIndexImportFee() {
    return this.indexImportFee;
  }

  public void setIndexImportFee(int indexImportFee) {
    this.indexImportFee = indexImportFee;
  }

  //jsoup element **********************************************
  // doc  
  public org.jsoup.nodes.Document getDoc() {
    return this.doc;
  }

  public void setDoc() throws IOException {

    try {
      this.doc = Jsoup.connect(this.url.toString()).get();
    } catch (IOException e) {
      e.printStackTrace();
      this.doc = null;
    }
    ;
  }

  //elementMetaName
  public org.jsoup.nodes.Element getElementMetaName() {
    return this.elementMetaName;
  }

  public void setElementMetaName() throws IOException {
    this.elementMetaName = this.doc.select(this.htmlPathMetaName).get(this.indexMetaName);
  }

  //elementMetaDescription
  public org.jsoup.nodes.Element getElementMetaDescription() {
    return this.elementMetaDescription;
  }

  public void setElementMetaDescription() throws IOException {
    this.elementMetaDescription = this.doc.select(this.htmlPathMetaDescription).get(
        this.indexMetaDescription);
  }

  //elementMetaKeywords
  public org.jsoup.nodes.Element getElementMetaKeywords() {
    return this.elementMetaKeywords;
  }

  public void setElementMetaKeywords() throws IOException {
    this.elementMetaKeywords = this.doc.select(this.htmlPathMetaKeywords).get(
        this.indexMetaKeywords);
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
    this.elementName.text();

  }

  // result - description  
  public String getDescription() {
    return this.description;
  }

  public void setDescription() {
    this.description = this.elementDescription.text();
  }

  //  result - images 
  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  //  result - price  
  public String getPirce() {
    return this.price;
  }

  public void setPirce(String price) {
    this.price = price;
  }

}