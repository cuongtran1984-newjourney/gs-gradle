package com.gs.concurrency.forkjoin;

import static com.gs.streams.ParallelStreamsHarness.FORK_JOIN_POOL;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {
  // The sequential threshold value.
  private static final int THRESHOLD = 10_000;
  // Array to be accessed.
  private final long[] numbers;
  // Determines what part of data to process.
  private final int start;
  private final int end;

  public ForkJoinSumCalculator(long[] numbers) {
    this(numbers, 0, numbers.length);
  }

  private ForkJoinSumCalculator(long[] numbers, int start, int end) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    long sum = 0;
    // If number of elements is below the sequential threshold,
    // then process sequentially.
    if ((end - start) < THRESHOLD) {
      // Sum the elements.
      for (int i = start; i < end; i++) sum += numbers[i];
    } else {
      // Otherwise, continue to break the data into smaller pieces.
      // Find the midpoint.
      int middle = (start + end) / 2;
      // Invoke new tasks, using the subdivided data.
      ForkJoinSumCalculator subTaskA = new ForkJoinSumCalculator(numbers, start, middle);
      ForkJoinSumCalculator subTaskB = new ForkJoinSumCalculator(numbers, middle, end);
      // Start each subtask by forking.
      subTaskA.fork();
      // Wait for the subtasks to return, and aggregate the results.
      sum = subTaskB.compute() + subTaskA.join();
    }
    // Return the final sum.
    return sum;
  }

  public static long forkJoinSum(long n) {
    long[] numbers = LongStream.rangeClosed(1, n).toArray();
    ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
    return FORK_JOIN_POOL.invoke(task);
  }
}
