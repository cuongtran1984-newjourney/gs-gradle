package com.gs.concurrency.synchronization;

import java.util.concurrent.Semaphore;

class IncThread implements Runnable {
  private String name;
  private Semaphore sem;

  public IncThread(Semaphore sem, String name) {
    this.name = name;
    this.sem = sem;
    new Thread(this).start();
  }

  @Override
  public void run() {
    System.out.println("Starting " + name);
    try {
      // First, gets a permit
      System.out.println(name + " is waiting for a permit.");
      // sem.acquire();
      System.out.println(name + " gets a permit.");
      // Now, access shared resource
      for (int i = 0; i < 5; i++) {
        SharedCounter.count++;
        System.out.println(name + ": " + SharedCounter.count);
        // allow a context switch if possible
        Thread.sleep(10);
      }
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    // Release the permit
    System.out.println(name + " releases the permit.");
    // sem.release();
  }
}
