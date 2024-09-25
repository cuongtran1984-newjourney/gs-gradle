package com.gs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {
  public String getGreeting() {
    return "Hello World!";
  }

  public static void main(String[] args) {
    System.out.println(new App().getGreeting());
    LocalDate localDt =
        LocalDate.parse("2024-02-30", DateTimeFormatter.ofPattern("[M/d/yyyy]" + "[yyyy-M-d]"));
    System.out.println(localDt);
  }
}
