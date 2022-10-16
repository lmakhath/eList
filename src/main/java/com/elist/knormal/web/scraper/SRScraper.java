package com.elist.knormal.web.scraper;

import com.elist.knormal.ItemController;
import com.elist.knormal.beans.ShopriteBean;
import com.elist.knormal.client.ERestClient;
import com.elist.knormal.db.ProductJDBC;
import io.javalin.http.Context;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.Date;

public class SRScraper extends Scraper {
    private static final String SHOPRITE_URL = "https://www.shoprite.co.za/c-2256/All-Departments?q=%3Arelevance%3AbrowseAllStoresFacetOff%3AbrowseAllStoresFacetOff&page=";
    private static final String FIND_TAG = "hidden productListJSON";
    private static final String FIND_TAG_PRODUCT = "product-frame product-ga product_";
    private ShopriteBean shopriteBean;
    private static ProductJDBC jdbcObject = ProductJDBC.getInstance();
    private static List<JSONObject> jList = new ArrayList<>();

    @Override
    public void scrape(Context context) {
        ItemController.refreshItemList(context);
        ERestClient client = ERestClient.getInstance();
        for (int i = 0; i < 500; i++) {
            Date start = new Date();
            findProducts(client.doGetRequest(SHOPRITE_URL + i));
            System.out.println(SHOPRITE_URL + i);
            requestDelay(start, 10000);
        }
        jdbcObject.insertShopriteItemsList(jList);
        //test();
    }

    /*private static void test() {
        try {
            Scanner sc = new Scanner(new File("jsonstrings.txt"));
            while(sc.hasNextLine()) {
                //System.out.println(sc.nextLine());
                String tmp = sc.nextLine();
                System.out.println(tmp);
                jList.add(new JSONObject(tmp));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        jdbcObject.insertShopriteItemsList(jList);
    }*/

    @Override
    public final void findProducts(String html) {
        if (html == null)
            return;
        int index = 26 + html.indexOf(FIND_TAG);
        String json = html.substring(index);
        int lastIndex = json.indexOf("]");
        json = json.substring(0, lastIndex);
        String[] brokenJsonArray = json.split(",");

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
            //System.out.println(productString);
            JSONObject jsonObject = new JSONObject(checkJson(productString));
            jList.add(jsonObject);
        }
    }

    private final String checkJson(String productString) {

        if (productString.indexOf("'") == 0) {
            return productString.split("'")[1];
        }
        return productString;
    }
}
