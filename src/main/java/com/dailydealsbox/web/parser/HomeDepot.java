package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class HomeDepot extends ProductPage {

  @Override
  public void setExpiration(Date expiration) {

    //set product expired date
    Calendar now = Calendar.getInstance();
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.THURSDAY) {
      // calculate how much to add
      // the 5 is the difference between Saturday and Thursday
      int days = (Calendar.SATURDAY - weekday + 5);
      now.add(Calendar.DAY_OF_YEAR, days);
    }
    // now is the date you want
    Date expiredDate = now.getTime();
    this.expiration = expiredDate;

  }

  @Override
  public void setName() throws IOException {
    this.name = this.doc.select("h1").first().text();

  }

  @Override
  public void setDescription() throws IOException {
    this.description = this.doc.select("div.overview").first().text();

  }

  @Override
  public void setImage() throws IOException {
    this.image = "www.homedepot.ca"
        + this.doc.select("div.main-product-info").get(0).select("img").get(0).attr("src");
  }

  @Override
  public void setPrice() throws IOException {
    this.price = 0.00;
  }
}