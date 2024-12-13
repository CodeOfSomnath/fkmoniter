/**
 * Copyright 2024 Somnath Nandi
 * This class will be use to crawl all the data needed from internet
 */

package com.somnath.fkmon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Products {
    private String url;
    private Document soup;
    private int expactedPrice;


    public Products(String url, int expactedPrice) {
        this.url = url;
        this.expactedPrice = expactedPrice;
    }

    public int getExpactedPrice() {
        return expactedPrice;
    }

    public void setExpactedPrice(int expactedPrice) {
        this.expactedPrice = expactedPrice;
    }

    public void getPage() throws Exception {
        Document soup = Jsoup.connect(this.url)
            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
            .header("Sec-Fetch-Dest", "document")
            .get();
        this.soup = soup;
    }

    public void updatePage() {
        try {
            refresh();
            getCurrentPrice();
            getFullPrice();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void refresh() {
        // it is just look good
        try {
            this.getPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCurrentPrice()  {
        this.refresh();
        Elements ems =  soup.select(".hl05eU div");
        return Integer.parseInt(ems.get(0).text().replaceAll("₹", "").replaceAll(",", ""));
    }

    public int getFullPrice() {
        this.refresh();
        Elements ems =  soup.select(".hl05eU div");
        return Integer.parseInt(ems.get(1).text().replaceAll("₹", "").replaceAll(",", ""));
    }

    public Document getDocument() {
        return this.soup;
    }

}
