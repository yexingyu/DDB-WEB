package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class SephoraCom extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 23;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "skuId=(\\d+)";
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
  public void setDoc() throws IOException {

    try {
      this.doc = Jsoup.connect(this.url).get();
    } catch (IOException e) {
      e.printStackTrace();
      this.doc = null;
    }
    ;
  }

  @Override
  public void setName() throws IOException {
    this.name = this.doc.select("meta[itemprop=\"name\"]").attr("content");
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("meta[property=\"og:description\"]").attr("content");
  }

  @Override
  public void setImage() throws IOException {
    //image
    this.image = this.doc.select("meta[itemprop=\"image\"]").attr("content");
    ;
  }

  @Override
  public void setPrice() throws IOException {
    //price

    String pricePattern = "\"price\":\"(\\d+.\\d+)\"";
    String priceString = "";
    //pattern
    Pattern r = Pattern.compile(pricePattern);

    //mathcer
    Matcher m = r.matcher(this.doc.html());
    if (m.find()) {
      priceString = m.group(1);

    } else {
      priceString = "0.00";
    }
    ;
    this.price = Double.parseDouble(priceString);
  }
}