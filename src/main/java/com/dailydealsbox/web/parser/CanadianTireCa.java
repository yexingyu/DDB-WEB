package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CanadianTireCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 18;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "-(\\d+)p.";
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
    this.name = this.doc.select("meta[name=\"title\"]").attr("content")
        .replace(" | Canadian Tire", "");
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("meta[property=\"og:description\"]").attr("content")
        .replace(" | Canadian Tire", "");
    ;
  }

  @Override
  public void setImage() throws IOException {
    //image
    this.image = this.doc.select("link[rel=\"image_src\"]").attr("href");
    ;
    ;
  }

  @Override
  public void setPrice() throws IOException {
    //price

    this.price = 0.00;
  }
}