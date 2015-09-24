package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeDepotCa extends ProductPage {

  @Override
  public void setStoreId() {
    this.storeId = 7;
  }

  @Override
  public void setKey() {
    // String to be scanned to find the pattern.
    String pattern = "/([0-9]{6})";

    // Create a Pattern object
    Pattern r = Pattern.compile(pattern);

    // Now create matcher object.
    Matcher m = r.matcher(this.url);
    if (m.find()) {
      this.key = m.group(1);

    } else {
      this.key = null;
    }
  }

  @Override
  public void setExpiration() {
    //set product expired date to next Thursday
    Calendar now = Calendar.getInstance();
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday >= Calendar.THURSDAY) {
      //4,5,6 next Thursday
      int days = (Calendar.SATURDAY - weekday + 5);
      now.add(Calendar.DAY_OF_YEAR, days);
    }
    //7,1,2,3 this
    if (weekday < Calendar.THURSDAY) {
      // calculate how much to add
      // the 5 is the difference between Saturday and Thursday
      int days = (Calendar.THURSDAY - weekday);
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
    this.image = "http://" + "www.homedepot.ca"
        + this.doc.select("div.main-product-info").get(0).select("img").get(0).attr("src");
  }

  @Override
  public void setPrice() throws IOException {
    this.price = 0.00;
  }
}