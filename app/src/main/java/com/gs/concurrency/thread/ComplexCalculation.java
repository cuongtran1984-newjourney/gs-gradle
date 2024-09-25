package com.gs.concurrency.thread;

import java.math.BigInteger;

public class ComplexCalculation {
  public static void main(String[] args) {
    BigInteger result =
        new ComplexCalculation()
            .calculate(
                BigInteger.valueOf(10),
                BigInteger.valueOf(2),
                BigInteger.valueOf(5),
                BigInteger.valueOf(10));
    System.out.println(result);
  }

  public BigInteger calculate(
      BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
    BigInteger result = BigInteger.ONE;
    // calculate result = (base1 ^ power1) + (base2 ^ power2)
    // where each calculation is on a different thread
    PowerCalculationThread t1 = new PowerCalculationThread(base1, power1);
    PowerCalculationThread t2 = new PowerCalculationThread(base2, power2);
    t1.start();
    t2.start();
    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return t1.getResult().add(t2.getResult());
  }

  private static class PowerCalculationThread extends Thread {
    private final BigInteger base;
    private final BigInteger power;
    private BigInteger result = BigInteger.ONE;

    public PowerCalculationThread(BigInteger base, BigInteger power) {
      this.base = base;
      this.power = power;
    }

    @Override
    public void run() {
      // implement the calculation of result = base ^ power
      for (BigInteger i = BigInteger.ONE; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
        result = result.multiply(base);
      }
    }

    public BigInteger getResult() {
      return result;
    }
  }
}
