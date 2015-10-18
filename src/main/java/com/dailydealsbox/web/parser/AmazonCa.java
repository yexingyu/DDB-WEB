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
    //String keyPattern = "dp/(.*)/";
    String keyPattern = "product/(.*)\\?";

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

    keyPattern = "dp/(.*)/";
    r = Pattern.compile(keyPattern);

    //matcher
    m = r.matcher(url);
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
    productPriceText = "";
    try {
      productPriceText = this.doc.select("span#priceblock_ourprice").text();
    } catch (IndexOutOfBoundsException e) {

      try {
        productPriceText = this.doc.select("span#priceblock_dealprice").text();
      } catch (IndexOutOfBoundsException e1) {

        try {
          productPriceText = this.doc.select("span#priceblock_saleprice").text();
        } catch (IndexOutOfBoundsException e3) {}
      }
    }

    this.price = Double.parseDouble(productPriceText.replace("CDN$ ", ""));
  }

  @Override
  public void setRating() {
    //rating
    String ratingPattern = "(\\d.\\d) out of 5 stars";
    String ratingString = "";

    String product_rating = this.doc.html();
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

    this.rating = Double.parseDouble(ratingString);
  }

  @Override
  public void setReviewsCount() {
    //product review number
    String product_review_number = this.doc.html();
    ;

    //reviews
    String reviewsPattern = "(\\d+) reviews";
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

    this.reviewsCount = Integer.parseInt(reviewsString);
  }
}