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
        app.get("/scrape", ctx -> Scraper.scrape(ctx));
        app.get("/refresh", ctx -> ItemController.refreshItemList(ctx));
        app.get("/items", ctx -> ItemController.getItems(ctx));
    }
}
