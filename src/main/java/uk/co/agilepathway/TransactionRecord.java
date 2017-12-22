package uk.co.agilepathway;

import java.util.Objects;

import java.time.Instant;

import javax.money.MonetaryAmount;

/**
 *  A simple record of a transaction -
 *  one item in an account's transaction history.
 */
public class TransactionRecord {

  public final String from;
  public final String to;
  public final MonetaryAmount amount;
  public final Instant timestamp;

  /**
   *  @param from The account number of the payer
   *  @param to The account number of the payee
   *  @param amount The amount of the transfer
   *  @param timestamp The time of the transfer.  Both the payer and the
                       payee have the same timestamp in their respective
                       transaction records.
   */
  public TransactionRecord(String from, String to, MonetaryAmount amount, Instant timestamp) {
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  /**
   *  @return true if the transaction was a transfer to or from
              the given account number
   */
  public boolean isToOrFrom(String accountNumber) {
    return to.equals(accountNumber) || from.equals(accountNumber);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof TransactionRecord)) {
      return false;
    }
    TransactionRecord t = (TransactionRecord) o;
    return Objects.equals(from, t.from) && Objects.equals(to, t.to) && Objects.equals(amount, t.amount)
        && Objects.equals(timestamp, t.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, amount, timestamp);
  }
}
