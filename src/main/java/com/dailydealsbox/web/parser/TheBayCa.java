package com.dailydealsbox.web.parser;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TheBayCa extends ProductPage {

  @Override
  public void setKey() {

  }

  @Override
  public void setExpiration() {
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
    this.expiration = now.getTime();

  }

  @Override
  public void setName() throws IOException {
    this.name = this.doc.select("h2.detial").first().text();
  }

  @Override
  public void setDescription() throws IOException {
    this.description = doc.select("div#detial_main_content").first().text();
  }

  @Override
  public void setImage() throws IOException {
    //image
    String product_image_text;
    product_image_text = doc.select("div#gallery_main1").get(0).select("script").html();
    String image_pattern = "(http.*)\"";
    String imageUrl;

    // Create a Pattern object
    Pattern r = Pattern.compile(image_pattern);

    // Now create matcher object.
    Matcher m = r.matcher(product_image_text);
    if (m.find()) {
      imageUrl = m.group(1);

    } else {
      imageUrl = null;
    }

    this.image = imageUrl;
  }

  @Override
  public void setPrice() throws IOException {
    this.price = 0.00;
  }
}