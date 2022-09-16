package com.elist.knormal;

/**
 * EList by Lucky Makhathini
 *
 */

import io.javalin.Javalin;

public class App 
{
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
        app.get("/", ctx -> Scraper.scrape(ctx));
    }
}
