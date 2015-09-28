package com.dailydealsbox.web.parser.prototype;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * product info
 */
public class CanadianTireCaPT {
  public static void main(String[] args) throws IOException {
    //set url
    String url = "http://www.canadiantire.ca/en/pdp/troy-bilt-208cc-24-in-ohv-snowthrower-0603733p.html";

    //set doc
    Document doc = Jsoup
        .connect(url)
        .userAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30")
        .get();

    //key
    String keyPattern = "-(\\d+)p.html";
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

    //name, description,image,price
    String product_name_text;
    String product_description_text;
    String product_image_text;
    String product_price_text;
    //String product_shipping_text;
    //String product_import_text;

    //name
    product_name_text = doc.select("meta[name=\"title\"]").attr("content");

    //description
    product_description_text = doc.select("meta[property=\"og:description\"]").attr("content");

    //image
    product_image_text = doc.select("link[rel=\"image_src\"]").attr("href");
    ;

    //price

    product_price_text = "0.00";

    //product_shipping_text = doc.select(htmlPath.get("shipping")).first().text();
    //product_import_text = doc.select(htmlPath.get("import")).first().text(); 

    //expiration
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

    //print
    System.out.println("keyString: " + keyString);
    System.out.println("product name: " + product_name_text);
    System.out.println("product description: " + product_description_text);
    System.out.println("product image: " + product_image_text);
    System.out.println("product price: " + product_price_text);
    //System.out.println(product_shipping_text);
    //System.out.println(product_import_text);
    System.out.println("product expiration: " + expiredDate.toString());

    //System.out.println("doc: " + doc.html());

  }
}
