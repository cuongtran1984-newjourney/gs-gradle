package com.gs.concurrency.executor;

import java.util.concurrent.CountDownLatch;

class MyThread implements Runnable {
  private final String name;
  private final CountDownLatch latch;

  MyThread(CountDownLatch latch, String name) {
    this.name = name;
    this.latch = latch;
  }

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      System.out.println(name + ": " + i);
      latch.countDown();
    }
  }
}
