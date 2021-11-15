package com.ericsson.commercehub.service;

import com.ericsson.commercehub.constants.CommerceHubConstants;
import com.ericsson.commercehub.dto.Product;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class ProductSearchService {

    private final Logger logger = LoggerFactory.getLogger(ProductSearchService.class);

    @Value("${amazon.api.host}")
    private String amazonHost;

    @Value("${aliexpress.api.host}")
    private String aliExpressHost;

    @Value("${rapidapikey}")
    private String rapidAPIKey;

    @Value("${amazon.rapidapi.host}")
    private String amazonRapidAPIHost;

    @Value("${aliexpress.rapidapi.host}")
    private String aliExpressAPIHost;

    @Value("${aliexpress.site.host}")
    private String aliExpressPDPURL;

    @Value("${aliexpress.site.name}")
    private String aliExpressSiteName;

    @Value("${amazon.site.name}")
    private String amazonSiteName;

    public List<Product> findProduct(String productName) {

        logger.debug("Inside findProduct () Starts");

        List<Product> productList = new ArrayList<>();

        productList = findProductInAmazon(productName, productList);

        productList = findProductInAliExpress(productName, productList);

        logger.debug("Inside findProduct () Ends");

        return productList;
    }

    private List<Product> findProductInAmazon(String productName, List<Product> productList) {

        logger.debug("Inside findProductInAmazon () Starts");

        String URL = amazonHost + productName;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .get()
                .addHeader(CommerceHubConstants.RAPID_API_HOST, amazonRapidAPIHost)
                .addHeader(CommerceHubConstants.RAPID_API_KEY, rapidAPIKey)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                productList = processAmazonResponseMessage(response.body().string(), productList);
            }

        } catch (IOException e) {
            logger.error("Got exception in Amazon api call " + e.getMessage());
        }

        logger.debug("Inside findProductInAmazon () Ends");

        return productList;
    }

    private List<Product> processAmazonResponseMessage(String responseBody, List<Product> productList) {
        logger.debug("Inside processAmazonResponseMessage () Starts");

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(responseBody);

            JSONArray resultArray = (JSONArray) json.get("docs");

            for (int i = 0; i < resultArray.size(); i++) {

                Product product = updateAmazonProductDto((JSONObject) resultArray.get(i));

                productList.add(product);
            }
        } catch (ParseException e) {
            logger.error("Got ParseException in Amazon response parsing " + e.getMessage());
        }

        logger.debug("Inside processAmazonResponseMessage () Ends");

        return productList;
    }

    private Product updateAmazonProductDto(JSONObject resultObject) {

        logger.debug("Inside updateAmazonProductDto () Starts");

        Product product = new Product();

        product.setSiteName(amazonSiteName);

        if (resultObject.get("app_sale_price") != null) {
            String salePrice = (String) resultObject.get("app_sale_price");
            salePrice = salePrice.replace(",","");
            product.setPrice(Double.valueOf(salePrice));
        }

        if (resultObject.get("app_sale_price_currency") != null) {
            product.setCurrencyCode((String) resultObject.get("app_sale_price_currency"));
        }

        if (resultObject.get("product_detail_url") != null) {
            product.setProductURL((String) resultObject.get("product_detail_url"));
        }

        if (resultObject.get("product_title") != null) {
            product.setName((String) resultObject.get("product_title"));
        }

        if (resultObject.get("product_main_image_url") != null) {
            product.setImageURL((String) resultObject.get("product_main_image_url"));
        }

        logger.debug("Inside updateAmazonProductDto () Ends");

        return product;
    }

    private List<Product> findProductInAliExpress(String productName, List<Product> productList) {

        logger.debug("Inside findProductInAliExpress () Starts");

        String url = aliExpressHost + productName + CommerceHubConstants.PAGE_PARAM;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(CommerceHubConstants.RAPID_API_HOST, aliExpressAPIHost)
                .addHeader(CommerceHubConstants.RAPID_API_KEY, rapidAPIKey)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                productList = processAliExpressResponseMessage(response.body().string(), productList);
            }

        } catch (IOException e) {
            logger.error("Got exception in AliExpress api call " + e.getMessage());
        }

        logger.debug("Inside findProductInAliExpress () Ends");

        return productList;
    }

    private List<Product> processAliExpressResponseMessage(String responseBody, List<Product> productList) {

        logger.debug("Inside processAliExpressResponseMessage () Starts");

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(responseBody);

            JSONObject data = (JSONObject) json.get("data");
            if (data != null) {
                JSONObject searchResult = (JSONObject) data.get("searchResult");
                JSONObject mods = (JSONObject) searchResult.get("mods");
                JSONObject itemList = (JSONObject) mods.get("itemList");
                JSONArray contentArray = (JSONArray) itemList.get("content");

                for (int i = 0; i < contentArray.size(); i++) {

                    Product product = updateAliExpressProductDto((JSONObject) contentArray.get(i));

                    productList.add(product);
                }
            } else {
                logger.debug("No data object found in AliExpress response");
            }

        } catch (ParseException e) {
            logger.error("Got ParseException in AliExpress response parsing " + e.getMessage());
        }

        logger.debug("Inside processAliExpressResponseMessage () Ends");

        return productList;
    }

    private Product updateAliExpressProductDto(JSONObject content) {

        logger.debug("Inside updateAliExpressProductDto () Starts");

        Product product = new Product();

        product.setSiteName(aliExpressSiteName);

        product.setProductURL(aliExpressPDPURL + content.get("productId") + ".html");

        JSONObject title = (JSONObject) content.get("title");

        if (title != null && title.get("seoTitle") != null) {
            product.setName((String) title.get("seoTitle"));
        }

        JSONObject imageObject = (JSONObject) content.get("image");

        if (imageObject != null && imageObject.get("imageUrl") != null) {
            product.setImageURL((String) imageObject.get("imageUrl"));
        }

        JSONObject prices = (JSONObject) content.get("prices");

        if (prices != null && prices.get("sale_price") != null) {
            JSONObject salePrices = (JSONObject) prices.get("sale_price");
            if (salePrices != null && salePrices.get("minPrice") != null) {
                product.setPrice((double)salePrices.get("minPrice"));
            }
            if (salePrices != null && salePrices.get("currencyCode") != null) {
                product.setCurrencyCode((String) salePrices.get("currencyCode"));
            }
        }

        logger.debug("Inside updateAliExpressProductDto () Ends");

        return product;
    }
}
