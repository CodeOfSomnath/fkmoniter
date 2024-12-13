package com.somnath.fkmon;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

// This class for helping page refresh like task
public class HelperThread extends Thread {
    private CountDownLatch latch;
    private Products page;


    public HelperThread(CountDownLatch latch, Products crawler) {
        page = crawler;
        this.latch = latch;
    }

    public void refresh() {

        try {
            page.updatePage();
            Thread.sleep(Duration.ofSeconds(10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        refresh();
        latch.countDown();
    }
}
