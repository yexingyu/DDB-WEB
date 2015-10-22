package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BestBuyCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 11;
  }

  @Override
  public void setKey() {

    //key
    String keyPattern = "\\/(\\d+).aspx";
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
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("meta[property=og:description]").attr("content");
  }

  @Override
  public void setImage() throws IOException {

    this.image = this.doc.select("meta[property=og:image]").attr("content");

    ;
  }

  @Override
  public void setPrice() throws IOException {

    this.price = Double.parseDouble(this.doc.select("div.prodprice").first().text()
        .replace("$", ""));
  }

  @Override
  public void setRating() {
    //product rating
    this.rating = Double.parseDouble(this.doc.select("div.rating-score").first().html());
  }

  @Override
  public void setReviewsCount() {
    //product review number
    String product_review_number = doc.select("div.rating-num").first().html();
    //reviews
    String reviewsPattern = "(\\d)+ ratings";
    String reviewsString = "";
    //pattern
    Pattern r = Pattern.compile(reviewsPattern);

    //matcher
    Matcher m = r.matcher(product_review_number);
    if (m.find()) {
      reviewsString = m.group(1);

    } else {
      reviewsString = null;
    }
    ;
    product_review_number = reviewsString;

    this.reviewsCount = Integer.parseInt(reviewsString);
  }
}