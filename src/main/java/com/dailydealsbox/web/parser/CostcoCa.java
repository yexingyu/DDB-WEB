package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CostcoCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 20;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "product.(\\d+)";
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
    this.name = this.doc.select("h1").first().text();
    ;
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("div.product-detail-tabs").first().text()
        .replace("Product Details Specifications Shipping & Terms Returns/Warranty Reviews ", "")
        .substring(0, 255);
    ;
  }

  @Override
  public void setImage() throws IOException {
    //image
    this.image = this.doc.select("UL#large_images").get(0).select("img").get(0).attr("src");
  }

  @Override
  public void setPrice() throws IOException {
    //price

    this.price = Double.parseDouble(this.doc.select("div.your-price").get(0).text()
        .replace("Your Price $", ""));
  }
}