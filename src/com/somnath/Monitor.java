package com.somnath;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class Monitor {
    private int id;
    private CountDownLatch latch;

    private HashMap<Integer, WebCrawler> pages;
    private HashMap<Integer, WebCrawler> deadPages;

    public Monitor() {
        id = 0;
        pages = new HashMap<>();
        deadPages = new HashMap<>();
    }

    public void display() throws Exception {
        WebCrawler page;
        System.out.println("-".repeat(80));
        for (int key : pages.keySet()) {
            page = pages.get(key);
            System.out.printf("ID: %d\nPRODUCT_NAME: %s\nCURRENT_PRICE: Rs.%d\nMAX_PRICE: Rs.%d\n",
                    key,
                    page.getDocument().title(),
                    page.getCurrentPrice(),
                    page.getFullPrice());
        }
    }

    public void displayDeadUrls() throws Exception {
        WebCrawler page;
        System.out.println("-".repeat(80));
        for (int key : deadPages.keySet()) {
            page =  deadPages.get(key);
            System.out.printf("ID: %d\nPRODUCT_NAME: %s\nCURRENT_PRICE: Rs.%d\nMAX_PRICE: Rs.%d\n",
                    key,
                    page.getDocument().title(),
                    page.getCurrentPrice(),
                    page.getFullPrice());
        }
    }

    public void refresh() {
        // Todo: This function will be used to refresh the data from server using threads
        // HelperThread thread;
        WebCrawler page;
        latch = new CountDownLatch(pages.keySet().size());
        for (int key: pages.keySet()) {
            page = pages.get(key);
            new HelperThread(latch, page).start();
        }
        
    }

    

    public void addUrl(String url) {
        WebCrawler crawler = new WebCrawler(url);
        pages.put(id, crawler);
    
        id++;
    }

    public void removeUrl(int id) {
            WebCrawler page = pages.remove(id);
            deadPages.put(id, page);
    }

    public void enableUrl(int id) {
        WebCrawler page = deadPages.remove(id);
        pages.put(id, page);
    }

}
