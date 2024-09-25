package com.gs.concurrency.synchronization;

import java.util.concurrent.Semaphore;

class SimpleQueue {
  private int n;
  private Semaphore semCon = new Semaphore(0);
  private Semaphore semProd = new Semaphore(1);

  void get() {
    try {
      semCon.acquire();
    } catch (InterruptedException e) {
      System.out.println("InterruptedException caught");
    }
    System.out.println("Got: " + n);
    semProd.release();
  }

  void put(int n) {
    try {
      semProd.acquire();
    } catch (InterruptedException e) {
      System.out.println("InterruptedException caught");
    }
    this.n = n;
    System.out.println("Put: " + n);
    semCon.release();
  }
}
