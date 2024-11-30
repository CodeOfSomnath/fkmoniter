package com.somnath;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

public class Monitor {
    private int id;
    private CountDownLatch latch;

    private HashMap<Integer, WebCrawler> pages;

    public void display() throws Exception {
        WebCrawler page;
        System.out.println("\n\nID\t\tPRODUCT_NAME\t\tCURRENT_PRICE\t\tMAX_PRICE");
        System.out.println("-".repeat(80));
        for (int key : pages.keySet()) {
            page = pages.get(key);
            System.out.printf("%d\t\t%s\t\t%d\t\t%d",
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
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Monitor() {
        id = 0;
        pages = new HashMap<>();
    }

    public void addUrl(String url) {
        WebCrawler crawler = new WebCrawler(url);
        pages.put(id, crawler);
    
        id++;
    }

}
