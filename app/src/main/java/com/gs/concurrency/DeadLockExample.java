package com.gs.concurrency;

class DeadLockExample implements Runnable {
  A a = new A();
  B b = new B();

  DeadLockExample() {
    Thread.currentThread().setName("MainThread");
    Thread t = new Thread(this, "RacingThread");
    t.start();
    a.foo(b); // get lock on a in this thread.
    System.out.println("Back in main thread");
  }

  @Override
  public void run() {
    b.bar(a); // get lock on b in other thread.
    System.out.println("Back in other thread");
  }

  public static void main(String[] args) {
    new DeadLockExample();
  }
}
