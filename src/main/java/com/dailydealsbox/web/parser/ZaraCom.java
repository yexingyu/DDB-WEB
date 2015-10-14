package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZaraCom extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 27;
  }

  @Override
  public void setKey() {

	    //key
	    String keyPattern = "-([0-9 a-z]*)\\.html";
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
    this.name = this.doc.select("meta[name=\"description\"]").attr("content");
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("p.description").get(0).text();
  }

  @Override
  public void setImage() throws IOException {

    this.image = this.doc.select("img.image-big").get(0).attr("src");

    ;
  }

  @Override
  public void setPrice() throws IOException {
    this.price = Double.parseDouble(doc.select("span.price").get(0).attr("data-price").replace("  CAD", ""));
  }

}