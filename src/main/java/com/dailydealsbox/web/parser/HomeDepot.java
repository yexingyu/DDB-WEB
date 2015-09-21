package com.dailydealsbox.web.parser;

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
}