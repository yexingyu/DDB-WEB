package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NordStromCom extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 10;
  }

  @Override
  public void setKey() {

	    //key
	    String keyPattern = "\\/(\\d+)";
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
    this.name = this.doc.select("meta[property=\"og:title\"]").attr("content");
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("meta[property=\"og:description\"]").attr("content");
  }

  @Override
  public void setImage() throws IOException {

    this.image = this.doc.select("div#product-image").get(0).select("img").get(0).attr("src");

    ;
  }

  @Override
  public void setPrice() throws IOException {
    this.price = Double.parseDouble(this.doc.select("span.sale-price").get(0).text().replace("Now: CAD ", ""));
  }

}