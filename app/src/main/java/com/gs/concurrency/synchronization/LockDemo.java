package com.gs.concurrency.synchronization;

import java.util.concurrent.locks.ReentrantLock;

class LockDemo {
  public static void main(String[] args) {
    ReentrantLock lock = new ReentrantLock();
    new LockThread(lock, "A");
    new LockThread(lock, "B");
  }

  static class LockThread implements Runnable {
    String name;
    ReentrantLock lock;

    LockThread(ReentrantLock lk, String n) {
      lock = lk;
      name = n;
      new Thread(this).start();
    }

    @Override
    public void run() {
      System.out.println("Starting " + name);
      try {
        // First, lock count
        System.out.println(name + " is waiting to lock count.");
        lock.lock();
        System.out.println(name + " is locking count.");
        SharedCounter.count++;
        System.out.println(name + ": " + SharedCounter.count);
        // Now, allow a context switch -- if possible
        System.out.println(name + " is sleeping.");
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println(e);
      } finally {
        // Unlock
        System.out.println(name + " is unlocking count.");
        lock.unlock();
      }
    }
  }
}
