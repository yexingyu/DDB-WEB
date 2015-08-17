/**
 *
 */
package com.dailydealsbox.database.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.dailydealsbox.configuration.BaseEnum.CURRENCY;
import com.dailydealsbox.configuration.BaseEnum.LANGUAGE;
import com.dailydealsbox.configuration.BaseEnum.PRODUCT_TAX_TITLE;
import com.dailydealsbox.configuration.BaseEnum.PRODUCT_TAX_TYPE;
import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.ProductImage;
import com.dailydealsbox.database.model.ProductPrice;
import com.dailydealsbox.database.model.ProductTax;
import com.dailydealsbox.database.model.ProductText;
import com.dailydealsbox.database.model.Store;
import com.dailydealsbox.database.service.SpiderService;

/**
 * @author x_ye
 */
@Service
public class SpiderServiceImpl implements SpiderService {
  private Map<String, String> HTML_PATCH_BESTBUY = new HashMap<>();

  /*
   * Constructor
   */
  public SpiderServiceImpl() {
    // HTML_PATCH_BESTBUY
    this.HTML_PATCH_BESTBUY.put("name", "SPAN#ctl00_CP_ctl00_PD_lblProductTitle");
    this.HTML_PATCH_BESTBUY.put("description", "DIV.tab-overview-item");
    this.HTML_PATCH_BESTBUY.put("image", "IMG#ctl00_CP_ctl00_PD_PI_IP");
    this.HTML_PATCH_BESTBUY.put("price", "SPAN.amount");
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.SpiderService#getProductFromBestbuy(java.lang.String)
   */
  @Override
  public Product getProduct(String url) {
    // parse url
    URL oUrl;
    try {
      oUrl = new URL(StringUtils.lowerCase(url));
    } catch (MalformedURLException e1) {
      e1.printStackTrace();
      return null;
    }

    // init some properties by emptySet
    Product product = new Product();
    if (product.getTexts() == null) {
      product.setTexts(new HashSet<ProductText>());
    }
    if (product.getPrices() == null) {
      product.setPrices(new HashSet<ProductPrice>());
    }
    if (product.getImages() == null) {
      product.setImages(new HashSet<ProductImage>());
    }
    if (product.getTaxes() == null) {
      product.setTaxes(new HashSet<ProductTax>());
    }

    // spider page based on host.
    // if no match, return an empty product.
    String host = oUrl.getHost();
    switch (host) {
      case "www.ebay.ca":
        this.getProductFromEbayCA(oUrl, product, LANGUAGE.EN);
        this.getProductFromEbayCA(oUrl, product, LANGUAGE.FR);
        break;
      case "www.ebay.com":
        this.getProductFromEbayUS(oUrl, product, LANGUAGE.EN);
        break;
      case "www.bestbuy.ca":
        this.getProductFromBestbuyCA(oUrl, product, LANGUAGE.EN);
        this.getProductFromBestbuyCA(oUrl, product, LANGUAGE.FR);
        break;
      case "www.bestbuy.com":
        this.getProductFromBestbuyUS(oUrl, product, LANGUAGE.EN);
        break;
      case "www.amazon.ca":
        this.getProductFromAmazonCA(oUrl, product, LANGUAGE.EN);
        this.getProductFromAmazonCA(oUrl, product, LANGUAGE.FR);
        break;
      case "www.amazon.com":
        this.getProductFromAmazonUS(oUrl, product, LANGUAGE.EN);
        break;
      default:
        break;
    }

    return product;
  }

  /**
   * getProductFromEbayUS
   *
   * @param url
   * @param product
   * @param language
   */
  private void getProductFromEbayUS(URL url, Product product, LANGUAGE language) {

  }

  /**
   * getProductFromEbayCA
   *
   * @param url
   * @param product
   * @param language
   */
  private void getProductFromEbayCA(URL url, Product product, LANGUAGE language) {

  }

  /**
   * getProductFromAmazonUS
   *
   * @param url
   * @param product
   * @param language
   */
  private void getProductFromAmazonUS(URL url, Product product, LANGUAGE language) {

  }

  /**
   * getProductFromAmazonCA
   *
   * @param url
   * @param product
   * @param language
   */
  private void getProductFromAmazonCA(URL url, Product product, LANGUAGE language) {

  }

  /**
   * getProductFromBestbuyUS
   *
   * @param url
   * @param product
   * @param language
   */
  private void getProductFromBestbuyUS(URL url, Product product, LANGUAGE language) {

  }

  /**
   * getProductKeyFromBestbuyCA
   *
   * @param url
   * @return
   */
  private String getProductKeyFromBestbuyCA(URL url) {
    // String to be scanned to find the pattern.
    String pattern = "\\/(\\d+).aspx";

    // Create a Pattern object
    Pattern r = Pattern.compile(pattern);

    // Now create matcher object.
    Matcher m = r.matcher(url.toString());
    if (m.find()) {
      return m.group(1);

    } else {
      return null;
    }
  }

  /**
   * getProductFromBestbuyCA
   *
   * @param url
   * @param product
   * @param language
   * @return
   */
  private Product getProductFromBestbuyCA(URL url, Product product, LANGUAGE language) {
    //set product url
    product.setUrl(url.toString());
    //set product status
    product.setDisabled(false);
    //set product key
    product.setKey(this.getProductKeyFromBestbuyCA(url));

    //set product store
    Store store = new Store();
    int storeID = 11;
    store.setId(storeID);
    product.setStore(store);
    
    //set product expired date
    Calendar now = Calendar.getInstance();
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.THURSDAY)
    {
        // calculate how much to add
        // the 5 is the difference between Saturday and Thursday
        int days = (Calendar.SATURDAY - weekday + 5);
        now.add(Calendar.DAY_OF_YEAR, days);
    }
    // now is the date you want
    Date expiredDate = now.getTime();


    product.setExpiredAt(expiredDate);

    //set product tax
    PRODUCT_TAX_TITLE federal = PRODUCT_TAX_TITLE.CAFEDERAL;
    PRODUCT_TAX_TITLE provincial = PRODUCT_TAX_TITLE.CAPROVINCE;
    PRODUCT_TAX_TYPE percentage = PRODUCT_TAX_TYPE.PERCENTAGE;
    
    if (product.getTaxes().isEmpty()) {
	    ProductTax tax1 = new ProductTax();
	    tax1.setTitle(federal);
	    tax1.setType(percentage);
	
	    ProductTax tax2 = new ProductTax();
	    tax2.setTitle(provincial);
	    tax2.setType(percentage);
	
	    product.getTaxes().add(tax1);
	    product.getTaxes().add(tax2);
    }

    // language switch
    String urlStr = url.toString();
    NumberFormat numberFormat;
    switch (language) {
      case EN:
        if (StringUtils.containsIgnoreCase(urlStr, "/fr-ca/")) {
          urlStr = StringUtils.replaceOnce(urlStr, "/fr-ca/", "/en-ca/");
        }
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
      case FR:
        if (StringUtils.containsIgnoreCase(urlStr, "/en-ca/")) {
          urlStr = StringUtils.replaceOnce(urlStr, "/en-ca/", "/fr-ca/");
        }
        numberFormat = NumberFormat.getInstance(Locale.FRANCE);
        break;
      default:
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
    }

    Document doc;
    try {
      doc = Jsoup.connect(urlStr).get();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    if (product.getPrices().isEmpty()) {
      String productPrice = doc.select(this.HTML_PATCH_BESTBUY.get("price")).first().text();
      Number number;
      try {
        number = numberFormat.parse(StringUtils.remove(productPrice, "$"));
        double p = number.doubleValue();
        ProductPrice price = new ProductPrice();
        price.setValue(p);
        product.getPrices().add(price);
        product.setCurrentPrice(p);
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(doc.select(this.HTML_PATCH_BESTBUY.get("name")).first().text());
    text.setDescription(doc.select(this.HTML_PATCH_BESTBUY.get("description")).first().ownText());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      ProductImage image = new ProductImage();
      image.setUrl(String.format("%s://%s%s", url.getProtocol(), url.getHost(), doc.select(this.HTML_PATCH_BESTBUY.get("image")).first().attr("src")));
      product.getImages().add(image);
    }

    return product;
  }
}
