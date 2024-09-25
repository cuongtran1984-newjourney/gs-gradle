package com.gs.concurrency.synchronization;

class Producer implements Runnable {
  SimpleQueue q;

  Producer(SimpleQueue q) {
    this.q = q;
    new Thread(this, "Producer").start();
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) q.put(i);
  }
}
