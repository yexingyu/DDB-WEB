package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrownsShoesCom extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 22;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "pid=(\\d+)";
    ;
    String keyString = "";
    //pattern
    Pattern r = Pattern.compile(keyPattern);

    //matcher
    Matcher m = r.matcher(url);
    if (m.find()) {
      keyString = m.group(1);

    } else {
      keyString = null;
    }
    ;

    this.key = keyString;
  }

  @Override
  public void setName() throws IOException {
    String productBrand;
    productBrand = this.doc.select("span.last").get(0).text();

    try {
      this.name = productBrand + " " + this.doc.select("h2.product-name").first().text();
    } catch (NullPointerException e) {}
    this.name = "";
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("div.product-main-attributes").first().text();
  }

  @Override
  public void setImage() throws IOException {
    //image
    this.image = this.doc.select("div#thumbnails").get(0).select("img").attr("src")
        .replace("?sh=100", "?sh=250");
    ;
  }

  @Override
  public void setPrice() throws IOException {
    String product_price_text;
    String priceString;
    try {
      product_price_text = this.doc.select("span.price-sales").get(0).text();
    } catch (IndexOutOfBoundsException e) {
      product_price_text = "";
    }

    try {
      product_price_text = this.doc.select("span.price-end").get(0).text();
    } catch (IndexOutOfBoundsException e) {
      product_price_text = "";
    }
    try {
      product_price_text = this.doc.select("span.price-standard").get(0).text();
    } catch (IndexOutOfBoundsException e) {
      product_price_text = "";
    }

    String pricePattern = "\\$(.+)";
    //pattern
    Pattern r = Pattern.compile(pricePattern);

    //matcher
    Matcher m = r.matcher(product_price_text);
    if (m.find()) {
      priceString = m.group(1);

    } else {
      priceString = null;
    }
    ;
    this.price = Double.parseDouble(priceString);
  }
}