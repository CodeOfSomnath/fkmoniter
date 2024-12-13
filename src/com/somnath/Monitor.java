package com.somnath;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

public class Monitor {
    private int id;
    private CountDownLatch latch;

    private HashMap<Integer, WebCrawler> pages;
    private HashMap<Integer, WebCrawler> deadPages;
    private HashMap<Integer, WebCrawler> matchPages;

    public Monitor() {
        id = 0;
        pages = new HashMap<>();
        deadPages = new HashMap<>();
        matchPages = new HashMap<>();
    }

    public void display() {
        WebCrawler page;
        System.out.println("-".repeat(80));
        for (int key : pages.keySet()) {
            page = pages.get(key);
            System.out.printf("ID: %d\nPRODUCT_NAME: %s\nCURRENT_PRICE: Rs.%d\nMAX_PRICE: Rs.%d\nEXPACTED_PRICE:%d\n",
                    key,
                    page.getDocument().title(),
                    page.getCurrentPrice(),
                    page.getFullPrice(),
                    page.getExpactedPrice()
                    );
        }
    }

    public void matchPriceUrls() {
        WebCrawler page;
        System.out.println("-".repeat(80));
        for (int key : matchPages.keySet()) {
            page = matchPages.get(key);
            System.out.printf("ID: %d\nPRODUCT_NAME: %s\nCURRENT_PRICE: Rs.%d\nMAX_PRICE: Rs.%d\nEXPACTED_PRICE:%d\n",
                    key,
                    page.getDocument().title(),
                    page.getCurrentPrice(),
                    page.getFullPrice(),
                    page.getExpactedPrice()
                    );
        }
    }

    public void displayDeadUrls() {
        WebCrawler page;
        System.out.println("-".repeat(80));
        for (int key : deadPages.keySet()) {
            page =  deadPages.get(key);
            System.out.printf("ID: %d\nPRODUCT_NAME: %s\nCURRENT_PRICE: Rs.%d\nMAX_PRICE: Rs.%d\nEXPACTED_PRICE:%d\n",
                    key,
                    page.getDocument().title(),
                    page.getCurrentPrice(),
                    page.getFullPrice(),
                    page.getExpactedPrice());
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

        try {
            latch.await();
            for (int key: pages.keySet()) {
                page = pages.get(key);
                if (page.getExpactedPrice() >= page.getCurrentPrice()) {
                    matchPages.put(key, page);
                    pages.remove(key);
                }
            }
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }

    

    public void addUrl(String url, int expactedPrice) {
        WebCrawler crawler = new WebCrawler(url, expactedPrice);
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
