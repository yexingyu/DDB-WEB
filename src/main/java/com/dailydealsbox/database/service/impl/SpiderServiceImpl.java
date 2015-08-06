/**
 *
 */
package com.dailydealsbox.database.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.dailydealsbox.database.model.Product;
import com.dailydealsbox.database.model.ProductImage;
import com.dailydealsbox.database.model.ProductPrice;
import com.dailydealsbox.database.model.ProductText;
import com.dailydealsbox.database.model.base.BaseEnum.LANGUAGE;
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
  public Product getProductFromBestbuy(String url) {
    Product product = new Product();
    this.getProductFromBestbuy(url, product, LANGUAGE.EN);
    this.getProductFromBestbuy(url, product, LANGUAGE.FR);
    return product;
  }

  /**
   * getProductFromBestbuy
   *
   * @param url
   * @param product
   * @param language
   * @return
   */
  private Product getProductFromBestbuy(String url, Product product, LANGUAGE language) {
    // init some properties by emptySet
    if (product.getTexts() == null) {
      product.setTexts(new HashSet<ProductText>());
    }
    if (product.getPrices() == null) {
      product.setPrices(new HashSet<ProductPrice>());
    }
    if (product.getImages() == null) {
      product.setImages(new HashSet<ProductImage>());
    }

    // language switch
    NumberFormat numberFormat;
    switch (language) {
      case EN:
        if (StringUtils.containsIgnoreCase(url, "/fr-ca/")) {
          url = StringUtils.replaceOnce(url, "/fr-ca/", "/en-ca/");
        }
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
      case FR:
        if (StringUtils.containsIgnoreCase(url, "/en-ca/")) {
          url = StringUtils.replaceOnce(url, "/en-ca/", "/fr-ca/");
        }
        numberFormat = NumberFormat.getInstance(Locale.FRANCE);
        break;
      default:
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
    }

    URL aURL;
    try {
      aURL = new URL(url);
    } catch (MalformedURLException e1) {
      e1.printStackTrace();
      return null;
    }

    Document doc;
    try {
      doc = Jsoup.connect(url).get();
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
      image.setUrl(String.format("%s://%s%s", aURL.getProtocol(), aURL.getHost(), doc.select(this.HTML_PATCH_BESTBUY.get("image")).first().attr("src")));
      product.getImages().add(image);
    }

    return product;
  }

}
