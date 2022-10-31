package com.elist.knormal.web.scraper;

import com.elist.knormal.client.ERestClient;
import io.javalin.http.Context;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Scraper {
    static final String SHOPRITE_URL = "https://www.shoprite.co.za/c-2256/All-Departments?q=%3Arelevance%3AbrowseAllStoresFacetOff%3AbrowseAllStoresFacetOff&page=";
    static final String FIND_TAG = "hidden productListJSON";
    static final String FIND_TAG_PRODUCT = "product-frame product-ga product_";

    abstract void scrape(Context context);

    abstract void findProducts(String html);

    abstract int findTotalItems(ERestClient client, String base);

    public static void requestDelay(Date start, int delay) {

        try {
            Date now = new Date();
            int time = (int)((now.getTime() - start.getTime()) / 1000);
            TimeUnit.MILLISECONDS.sleep(delay - time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isDigit(StringBuilder sb) {

        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) < '0' || sb.charAt(i) > '9')
                return false;
        }
        return true;
    }
}
