package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaplesCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 28;
  }

  @Override
  public void setKey() {

	    //key
	    String keyPattern = "product_(\\d+)_";
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
    this.name = this.doc.select("meta[property=\"og:title\"]").attr("content");;
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("div.copyBullets").get(0).text();
  }

  @Override
  public void setImage() throws IOException {

    this.image = this.doc.select("meta[property=\"og:image\"]").attr("content");

    ;
  }

  @Override
  public void setPrice() throws IOException {
    this.price = Double.parseDouble(this.doc.select("span.finalPrice").get(0).text().replace("*", "").replace("$", ""));
  }

}