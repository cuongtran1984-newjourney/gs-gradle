package com.gs.streams;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WordCounterDemo {
  public static final String SENTENCE =
      " Nel   mezzo del cammin  di nostra  vita "
          + "mi  ritrovai in una  selva oscura"
          + " che la  dritta via era   smarrita ";

  public static void main(String[] args) {
    System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
    System.out.println("Found " + countWords(SENTENCE) + " words");
  }

  public static int countWordsIteratively(String s) {
    int counter = 0;
    boolean lastSpace = true;
    for (char c : s.toCharArray()) {
      if (Character.isWhitespace(c)) {
        lastSpace = true;
      } else {
        if (lastSpace) {
          counter++;
        }
        lastSpace = Character.isWhitespace(c);
      }
    }
    return counter;
  }

  public static int countWords(String s) {
    Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
    return countWords(stream);
  }

  private static int countWords(Stream<Character> stream) {
    WordCounter wordCounter =
        stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
    return wordCounter.getCounter();
  }

  private static class WordCounter {
    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
      this.counter = counter;
      this.lastSpace = lastSpace;
    }

    public WordCounter accumulate(Character c) {
      if (Character.isWhitespace(c)) {
        return lastSpace ? this : new WordCounter(counter, true);
      } else {
        return lastSpace ? new WordCounter(counter + 1, false) : this;
      }
    }

    public WordCounter combine(WordCounter wordCounter) {
      return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    public int getCounter() {
      return counter;
    }
  }
}
