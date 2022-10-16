package com.elist.knormal.web.scraper;

import io.javalin.http.Context;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Scraper {

    abstract void scrape(Context context);

    abstract void findProducts(String html);

    public static void requestDelay(Date start, int delay) {

        try {
            Date now = new Date();
            int time = (int)((now.getTime() - start.getTime()) / 1000);
            TimeUnit.MILLISECONDS.sleep(delay - time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
