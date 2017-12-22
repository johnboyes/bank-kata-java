package uk.co.agilepathway;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

/**
 *  Utility class for working with {@link MonetaryAmount} in British pounds
 */
final class Pound {

  private Pound() {
  }

  /**
   * @return a {@link MonetaryAmount} in British pounds
   */
  public static MonetaryAmount pounds(int number) {
    return Monetary.getDefaultAmountFactory().setNumber(number).setCurrency("GBP").create();
  }

}
