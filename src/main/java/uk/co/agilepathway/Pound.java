package uk.co.agilepathway;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

final class Pound {

  private Pound() {
  }

  public static MonetaryAmount of(int number) {
    return Monetary.getDefaultAmountFactory().setNumber(number).setCurrency("GBP").create();
  }

}
