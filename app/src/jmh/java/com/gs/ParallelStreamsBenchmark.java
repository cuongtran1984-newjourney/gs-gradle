package com.gs;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(
    value = 2,
    warmups = 1,
    jvmArgs = {"-Xms4G", "-Xmx4G"})
@Measurement(iterations = 2)
public class ParallelStreamsBenchmark {
  private static final long N = 10_000_000L;

  public static void main(String... args) throws Exception {
    org.openjdk.jmh.Main.main(args);
  }

  @Benchmark
  public long iterativeSum() {
    long result = 0;
    for (long i = 1L; i <= N; i++) {
      result += i;
    }
    return result;
  }

  @Benchmark
  public long sequentialSum() {
    return Stream.iterate(1L, i -> i + 1).limit(N).reduce(0L, Long::sum);
  }

  @Benchmark
  public long parallelSum() {
    return Stream.iterate(1L, i -> i + 1).limit(N).parallel().reduce(0L, Long::sum);
  }

  public long rangedSum() {
    return LongStream.rangeClosed(1, N).reduce(Long::sum).orElse(0);
  }

  public long parallelRangedSum() {
    return LongStream.rangeClosed(1, N).parallel().reduce(Long::sum).orElse(0);
  }
}
