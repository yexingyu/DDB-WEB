package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdidasCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 15;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "/([A-Z 0-9]+).html";
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
    this.name = this.doc.select("meta[property=\"og:title\"]").attr("content")
        .replace(" | adidas Canada", "");
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("meta[property=\"og:description\"]").attr("content")
        .replace(" | adidas Canada", "");
    ;
  }

  @Override
  public void setImage() throws IOException {
    //image
    this.image = this.doc.select("meta[property=\"og:image\"]").attr("content");
    ;
    ;
  }

  @Override
  public void setPrice() throws IOException {
    //price
    String product_price_text = doc.html();

    String pricePattern = "data-sale-price=\"(\\d+.\\d+)\"";
    String priceString = "";
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