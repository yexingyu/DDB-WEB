package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 12;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "dp/(.*)/";
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
    this.name = this.doc.select("meta[name=\"title\"]").attr("content");
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("meta[name=\"description\"]").attr("content");
  }

  @Override
  public void setImage() throws IOException {
    String product_image_text = this.doc.select("div#imgTagWrapperId").first().select("img")
        .first().attr("data-a-dynamic-image");

    //image
    String imagePattern = "([^\\[]+)\":";
    String imageString = "";
    //pattern
    Pattern r = Pattern.compile(imagePattern);

    //matcher
    Matcher m = r.matcher(product_image_text);
    if (m.find()) {
      imageString = m.group(1);

    } else {
      imageString = null;
    }
    ;

    this.image = imageString.replace("{\"", "");

    ;
  }

  @Override
  public void setPrice() throws IOException {
    String productPriceText;
    try {
      productPriceText = this.doc.select("span#priceblock_ourprice").text();
    } catch (IndexOutOfBoundsException e) {
      productPriceText = "";
    }

    try {
      productPriceText = this.doc.select("span#priceblock_dealprice").text();
    } catch (IndexOutOfBoundsException e) {
      productPriceText = "";
    }

    this.price = Double.parseDouble(productPriceText.replace("CDN$ ", ""));
  }
}