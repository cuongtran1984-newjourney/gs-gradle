package com.gs.concurrency.synchronization;

import java.util.concurrent.Semaphore;

class SemaDemo {
  public static void main(String[] args) {
    Semaphore sem = new Semaphore(1);
    new IncThread(sem, "Thread A");
    new DecThread(sem, "Thread B");
  }
}
