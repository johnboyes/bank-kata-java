package uk.co.agilepathway;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Account {

  private MonetaryAmount balance = Pound.of(0);
  private List<TransactionRecord> transactionHistory = new ArrayList<TransactionRecord>();
  private String accountNumber;

  public Account(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public Instant transfer(Account to, MonetaryAmount amount) {
    balance = balance.subtract(amount);
    Instant timestamp = Instant.now();
    to.receiveTransfer(this, amount, timestamp);
    TransactionRecord transactionRecord = new TransactionRecord(this.accountNumber, to.accountNumber, amount,
        timestamp);
    transactionHistory.add(transactionRecord);
    return timestamp;
  }

  public void receiveTransfer(Account from, MonetaryAmount amount, Instant timestamp) {
    balance = balance.add(amount);
    transactionHistory.add(new TransactionRecord(from.accountNumber, this.accountNumber, amount, timestamp));
  }

  public MonetaryAmount getBalance() {
    return balance;
  }

  public List<TransactionRecord> getTransactionHistory() {
    return transactionHistory;
  }

  public List<TransactionRecord> getTransferHistoryToOrFromAccount(String accountNumber) {
    return transactionHistory.stream().filter(record -> record.isToOrFrom(accountNumber))
        .collect(Collectors.toCollection(ArrayList::new));
  }

}
