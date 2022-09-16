package com.elist.knormal;

import com.elist.knormal.beans.ShopriteJsonBean;
import com.elist.knormal.client.ERestClient;
import com.elist.knormal.db.ProductJDBC;
import io.javalin.http.Context;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Scraper {
    private static final String SHOPRITE_URL = "https://www.shoprite.co.za/c-2256/All-Departments?q=%3Arelevance%3AbrowseAllStoresFacetOff%3AbrowseAllStoresFacetOff&page=1";
    private static final String FIND_TAG = "hidden productListJSON";
    private static final String FIND_TAG_PRODUCT = "product-frame product-ga product_";
    private ShopriteJsonBean shopriteBean;

    public Scraper() {
        shopriteBean = ShopriteJsonBean.getInstance();
    }

    public static void scrape(Context context) {
        //ERestClient client = ERestClient.getInstance();
        //find(client.doRequest(SHOPRITE_URL));
        context.result("Hello world!");
    }

    private static String find(String html) {
        int index = 26 + html.indexOf(FIND_TAG);
        String json = html.substring(index);
        int lastIndex = json.indexOf("]");
        json = json.substring(0, lastIndex);
        String[] brokenJsonArray = json.split(",");

        System.out.println(json);

        List<String> codes = new ArrayList<>();
        for (String code : brokenJsonArray) {
            if(code.contains("code")) {
                codes.add(code.substring(10, 19));
            }
        }

        for (String tmp : codes) {
            int productIndex = 62 +  html.indexOf(FIND_TAG_PRODUCT + tmp);
            String productString =  html.substring(productIndex);
            productString = productString.substring(0, 1 + productString.indexOf("}"));
            JSONObject jsonObject = new JSONObject(productString);
            System.out.println(productString);
        }

        return null;
    }
}
