package com.somnath;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

// This class for helping page refresh like task
public class HelperThread extends Thread {
    private boolean canExit = false;
    private CountDownLatch latch;
    private WebCrawler page;

    void exit() {
        canExit = true;
    }

    public HelperThread(CountDownLatch latch, WebCrawler crawler) {
       page = crawler;
       this.latch = latch;
    }

    public void refresh() {
        
        try {
            page.getPage();
            Thread.sleep(Duration.ofSeconds(10));
        } catch (Exception e) {
            // canExit = true;
            // e.printStackTrace();
        }
    } 

    @Override
    public void run() {
        while (!canExit) {
            refresh();
        }
        latch.countDown();
    }
}
