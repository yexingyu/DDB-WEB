package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WalmartCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 12;
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
    this.name = this.doc.select("meta[property=og:title]").attr("content");
  }

  @Override
  public void setDescription() throws IOException {

    this.description = this.doc.select("meta[property=og:description]").attr("content");
  }

  @Override
  public void setImage() throws IOException {

    this.image = this.doc.select("DIV.centered-img-wrap").first().select("img").first().attr("src");

    ;
  }

  @Override
  public void setPrice() throws IOException {

    this.price = Double.parseDouble(this.doc.select("div.pricing-shipping").first()
        .select("div.microdata-price").first().select("span").first().text().replace("$", ""));
  }

  @Override
  public void setRating() {
    //product rating
    String product_rating = doc.select("div#product-desc").get(0).select("ul.rating").get(0)
        .select("li").get(0).html();

    //rating
    String ratingPattern = "data-analytics-value=\"(\\d_\\d)\"";
    String ratingString = "";
    //pattern
    Pattern r = Pattern.compile(ratingPattern);

    //matcher
    Matcher m = r.matcher(product_rating);
    if (m.find()) {
      ratingString = m.group(1);

    } else {
      ratingString = null;
    }
    ;

    this.rating = Double.parseDouble(ratingString.replace("_", "."));
  }

  @Override
  public void setReviewsCount() {
    //product review number
    String product_review_number = doc.select("div#product-desc").get(0).html();
    //reviews
    String reviewsPattern = "(\\d+) Reviews";
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
    try {
    this.reviewsCount = Integer.parseInt(reviewsString);
    } catch (NumberFormatException e ) {
    	this.reviewsCount =0;
    }
  }
}