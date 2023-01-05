package com.elist.knormal.web.scraper;

import com.elist.knormal.ItemController;
import com.elist.knormal.beans.ShopBean;
import com.elist.knormal.client.ERestClient;
import com.elist.knormal.commons.ShopriteCommons;
import com.elist.knormal.db.ProductJDBC;
import io.javalin.http.Context;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;

public class SRScraper extends Scraper {
    private ShopBean shopBean;
    private static ProductJDBC jdbcObject = ProductJDBC.getInstance();
    private static List<JSONObject> jList = new ArrayList<>();

    @Override
    public void scrape(Context context) {
        ItemController.refreshItemList(context);
        ERestClient client = ERestClient.getInstance();
        int j = 0;
        for (String base : ShopriteCommons.BASE_URLS) {
            int totalNumberOfItems = findTotalItems(client, base) / 20;
            String[] links = ShopriteCommons.LINKS;
            for (int i = 0; i < totalNumberOfItems; i++) {
                Date start = new Date();
                findProducts(client.doGetRequest(links[j] + i));
                System.out.println(links[j] + i);
                requestDelay(start, 10000);
            }
            j++;
        }
        jdbcObject.insertShopriteItemsList(jList);
    }

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
            JSONObject jsonObject = new JSONObject(checkJson(productString));
            jList.add(jsonObject);
        }
    }

    @Override
    public int findTotalItems(ERestClient client, String base) {
        //find the number of items.
        String html = client.doGetRequest(base);
        html = html.substring(html.indexOf(ShopriteCommons.NUMBER_OF_ITEMS));
        String items = html.substring(ShopriteCommons.NUMBER_OF_ITEMS.length() + 3, html.indexOf("items") - 1);
        StringBuilder stringBuilder = new StringBuilder(items);
        while(!items.matches("\\d")) { //check if string is digits.
            stringBuilder.deleteCharAt(items.indexOf(","));
        }
        System.out.println(stringBuilder);
        return Integer.parseInt(stringBuilder.toString());
    }

    private final String checkJson(String productString) {

        if (productString.indexOf("'") == 0) {
            return productString.split("'")[1];
        }
        return productString;
    }
}
