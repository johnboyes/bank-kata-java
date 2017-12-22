package uk.co.agilepathway;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.Objects;

public class TransactionRecord {

  public final String from;
  public final String to;
  public final MonetaryAmount amount;
  public final Instant timestamp;

  public TransactionRecord(String from, String to, MonetaryAmount amount, Instant timestamp) {
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public boolean isToOrFrom(String number) {
    return (from.equals(number)) || (to.equals(number));
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
