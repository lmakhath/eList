package com.elist.knormal;

import com.elist.knormal.web.scraper.PNPScraper;
import com.elist.knormal.web.scraper.SRScraper;
import com.elist.knormal.web.scraper.Scraper;
import java.util.Arrays;
import java.util.List;

public class Scrape {

    public void toScrape() {

        List<Scraper> list = Arrays.asList(new SRScraper(), new PNPScraper());
        list.forEach(sc -> new ScrapeThread(sc).start());
    }
}

class ScrapeThread extends Thread {
    Scraper scrapeObject;
    public ScrapeThread(Scraper scrapeObject) {
        this.scrapeObject = scrapeObject;
    }

    public void run()
    {
        try {
            scrapeObject.scrape();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
