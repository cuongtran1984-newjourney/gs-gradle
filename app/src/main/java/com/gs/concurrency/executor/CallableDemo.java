package com.gs.concurrency.executor;

import java.util.concurrent.*;

class CallableDemo {
  public static void main(String[] args) {
    System.out.println("Starting");
    ExecutorService es = Executors.newFixedThreadPool(3);
    Future<Integer> f = es.submit(new Sum(10));
    Future<Double> f2 = es.submit(new Hypot(3, 4));
    Future<Integer> f3 = es.submit(new Factorial(5));

    try {
      System.out.println(f.get(10, TimeUnit.MILLISECONDS));
      System.out.println(f2.get(10, TimeUnit.MILLISECONDS));
      System.out.println(f3.get(10, TimeUnit.MILLISECONDS));
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      System.out.println(e);
    }
    es.shutdown();
    System.out.println("Done");
  }

  // Following are three computational threads.
  static class Sum implements Callable<Integer> {
    int stop;

    Sum(int v) {
      stop = v;
    }

    public Integer call() {
      int sum = 0;
      for (int i = 1; i <= stop; i++) {
        sum += i;
      }
      return sum;
    }
  }

  static class Hypot implements Callable<Double> {
    double side1, side2;

    Hypot(double s1, double s2) {
      side1 = s1;
      side2 = s2;
    }

    public Double call() {
      return Math.sqrt((side1 * side1) + (side2 * side2));
    }
  }

  static class Factorial implements Callable<Integer> {
    int stop;

    Factorial(int v) {
      stop = v;
    }

    public Integer call() {
      int fact = 1;
      for (int i = 2; i <= stop; i++) {
        fact *= i;
      }
      return fact;
    }
  }
}
