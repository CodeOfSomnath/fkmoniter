package com.somnath.fkmon;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class Monitor {
    private int id;
    private CountDownLatch latch;
    private HashMap<Integer, Products> urls;
    private HashMap<Integer, Products> deadUrls;
    private HashMap<Integer, Products> matchedUrls;

    public Monitor() {
        id = 0;
        urls = new HashMap<>();
        deadUrls = new HashMap<>();
        matchedUrls = new HashMap<>();
    }

    /**
     * Displays the products which are currently actives
     */
    public void display() {
        Products page;
        System.out.println("-".repeat(80));
        for (int key : urls.keySet()) {
            page = urls.get(key);
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
        Products page;
        System.out.println("-".repeat(80));
        for (int key : matchedUrls.keySet()) {
            page = matchedUrls.get(key);
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
        Products page;
        System.out.println("-".repeat(80));
        for (int key : deadUrls.keySet()) {
            page =  deadUrls.get(key);
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
        Products page;
        latch = new CountDownLatch(urls.keySet().size());
        for (int key: urls.keySet()) {
            page = urls.get(key);
            new HelperThread(latch, page).start();
        }

        try {
            latch.await();
            for (int key: urls.keySet()) {
                page = urls.get(key);
                if (page.getExpactedPrice() >= page.getCurrentPrice()) {
                    matchedUrls.put(key, page);
                    urls.remove(key);
                }
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }

    

    public void addUrl(String url, int expactedPrice) {
        Products crawler = new Products(url, expactedPrice);
        urls.put(id, crawler);
    
        id++;
    }

    public void removeUrl(int id) {
        Products page = urls.remove(id);
        deadUrls.put(id, page);
    }

    public void enableUrl(int id) {
        Products page = deadUrls.remove(id);
        urls.put(id, page);
    }

}