package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GapCanadaCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 16;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "pid=(\\d+)";
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
    this.name = this.doc.select("h1").get(0).text();
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("div#tabWindow").get(0).text();
  }

  @Override
  public void setImage() throws IOException {
    //image
    this.image = "";
    ;
  }

  @Override
  public void setPrice() throws IOException {
    //price
    String product_price_text = doc.html();

    String pricePattern = "CA\\$(\\d+.\\d+)";
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