package com.gs.streams;

import com.gs.concurrency.forkjoin.ForkJoinSumCalculator;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class ParallelStreamsHarness {
  public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
  private static final long N = 10_000_000L;

  public static void main(String[] args) {
    System.out.println(
        "Iterative Sum done in: " + measurePerf(ParallelStreams::iterativeSum, N) + " ms");
    System.out.println(
        "Range forkJoinSum done in: " + measurePerf(ParallelStreams::rangedSum, N) + " ms");
    System.out.println(
        "Parallel range forkJoinSum done in: "
            + measurePerf(ParallelStreams::parallelRangedSum, N)
            + " ms");
    System.out.println(
        "ForkJoin sum done in: " + measurePerf(ForkJoinSumCalculator::forkJoinSum, N) + " ms");
  }

  public static <T, R> long measurePerf(Function<T, R> f, T input) {
    long fastest = Long.MAX_VALUE;
    R result = null;
    for (int i = 0; i < 10; i++) {
      long start = System.nanoTime();
      result = f.apply(input);
      long duration = (System.nanoTime() - start) / 1_000_000;
      if (duration < fastest) {
        fastest = duration;
      }
    }
    System.out.println("Result: " + result);
    return fastest;
  }
}
