package com.gs.streams;

import java.util.stream.LongStream;

public class ParallelStreams {
  public static long iterativeSum(long n) {
    long result = 0;
    long[] numbers = LongStream.rangeClosed(1, n).toArray();
    for (long number : numbers) {
      result += number;
    }
    return result;
  }

  public static long rangedSum(long n) {
    return LongStream.rangeClosed(1, n).reduce(Long::sum).orElse(0);
  }

  public static long parallelRangedSum(long n) {
    return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).orElse(0);
  }
}
