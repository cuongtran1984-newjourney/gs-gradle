package com.gs.concurrency.synchronization;

class Consumer implements Runnable {
  SimpleQueue q;

  Consumer(SimpleQueue q) {
    this.q = q;
    new Thread(this, "Consumer").start();
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) q.get();
  }
}
