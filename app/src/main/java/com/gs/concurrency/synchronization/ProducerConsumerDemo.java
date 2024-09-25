package com.gs.concurrency.synchronization;

class ProducerConsumerDemo {
  public static void main(String[] args) {
    SimpleQueue q = new SimpleQueue();
    new Producer(q);
    new Consumer(q);
    System.out.println("Press Control-C to stop.");
  }
}
