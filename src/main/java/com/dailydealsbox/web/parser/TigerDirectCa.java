package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TigerDirectCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 30;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "EdpNo=(\\d+)";
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
    this.name = this.doc.select("meta[property=og:title]").attr("content");
    ;
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("meta[name=\"description\"]").attr("content");
  }

  @Override
  public void setImage() throws IOException {

    this.image = this.doc.select("img#detailImg").first().attr("src");

    ;
  }

  @Override
  public void setPrice() throws IOException {
    try {
      this.price = Double.parseDouble(this.doc.select("meta[itemprop=\"price\"]").first()
          .attr("content"));
    } catch (IndexOutOfBoundsException e) {
      this.price = 0.00;
    }
  }

  @Override
  public void setRating() {
    try {
      this.rating = Double.parseDouble(this.doc.select("span.piRatingVal").first().text());
    } catch (NullPointerException e) {
      this.rating = 0.0;
    }
  }

  @Override
  public void setReviewsCount() {
    try {
      this.reviewsCount = Integer.parseInt(this.doc.select("a.piReadRev").first().text()
          .replace("Read reviews (", "").replace(")", ""));
    } catch (NullPointerException e) {
      this.reviewsCount = 0;
    }
  }
}
