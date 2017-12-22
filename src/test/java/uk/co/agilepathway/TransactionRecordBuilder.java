package uk.co.agilepathway;

import java.time.Instant;

import javax.money.MonetaryAmount;

import static uk.co.agilepathway.Pound.pounds;

public class TransactionRecordBuilder {

  private String to = "12345678";
  private String from = "87654321";
  private MonetaryAmount amount = pounds(10);
  private Instant timestamp = Instant.EPOCH;

  public TransactionRecord build() {
    return new TransactionRecord(to, from, amount, timestamp);
  }

  public TransactionRecordBuilder from(String from) {
    this.from = from;
    return this;
  }

  public TransactionRecordBuilder to(String to) {
    this.to = to;
    return this;
  }

  public TransactionRecordBuilder amount(MonetaryAmount amount) {
    this.amount = amount;
    return this;
  }

  public TransactionRecordBuilder timestamp(Instant timestamp) {
    this.timestamp = timestamp;
    return this;
  }

}
