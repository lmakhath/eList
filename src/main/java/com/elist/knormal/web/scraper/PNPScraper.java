package com.elist.knormal.web.scraper;

import com.elist.knormal.client.ERestClient;
import io.javalin.http.Context;

public class PNPScraper extends Scraper {
    @Override
    void scrape(Context context) {

    }

    @Override
    void findProducts(String html) {

    }

    @Override
    int findTotalItems(ERestClient client, String base) {
        return 0;
    }
}
