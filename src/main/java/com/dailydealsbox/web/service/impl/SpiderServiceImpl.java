/**
 *
 */
package com.dailydealsbox.web.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dailydealsbox.web.configuration.BaseEnum.CURRENCY;
import com.dailydealsbox.web.configuration.BaseEnum.LANGUAGE;
import com.dailydealsbox.web.configuration.BaseEnum.PRODUCT_FEE_TITLE;
import com.dailydealsbox.web.configuration.BaseEnum.PRODUCT_FEE_TYPE;
import com.dailydealsbox.web.configuration.BaseEnum.PRODUCT_TAX_TITLE;
import com.dailydealsbox.web.configuration.BaseEnum.PRODUCT_TAX_TYPE;
import com.dailydealsbox.web.database.model.Product;
import com.dailydealsbox.web.database.model.ProductFee;
import com.dailydealsbox.web.database.model.ProductImage;
import com.dailydealsbox.web.database.model.ProductLink;
import com.dailydealsbox.web.database.model.ProductPrice;
import com.dailydealsbox.web.database.model.ProductTax;
import com.dailydealsbox.web.database.model.ProductText;
import com.dailydealsbox.web.database.model.Store;
import com.dailydealsbox.web.parser.AdidasCa;
import com.dailydealsbox.web.parser.AmazonCa;
import com.dailydealsbox.web.parser.BananaRepublicCa;
import com.dailydealsbox.web.parser.BrownsShoesCom;
import com.dailydealsbox.web.parser.CanadianTireCa;
import com.dailydealsbox.web.parser.EbayCom;
import com.dailydealsbox.web.parser.GapCanadaCa;
import com.dailydealsbox.web.parser.HomeDepotCa;
import com.dailydealsbox.web.parser.IkeaCa;
import com.dailydealsbox.web.parser.NordStromCom;
import com.dailydealsbox.web.parser.SephoraCom;
import com.dailydealsbox.web.parser.SportsExpertsCa;
import com.dailydealsbox.web.parser.StaplesCa;
import com.dailydealsbox.web.parser.TheBayCa;
import com.dailydealsbox.web.parser.ZaraCom;
import com.dailydealsbox.web.service.SpiderService;

/**
 * @author x_ye
 */
@Service
@Transactional
public class SpiderServiceImpl implements SpiderService {
  private Map<String, String> HTML_PATH_BESTBUY   = new HashMap<>();
  private Map<String, String> HTML_PATH_WALMARTCA = new HashMap<>();
  private Map<String, String> HTML_PATH_EBAYCOM   = new HashMap<>();
  private Map<String, String> HTML_PATH_EBAYCA    = new HashMap<>();

  private Map<String, String> HTML_PATH_COSTCOCA  = new HashMap<>();
  private Map<String, String> HTML_PATH_NEWEGGCA  = new HashMap<>();

  /*
   * Constructor
   */
  public SpiderServiceImpl() {
    String keyNameHtmlPath;
    String keyDescriptionHtmlPath;
    String keyImageHtmlPath;
    String keyPriceHtmlPath;
    String keyPriceHtmlPath2;
    String keyShippingHtmlPath;
    String keyImportHtmlPath;

    // HTML_PATH_BESTBUY
    this.HTML_PATH_BESTBUY.put("name", "SPAN#ctl00_CP_ctl00_PD_lblProductTitle");
    this.HTML_PATH_BESTBUY.put("description", "DIV.tab-overview-item");
    this.HTML_PATH_BESTBUY.put("image", "IMG#ctl00_CP_ctl00_PD_PI_IP");
    this.HTML_PATH_BESTBUY.put("price", "SPAN.amount");

    //HTML_PATH_WALMARTCA
    this.HTML_PATH_WALMARTCA.put("name", "DIV#product-desc");
    this.HTML_PATH_WALMARTCA.put("description", "P.description");
    this.HTML_PATH_WALMARTCA.put("image", "DIV.centered-img-wrap");
    this.HTML_PATH_WALMARTCA.put("price", "div.pricing-shipping");

    //HTML_PATH_EBAYCA
    keyNameHtmlPath = "H1#itemTitle";
    keyDescriptionHtmlPath = "DIV.itemAttr";
    keyImageHtmlPath = "IMG#icImg";
    keyPriceHtmlPath = "SPAN#mm-saleDscPrc";
    keyPriceHtmlPath2 = "SPAN#prcIsum";
    keyShippingHtmlPath = "SPAN#fshippingCost";
    keyImportHtmlPath = "SPAN#impchCost";

    this.HTML_PATH_EBAYCA.put("name", keyNameHtmlPath);
    this.HTML_PATH_EBAYCA.put("description", keyDescriptionHtmlPath);
    this.HTML_PATH_EBAYCA.put("image", keyImageHtmlPath);
    this.HTML_PATH_EBAYCA.put("price", keyPriceHtmlPath);
    this.HTML_PATH_EBAYCA.put("price2", keyPriceHtmlPath2);
    this.HTML_PATH_EBAYCA.put("shipping", keyShippingHtmlPath);
    this.HTML_PATH_EBAYCA.put("import", keyImportHtmlPath);

    //HTML_PATH_AMAZONCA
    keyNameHtmlPath = "span#productTitle";
    keyDescriptionHtmlPath = "DIV#feature-bullets";
    keyImageHtmlPath = "div#imgTagWrapperId";
    keyPriceHtmlPath = "SPAN#priceblock_ourprice";
    keyPriceHtmlPath2 = "SPAN#priceblock_dealprice";

    //HTML_PATH_COSTCOCA
    keyNameHtmlPath = "h1";
    keyDescriptionHtmlPath = "DIV#features-description";
    keyImageHtmlPath = "UL#large_images";
    keyPriceHtmlPath = "div.your-price";

    this.HTML_PATH_COSTCOCA.put("name", keyNameHtmlPath);
    this.HTML_PATH_COSTCOCA.put("description", keyDescriptionHtmlPath);
    this.HTML_PATH_COSTCOCA.put("image", keyImageHtmlPath);
    this.HTML_PATH_COSTCOCA.put("price", keyPriceHtmlPath);

    //HTML_PATH_NEWEGGCA
    keyNameHtmlPath = "h1";
    keyDescriptionHtmlPath = "DIV.grpBullet";
    keyImageHtmlPath = "div.grpAside";
    keyPriceHtmlPath = "li.price-current ";

    this.HTML_PATH_NEWEGGCA.put("name", keyNameHtmlPath);
    this.HTML_PATH_NEWEGGCA.put("description", keyDescriptionHtmlPath);
    this.HTML_PATH_NEWEGGCA.put("image", keyImageHtmlPath);
    this.HTML_PATH_NEWEGGCA.put("price", keyPriceHtmlPath);
  }

  /*
   * (non-Javadoc)
   * @see com.dailydealsbox.database.service.SpiderService#getProductFromBestbuy(java.lang.String)
   */
  @Override
  public Product getProduct(String url) throws Exception {
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
    if (product.getFees() == null) {
      product.setFees(new HashSet<ProductFee>());
    }

    if (product.getLinks() == null) {
      product.setLinks(new HashSet<ProductLink>());
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
        this.getProductFromEbayCom(url, product, LANGUAGE.EN);
        this.getProductFromEbayCom(url, product, LANGUAGE.FR);
        break;

      case "www.bestbuy.ca":
        this.getProductFromBestbuyCA(oUrl, product, LANGUAGE.EN);
        this.getProductFromBestbuyCA(oUrl, product, LANGUAGE.FR);
        break;
      case "www.walmart.ca":
        this.getProductFromWalmartCA(oUrl, product, LANGUAGE.EN);
        this.getProductFromWalmartCA(oUrl, product, LANGUAGE.FR);
        break;
      case "www.costco.ca":
        this.getProductFromCostcoCA(oUrl, product, LANGUAGE.EN);
        this.getProductFromCostcoCA(oUrl, product, LANGUAGE.FR);
        break;
      case "www.bestbuy.com":
        this.getProductFromBestbuyCOM(oUrl, product, LANGUAGE.EN);
        this.getProductFromBestbuyCOM(oUrl, product, LANGUAGE.FR);
        break;
      case "www.amazon.ca":
        this.getProductFromAmazonCA(url, product, LANGUAGE.EN);
        this.getProductFromAmazonCA(url, product, LANGUAGE.FR);
        break;
      case "www.amazon.com":
        this.getProductFromAmazonCOM(oUrl, product, LANGUAGE.EN);
        break;
      case "www.newegg.ca":
        this.getProductFromNeweggCA(oUrl, product, LANGUAGE.EN);
        break;
      case "www.homedepot.ca":
        this.getProductFromHomedepotCA(oUrl, product, LANGUAGE.EN);
        this.getProductFromHomedepotCA(oUrl, product, LANGUAGE.FR);
        break;
      case "www.thebay.com":
        this.getProductFromThebayCA(oUrl, product, LANGUAGE.EN);
        this.getProductFromThebayCA(oUrl, product, LANGUAGE.FR);
        break;
      case "www.brownsshoes.com":
        this.getProductFromBrownsshoesCOM(url, product, LANGUAGE.EN);
        this.getProductFromBrownsshoesCOM(url, product, LANGUAGE.FR);
        break;
      case "www.sephora.com":
        this.getProductFromSephoraCOM(url, product, LANGUAGE.EN);
        this.getProductFromSephoraCOM(url, product, LANGUAGE.FR);
        break;
      case "bananarepublic.gapcanada.ca":
        this.getProductFromBananaRepublicCa(url, product, LANGUAGE.EN);
        this.getProductFromBananaRepublicCa(url, product, LANGUAGE.FR);
        break;
      case "www.canadiantire.ca":
        this.getProductFromCanadianTireCa(url, product, LANGUAGE.EN);
        this.getProductFromCanadianTireCa(url, product, LANGUAGE.FR);
        break;
      case "www.adidas.ca":
        this.getProductFromAdidasCa(url, product, LANGUAGE.EN);
        this.getProductFromAdidasCa(url, product, LANGUAGE.FR);
        break;
      case "www.sportsexperts.ca":
        this.getProductFromSportsExpertsCa(url, product, LANGUAGE.EN);
        this.getProductFromSportsExpertsCa(url, product, LANGUAGE.FR);
        break;

      case "www.ikea.com":
        this.getProductFromIkeaCa(url, product, LANGUAGE.EN);
        this.getProductFromIkeaCa(url, product, LANGUAGE.FR);
        break;
      case "www.zara.com":
        this.getProductFromZaraCom(url, product, LANGUAGE.EN);
        this.getProductFromZaraCom(url, product, LANGUAGE.FR);
        break;
      case "www.staples.ca":
        this.getProductFromStaplesCa(url, product, LANGUAGE.EN);
        this.getProductFromStaplesCa(url, product, LANGUAGE.FR);
        break;
      case "shop.nordstrom.com/":
          this.getProductFromNordStromCom(url, product, LANGUAGE.EN);
          this.getProductFromNordStromCom(url, product, LANGUAGE.FR);
          break;        
        
      case "www.gapcanada.ca":
        this.getProductFromGapCanadaCa(url, product, LANGUAGE.EN);
        this.getProductFromGapCanadaCa(url, product, LANGUAGE.FR);
        break;
      default:
        break;
    }

    return product;
  }

  /**
   * getProductFromEbayCA
   *
   * @param url
   * @param product
   * @param language
   */
  private Product getProductFromEbayCA(URL url, Product product, LANGUAGE language)
      throws Exception {
    //set product url
    product.setUrl(url.toString());
    //set product status
    product.setDisabled(false);
    //set product key
    product.setKey(this.getProductKeyFromEbayCA(url));

    //set product store
    Store store = new Store();
    int storeID = 2;
    store.setId(storeID);
    product.setStore(store);

    //set product expired date
    Calendar now = Calendar.getInstance();
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.THURSDAY) {
      // calculate how much to add
      // the 5 is the difference between Saturday and Thursday
      int days = (Calendar.SATURDAY - weekday + 5);
      now.add(Calendar.DAY_OF_YEAR, days);
    }

    // language switch
    String urlStr = url.toString();

    Document doc;
    try {
      doc = Jsoup.connect(urlStr).get();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    //set product fees
    PRODUCT_FEE_TITLE feeShipping = PRODUCT_FEE_TITLE.SHIPPING;
    PRODUCT_FEE_TITLE feeImport = PRODUCT_FEE_TITLE.IMPORT;
    PRODUCT_FEE_TYPE feeType = PRODUCT_FEE_TYPE.AMOUNT;
    if (product.getFees().isEmpty()) {
      ProductFee fee1 = new ProductFee();
      fee1.setTitle(feeShipping);
      fee1.setType(feeType);
      fee1.setValue(0);

      ProductFee fee2 = new ProductFee();
      fee2.setTitle(feeImport);
      fee2.setType(feeType);
      fee2.setValue(0);

      NumberFormat numberFormat;
      numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

      String fee1String = doc.select(this.HTML_PATH_EBAYCOM.get("shipping")).first().text();
      String fee2String = doc.select(this.HTML_PATH_EBAYCOM.get("import")).first().text();
      double fee1Double = 0.00;
      double fee2Double = 0.00;
      try {
        if (fee1String.equals("FREE")) {
          fee1Double = 0.00;
        } else {
          Number fee1Number = numberFormat.parse(StringUtils.remove(fee1String, "$"));
          fee1Double = fee1Number.doubleValue();
        }
        if (fee2String.equals("")) {
          fee2Double = 0.00;
        } else {
          Number fee2Number = numberFormat.parse(StringUtils.remove(fee2String, "$"));
          fee2Double = fee2Number.doubleValue();
        }

        fee1.setValue(fee1Double);
        fee2.setValue(fee2Double);
      } catch (Exception e) {
        e.printStackTrace();
      }

      product.getFees().add(fee1);
      product.getFees().add(fee2);
    }

    if (product.getPrices().isEmpty()) {
      String productPrice = "";
      Element productPriceElement1 = doc.select(this.HTML_PATH_EBAYCOM.get("price")).first();
      Element productPriceElement2 = doc.select(this.HTML_PATH_EBAYCOM.get("price2")).first();
      if (productPriceElement1 != null) {
        productPrice = productPriceElement1.text();
      }
      if (productPriceElement2 != null) {
        productPrice = productPriceElement2.text();
      }

      Number number;
      NumberFormat numberFormat;
      numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
      try {
        number = numberFormat.parse(StringUtils.remove(productPrice, "C $"));
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
    text.setName(doc.select(this.HTML_PATH_EBAYCOM.get("name")).first().text());
    text.setDescription(doc.select(this.HTML_PATH_EBAYCOM.get("description")).text());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      ProductImage image = new ProductImage();
      image.setUrl(doc.select(this.HTML_PATH_EBAYCOM.get("image")).first().attr("src"));
      product.getImages().add(image);
    }

    return product;
  }

  /**
   * getProductFromAmazonUS
   *
   * @param url
   * @param product
   * @param language
   */
  private void getProductFromAmazonCOM(URL url, Product product, LANGUAGE language)
      throws Exception {

  }

  private Product getProductFromAmazonCA(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    AmazonCa amazonCaPage = new AmazonCa();
    amazonCaPage.setActive(true);
    amazonCaPage.setStoreId();
    amazonCaPage.setUrl(url);
    amazonCaPage.setExpiration();

    amazonCaPage.setDoc();
    amazonCaPage.setKey();
    amazonCaPage.setName();
    amazonCaPage.setDescription();
    amazonCaPage.setImage();
    amazonCaPage.setPrice();

    amazonCaPage.setRating();
    amazonCaPage.setReviewsCount();

    //set product url
    product.setUrl(amazonCaPage.getUrl());

    //set product key
    product.setKey(amazonCaPage.getKey());

    //set product status
    product.setDisabled(!amazonCaPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(amazonCaPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(amazonCaPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(amazonCaPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(amazonCaPage.getName());
    text.setDescription(amazonCaPage.getDescription());
    product.getTexts().add(text);

    if (product.getLinks().isEmpty()) {
      ProductLink link = new ProductLink();
      link.setUrl(url);
      link.setName("amazon.ca");
      link.setRating(amazonCaPage.getRating());
      link.setReviewNumber(amazonCaPage.getReviewsCount());
      product.getLinks().add(link);
    }

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(amazonCaPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  /**
   * getProductFromNeweggCA
   *
   * @param url
   * @param product
   * @param language
   */

  //http://www.newegg.ca/Product/Product.aspx?Item=N82E16824106002&icid=328803
  private Product getProductFromNeweggCA(URL url, Product product, LANGUAGE language)
      throws Exception {
    //set product url
    product.setUrl(url.toString());
    //set product status
    product.setDisabled(false);
    //set product key
    product.setKey(this.getProductKeyFromNeweggCA(url));

    //set product store
    Store store = new Store();
    int storeID = 21;
    store.setId(storeID);
    product.setStore(store);

    //set product expired date
    Calendar now = Calendar.getInstance();
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.THURSDAY) {
      // calculate how much to add
      // the 5 is the difference between Saturday and Thursday
      int days = (Calendar.SATURDAY - weekday + 5);
      now.add(Calendar.DAY_OF_YEAR, days);
    }

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
        if (StringUtils.containsIgnoreCase(urlStr, "Product")) {
          urlStr = StringUtils.replaceOnce(urlStr, "Product", "Product");
        }
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
      case FR:
        if (StringUtils.containsIgnoreCase(urlStr, "Product")) {
          urlStr = StringUtils.replaceOnce(urlStr, "Product", "Product");
        }
        numberFormat = NumberFormat.getInstance(Locale.FRANCE);
        break;
      default:
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
    }

    Document doc;
    try {
      doc = Jsoup.connect(urlStr).timeout(20000).get();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    //set product fees
    PRODUCT_FEE_TITLE feeShipping = PRODUCT_FEE_TITLE.SHIPPING;
    PRODUCT_FEE_TYPE feeType = PRODUCT_FEE_TYPE.AMOUNT;
    if (product.getFees().isEmpty()) {
      ProductFee fee1 = new ProductFee();
      fee1.setTitle(feeShipping);
      fee1.setType(feeType);
      fee1.setValue(0);

      product.getFees().add(fee1);
    }

    //set product tax
    if (product.getPrices().isEmpty()) {
      String productPrice = "";
      Element productPriceElement1 = doc.select(this.HTML_PATH_NEWEGGCA.get("price")).first();
      if (productPriceElement1 != null) {

        productPrice = doc.select(this.HTML_PATH_NEWEGGCA.get("price")).first().select("strong")
            .text()
            + doc.select(this.HTML_PATH_NEWEGGCA.get("price")).first().select("sup").text();
      } else {
        productPrice = "0.00";
      }

      Number number;
      try {
        number = numberFormat.parse(productPrice);
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
    text.setName(doc.select(this.HTML_PATH_NEWEGGCA.get("name")).first().text());
    text.setDescription(doc.select(this.HTML_PATH_NEWEGGCA.get("description")).first().text());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      ProductImage image = new ProductImage();
      image.setUrl(doc.select(this.HTML_PATH_NEWEGGCA.get("image")).first().select("img").get(2)
          .attr("src").replace("$S35$", "$S120$"));
      product.getImages().add(image);
    }

    return product;
  }

  /**
   * getProductFromBestbuyUS
   *
   * @param url
   * @param product
   * @param language
   */
  private void getProductFromBestbuyCOM(URL url, Product product, LANGUAGE language)
      throws Exception {

  }

  /**
   * getProductKeyFromBestbuyCA
   *
   * @param url
   * @return
   */
  private String getProductKeyFromBestbuyCA(URL url) throws Exception {
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
   * getProductKeyFromWalmartCA
   *
   * @param {@link java.net.URL} url
   * @return
   * @throws Exception
   */
  private String getProductKeyFromWalmartCA(URL url) throws Exception {
    // String to be scanned to find the pattern.
    String pattern = "\\/(\\d+)";

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
   * getProductKeyFromCostcoCA
   *
   * @param url
   * @return
   * @throws Exception
   */
  private String getProductKeyFromCostcoCA(URL url) throws Exception {
    // String to be scanned to find the pattern.
    String pattern = "\\.(\\d+)\\.html";

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
   * getProductKeyFromEbayCA
   *
   * @param url
   * @return
   * @throws Exception
   */
  private String getProductKeyFromEbayCA(URL url) throws Exception {
    // String to be scanned to find the pattern.
    String pattern = "\\/(\\d+)";

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

  private String getProductKeyFromNeweggCA(URL url) throws Exception {
    // String to be scanned to find the pattern.
    String pattern = "\\/product\\/(\\w+)\\/ref";

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
  private Product getProductFromBestbuyCA(URL url, Product product, LANGUAGE language)
      throws Exception {
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
    if (weekday != Calendar.THURSDAY) {
      // calculate how much to add
      // the 5 is the difference between Saturday and Thursday
      int days = (Calendar.SATURDAY - weekday + 5);
      now.add(Calendar.DAY_OF_YEAR, days);
    }

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
        if (StringUtils.containsIgnoreCase(urlStr, "/fr-CA/")) {
          urlStr = StringUtils.replaceOnce(urlStr, "/fr-CA/", "/en-CA/");
        }
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
      case FR:
        if (StringUtils.containsIgnoreCase(urlStr, "/en-CA/")) {
          urlStr = StringUtils.replaceOnce(urlStr, "/en-CA/", "/fr-CA/");
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

    //set product fees
    PRODUCT_FEE_TITLE feeShipping = PRODUCT_FEE_TITLE.SHIPPING;
    PRODUCT_FEE_TYPE feeType = PRODUCT_FEE_TYPE.AMOUNT;
    if (product.getFees().isEmpty()) {
      ProductFee fee1 = new ProductFee();
      fee1.setTitle(feeShipping);
      fee1.setType(feeType);
      fee1.setValue(0);

      product.getFees().add(fee1);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      String productPrice = doc.select(this.HTML_PATH_BESTBUY.get("price")).first().text();
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
    text.setName(doc.select(this.HTML_PATH_BESTBUY.get("name")).first().text());
    text.setDescription(doc.select(this.HTML_PATH_BESTBUY.get("description")).first().ownText());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      ProductImage image = new ProductImage();
      image.setUrl(String.format("%s://%s%s", url.getProtocol(), url.getHost(),
          doc.select(this.HTML_PATH_BESTBUY.get("image")).first().attr("src")));
      product.getImages().add(image);
    }

    return product;
  }

  /**
   * getProductFromHomedepotCA
   *
   * @param url
   * @param product
   * @param language
   */
  private Product getProductFromHomedepotCA(URL url, Product product, LANGUAGE language)
      throws Exception {
    // language switch
    String urlStr = url.toString();

    //product page info
    HomeDepotCa homeDepotPage = new HomeDepotCa();
    homeDepotPage.setActive(true);
    homeDepotPage.setStoreId();
    homeDepotPage.setUrl(urlStr);
    homeDepotPage.setExpiration();

    homeDepotPage.setDoc();
    homeDepotPage.setKey();
    homeDepotPage.setName();
    homeDepotPage.setDescription();
    homeDepotPage.setImage();
    homeDepotPage.setPrice();

    //set product url
    product.setUrl(homeDepotPage.getUrl());

    //set product key
    product.setKey(homeDepotPage.getKey());

    //set product status
    product.setDisabled(!homeDepotPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(homeDepotPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(homeDepotPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(homeDepotPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(homeDepotPage.getName());
    text.setDescription(homeDepotPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(homeDepotPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  /**
   * getProductFromTheBayCA
   *
   * @param url
   * @param product
   * @param language
   */
  private Product getProductFromThebayCA(URL url, Product product, LANGUAGE language)
      throws Exception {
    // language switch
    String urlStr = url.toString();

    //product page info
    TheBayCa theBayPage = new TheBayCa();
    theBayPage.setActive(true);
    theBayPage.setStoreId();
    theBayPage.setUrl(urlStr);
    theBayPage.setExpiration();

    theBayPage.setDoc();
    theBayPage.setKey();
    theBayPage.setName();
    theBayPage.setDescription();
    theBayPage.setImage();
    theBayPage.setPrice();

    //set product url
    product.setUrl(theBayPage.getUrl());

    //set product key
    product.setKey(theBayPage.getKey());

    //set product status
    product.setDisabled(!theBayPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(theBayPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(theBayPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(theBayPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(theBayPage.getName());
    text.setDescription(theBayPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(theBayPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromBrownsshoesCOM(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    BrownsShoesCom brownsShoesPage = new BrownsShoesCom();
    brownsShoesPage.setActive(true);
    brownsShoesPage.setStoreId();
    brownsShoesPage.setUrl(url);
    brownsShoesPage.setExpiration();

    brownsShoesPage.setDoc();
    brownsShoesPage.setKey();
    brownsShoesPage.setName();
    brownsShoesPage.setDescription();
    brownsShoesPage.setImage();
    brownsShoesPage.setPrice();

    //set product url
    product.setUrl(brownsShoesPage.getUrl());

    //set product key
    product.setKey(brownsShoesPage.getKey());

    //set product status
    product.setDisabled(!brownsShoesPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(brownsShoesPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(brownsShoesPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(brownsShoesPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(brownsShoesPage.getName());
    text.setDescription(brownsShoesPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(brownsShoesPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromSephoraCOM(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    SephoraCom sephoraPage = new SephoraCom();
    sephoraPage.setActive(true);
    sephoraPage.setStoreId();
    sephoraPage.setUrl(url);
    sephoraPage.setExpiration();

    sephoraPage.setDoc();
    sephoraPage.setKey();
    sephoraPage.setName();
    sephoraPage.setDescription();
    sephoraPage.setImage();
    sephoraPage.setPrice();

    //set product url
    product.setUrl(sephoraPage.getUrl());

    //set product key
    product.setKey(sephoraPage.getKey());

    //set product status
    product.setDisabled(!sephoraPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(sephoraPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(sephoraPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(sephoraPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(sephoraPage.getName());
    text.setDescription(sephoraPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(sephoraPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromBananaRepublicCa(String url, Product product, LANGUAGE language)
      throws Exception {

    //product page info
    BananaRepublicCa bananaRepublicPage = new BananaRepublicCa();
    bananaRepublicPage.setActive(true);
    bananaRepublicPage.setStoreId();
    bananaRepublicPage.setUrl(url);
    bananaRepublicPage.setExpiration();

    bananaRepublicPage.setDoc();
    bananaRepublicPage.setKey();
    bananaRepublicPage.setName();
    bananaRepublicPage.setDescription();
    bananaRepublicPage.setImage();
    bananaRepublicPage.setPrice();

    //set product url
    product.setUrl(bananaRepublicPage.getUrl());

    //set product key
    product.setKey(bananaRepublicPage.getKey());

    //set product status
    product.setDisabled(!bananaRepublicPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(bananaRepublicPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(bananaRepublicPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(bananaRepublicPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(bananaRepublicPage.getName());
    text.setDescription(bananaRepublicPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(bananaRepublicPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromCanadianTireCa(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    CanadianTireCa canadianTireCaPage = new CanadianTireCa();
    canadianTireCaPage.setActive(true);
    canadianTireCaPage.setStoreId();
    canadianTireCaPage.setUrl(url);
    canadianTireCaPage.setExpiration();

    canadianTireCaPage.setDoc();
    canadianTireCaPage.setKey();
    canadianTireCaPage.setName();
    canadianTireCaPage.setDescription();
    canadianTireCaPage.setImage();
    canadianTireCaPage.setPrice();

    //set product url
    product.setUrl(canadianTireCaPage.getUrl());

    //set product key
    product.setKey(canadianTireCaPage.getKey());

    //set product status
    product.setDisabled(!canadianTireCaPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(canadianTireCaPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(canadianTireCaPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(canadianTireCaPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(canadianTireCaPage.getName());
    text.setDescription(canadianTireCaPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(canadianTireCaPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromAdidasCa(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    AdidasCa adidasCaPage = new AdidasCa();
    adidasCaPage.setActive(true);
    adidasCaPage.setStoreId();
    adidasCaPage.setUrl(url);
    adidasCaPage.setExpiration();

    adidasCaPage.setDoc();
    adidasCaPage.setKey();
    adidasCaPage.setName();
    adidasCaPage.setDescription();
    adidasCaPage.setImage();
    adidasCaPage.setPrice();

    //set product url
    product.setUrl(adidasCaPage.getUrl());

    //set product key
    product.setKey(adidasCaPage.getKey());

    //set product status
    product.setDisabled(!adidasCaPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(adidasCaPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(adidasCaPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(adidasCaPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(adidasCaPage.getName());
    text.setDescription(adidasCaPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(adidasCaPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromSportsExpertsCa(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    SportsExpertsCa sportsExpertsCaPage = new SportsExpertsCa();
    sportsExpertsCaPage.setActive(true);
    sportsExpertsCaPage.setStoreId();
    sportsExpertsCaPage.setUrl(url);
    sportsExpertsCaPage.setExpiration();

    sportsExpertsCaPage.setDoc();
    sportsExpertsCaPage.setKey();
    sportsExpertsCaPage.setName();
    sportsExpertsCaPage.setDescription();
    sportsExpertsCaPage.setImage();
    sportsExpertsCaPage.setPrice();

    //set product url
    product.setUrl(sportsExpertsCaPage.getUrl());

    //set product key
    product.setKey(sportsExpertsCaPage.getKey());

    //set product status
    product.setDisabled(!sportsExpertsCaPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(sportsExpertsCaPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(sportsExpertsCaPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(sportsExpertsCaPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(sportsExpertsCaPage.getName());
    text.setDescription(sportsExpertsCaPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(sportsExpertsCaPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromGapCanadaCa(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    GapCanadaCa gapCanadaCaPage = new GapCanadaCa();
    gapCanadaCaPage.setActive(true);
    gapCanadaCaPage.setStoreId();
    gapCanadaCaPage.setUrl(url);
    gapCanadaCaPage.setExpiration();

    gapCanadaCaPage.setDoc();
    gapCanadaCaPage.setKey();
    gapCanadaCaPage.setName();
    gapCanadaCaPage.setDescription();
    gapCanadaCaPage.setImage();
    gapCanadaCaPage.setPrice();

    //set product url
    product.setUrl(gapCanadaCaPage.getUrl());

    //set product key
    product.setKey(gapCanadaCaPage.getKey());

    //set product status
    product.setDisabled(!gapCanadaCaPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(gapCanadaCaPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(gapCanadaCaPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(gapCanadaCaPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(gapCanadaCaPage.getName());
    text.setDescription(gapCanadaCaPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(gapCanadaCaPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromIkeaCa(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    IkeaCa ikeaCaPage = new IkeaCa();
    ikeaCaPage.setActive(true);
    ikeaCaPage.setStoreId();
    ikeaCaPage.setUrl(url);
    ikeaCaPage.setExpiration();

    ikeaCaPage.setDoc();
    ikeaCaPage.setKey();
    ikeaCaPage.setName();
    ikeaCaPage.setDescription();
    ikeaCaPage.setImage();
    ikeaCaPage.setPrice();

    //set product url
    product.setUrl(ikeaCaPage.getUrl());

    //set product key
    product.setKey(ikeaCaPage.getKey());

    //set product status
    product.setDisabled(!ikeaCaPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(ikeaCaPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(ikeaCaPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(ikeaCaPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(ikeaCaPage.getName());
    text.setDescription(ikeaCaPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(ikeaCaPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromStaplesCa(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    StaplesCa staplesCaPage = new StaplesCa();
    staplesCaPage.setActive(true);
    staplesCaPage.setStoreId();
    staplesCaPage.setUrl(url);
    staplesCaPage.setExpiration();

    staplesCaPage.setDoc();
    staplesCaPage.setKey();
    staplesCaPage.setName();
    staplesCaPage.setDescription();
    staplesCaPage.setImage();
    staplesCaPage.setPrice();

    //set product url
    product.setUrl(staplesCaPage.getUrl());

    //set product key
    product.setKey(staplesCaPage.getKey());

    //set product status
    product.setDisabled(!staplesCaPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(staplesCaPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(staplesCaPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(staplesCaPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(staplesCaPage.getName());
    text.setDescription(staplesCaPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(staplesCaPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromZaraCom(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    ZaraCom zaraComPage = new ZaraCom();
    zaraComPage.setActive(true);
    zaraComPage.setStoreId();
    zaraComPage.setUrl(url);
    zaraComPage.setExpiration();

    zaraComPage.setDoc();
    zaraComPage.setKey();
    zaraComPage.setName();
    zaraComPage.setDescription();
    zaraComPage.setImage();
    zaraComPage.setPrice();

    //set product url
    product.setUrl(zaraComPage.getUrl());

    //set product key
    product.setKey(zaraComPage.getKey());

    //set product status
    product.setDisabled(!zaraComPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(zaraComPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(zaraComPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(zaraComPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(zaraComPage.getName());
    text.setDescription(zaraComPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(zaraComPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromWalmartCA(URL url, Product product, LANGUAGE language)
      throws Exception {
    //set product url
    product.setUrl(url.toString());
    //set product status
    product.setDisabled(false);
    //set product key
    product.setKey(this.getProductKeyFromWalmartCA(url));

    //set product store
    Store store = new Store();
    int storeID = 1;
    store.setId(storeID);
    product.setStore(store);

    //set product expired date
    Calendar now = Calendar.getInstance();
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.THURSDAY) {
      // calculate how much to add
      // the 5 is the difference between Saturday and Thursday
      int days = (Calendar.SATURDAY - weekday + 5);
      now.add(Calendar.DAY_OF_YEAR, days);
    }

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
        if (StringUtils.containsIgnoreCase(urlStr, "/fr/")) {
          urlStr = StringUtils.replaceOnce(urlStr, "/fr/", "/en/");
        }
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
      case FR:
        if (StringUtils.containsIgnoreCase(urlStr, "/en/")) {
          urlStr = StringUtils.replaceOnce(urlStr, "/en/", "/fr/");
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
    //set product fees
    PRODUCT_FEE_TITLE feeShipping = PRODUCT_FEE_TITLE.SHIPPING;
    PRODUCT_FEE_TYPE feeType = PRODUCT_FEE_TYPE.AMOUNT;
    if (product.getFees().isEmpty()) {
      ProductFee fee1 = new ProductFee();
      fee1.setTitle(feeShipping);
      fee1.setType(feeType);
      fee1.setValue(0);

      product.getFees().add(fee1);
    }

    //set product tax
    if (product.getPrices().isEmpty()) {
      String productPrice = doc.select(this.HTML_PATH_WALMARTCA.get("price")).first()
          .select("div.microdata-price").first().select("span").first().text();
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
    text.setName(doc.select(this.HTML_PATH_WALMARTCA.get("name")).first().select("h1").first()
        .text());
    text.setDescription(doc.select(this.HTML_PATH_WALMARTCA.get("description")).first().text());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      ProductImage image = new ProductImage();
      image.setUrl(String.format("%s://%s", url.getProtocol(),
          doc.select(this.HTML_PATH_WALMARTCA.get("image")).first().select("img.image").first()
              .attr("src")));
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromCostcoCA(URL url, Product product, LANGUAGE language)
      throws Exception {
    //set product url
    product.setUrl(url.toString());
    //set product status
    product.setDisabled(false);
    //set product key
    product.setKey(this.getProductKeyFromCostcoCA(url));

    //set product store
    Store store = new Store();
    int storeID = 20;
    store.setId(storeID);
    product.setStore(store);

    //set product expired date
    Calendar now = Calendar.getInstance();
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.THURSDAY) {
      // calculate how much to add
      // the 5 is the difference between Saturday and Thursday
      int days = (Calendar.SATURDAY - weekday + 5);
      now.add(Calendar.DAY_OF_YEAR, days);
    }

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
        if (StringUtils.containsIgnoreCase(urlStr, "langId=-25")) {
          urlStr = StringUtils.replaceOnce(urlStr, "langId=-25", "langId=-24");
        }
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
      case FR:
        if (StringUtils.containsIgnoreCase(urlStr, "langId=-24")) {
          urlStr = StringUtils.replaceOnce(urlStr, "langId=-24", "langId=-25");
        }
        numberFormat = NumberFormat.getInstance(Locale.FRANCE);
        break;
      default:
        numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        break;
    }

    Document doc;
    try {
      doc = Jsoup.connect(urlStr).timeout(10000).get();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    //set product fees
    PRODUCT_FEE_TITLE feeShipping = PRODUCT_FEE_TITLE.SHIPPING;
    PRODUCT_FEE_TYPE feeType = PRODUCT_FEE_TYPE.AMOUNT;
    if (product.getFees().isEmpty()) {
      ProductFee fee1 = new ProductFee();
      fee1.setTitle(feeShipping);
      fee1.setType(feeType);
      fee1.setValue(0);

      product.getFees().add(fee1);
    }

    //set product tax
    if (product.getPrices().isEmpty()) {
      String productPrice = doc.select(this.HTML_PATH_COSTCOCA.get("price")).first()
          .select("span.currency").first().text();
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
    text.setName(doc.select(this.HTML_PATH_COSTCOCA.get("name")).first().text());
    text.setDescription(doc.select(this.HTML_PATH_COSTCOCA.get("description")).first().text());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      ProductImage image = new ProductImage();
      image.setUrl(doc.select(this.HTML_PATH_COSTCOCA.get("image")).first().select("img").first()
          .attr("src"));
      product.getImages().add(image);
    }

    return product;
  }

  private Product getProductFromEbayCom(String url, Product product, LANGUAGE language)
      throws Exception {
    //product page info
    EbayCom ebayComPage = new EbayCom();
    ebayComPage.setActive(true);
    ebayComPage.setStoreId();
    ebayComPage.setUrl(url);
    ebayComPage.setExpiration();

    ebayComPage.setDoc();
    ebayComPage.setKey();
    ebayComPage.setName();
    ebayComPage.setDescription();
    ebayComPage.setImage();
    ebayComPage.setPrice();

    //set product url
    product.setUrl(ebayComPage.getUrl());

    //set product key
    product.setKey(ebayComPage.getKey());

    //set product status
    product.setDisabled(!ebayComPage.getActive());

    //set product store
    Store store = new Store();
    store.setId(ebayComPage.getStoreId());
    product.setStore(store);

    //set product tax
    if (product.getTaxes().isEmpty()) {
      ProductTax federal = new ProductTax();
      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      ProductTax provincial = new ProductTax();
      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

      product.getTaxes().add(federal);
      product.getTaxes().add(provincial);
    }

    //set product fees

    if (product.getFees().isEmpty()) {
      ProductFee shipping = new ProductFee();
      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
      shipping.setValue(0.00);

      product.getFees().add(shipping);
    }

    //set product price
    if (product.getPrices().isEmpty()) {
      try {
        //set price
        ProductPrice price = new ProductPrice();
        price.setValue(ebayComPage.getPrice());
        //add price to product
        product.getPrices().add(price);
        product.setCurrentPrice(ebayComPage.getPrice());
        product.setCurrency(CURRENCY.CAD);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ProductText text = new ProductText();
    text.setLanguage(language);
    text.setName(ebayComPage.getName());
    text.setDescription(ebayComPage.getDescription());
    product.getTexts().add(text);

    if (product.getImages().isEmpty()) {
      //set image
      ProductImage image = new ProductImage();
      image.setUrl(ebayComPage.getImage());
      //add image to product
      product.getImages().add(image);
    }

    return product;
  }
  
  private Product getProductFromNordStromCom(String url, Product product, LANGUAGE language)
	      throws Exception {
	    //product page info
	    NordStromCom nordStromComPage = new NordStromCom();
	    nordStromComPage.setActive(true);
	    nordStromComPage.setStoreId();
	    nordStromComPage.setUrl(url);
	    nordStromComPage.setExpiration();

	    nordStromComPage.setDoc();
	    nordStromComPage.setKey();
	    nordStromComPage.setName();
	    nordStromComPage.setDescription();
	    nordStromComPage.setImage();
	    nordStromComPage.setPrice();

	    //set product url
	    product.setUrl(nordStromComPage.getUrl());

	    //set product key
	    product.setKey(nordStromComPage.getKey());

	    //set product status
	    product.setDisabled(!nordStromComPage.getActive());

	    //set product store
	    Store store = new Store();
	    store.setId(nordStromComPage.getStoreId());
	    product.setStore(store);

	    //set product tax
	    if (product.getTaxes().isEmpty()) {
	      ProductTax federal = new ProductTax();
	      federal.setTitle(PRODUCT_TAX_TITLE.CAFEDERAL);
	      federal.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

	      ProductTax provincial = new ProductTax();
	      provincial.setTitle(PRODUCT_TAX_TITLE.CAPROVINCE);
	      provincial.setType(PRODUCT_TAX_TYPE.PERCENTAGE);

	      product.getTaxes().add(federal);
	      product.getTaxes().add(provincial);
	    }

	    //set product fees

	    if (product.getFees().isEmpty()) {
	      ProductFee shipping = new ProductFee();
	      shipping.setTitle(PRODUCT_FEE_TITLE.SHIPPING);
	      shipping.setType(PRODUCT_FEE_TYPE.AMOUNT);
	      shipping.setValue(0.00);

	      product.getFees().add(shipping);
	    }

	    //set product price
	    if (product.getPrices().isEmpty()) {
	      try {
	        //set price
	        ProductPrice price = new ProductPrice();
	        price.setValue(nordStromComPage.getPrice());
	        //add price to product
	        product.getPrices().add(price);
	        product.setCurrentPrice(nordStromComPage.getPrice());
	        product.setCurrency(CURRENCY.CAD);
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }

	    ProductText text = new ProductText();
	    text.setLanguage(language);
	    text.setName(nordStromComPage.getName());
	    text.setDescription(nordStromComPage.getDescription());
	    product.getTexts().add(text);

	    if (product.getImages().isEmpty()) {
	      //set image
	      ProductImage image = new ProductImage();
	      image.setUrl(nordStromComPage.getImage());
	      //add image to product
	      product.getImages().add(image);
	    }

	    return product;
	  }  
}
