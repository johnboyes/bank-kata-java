package uk.co.agilepathway;

import static uk.co.agilepathway.Pound.pounds;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.time.Instant;

import javax.money.MonetaryAmount;

/**
 *  Represents an individual's bank account
 */
public class Account {

  private MonetaryAmount balance = pounds(0);
  private List<TransactionRecord> transactionHistory = new ArrayList<TransactionRecord>();
  private String accountNumber;

  public Account(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   *  Transfers an amount from a payer to a payee's account
   *  Debits the amount from the payer's account
   *  @return the time of the transfer
   *          (from the generated {@link TransactionRecord})
   */
  public Instant transfer(MonetaryAmount amount, Account to) {
    balance = balance.subtract(amount);
    Instant timestamp = Instant.now();
    to.receiveTransfer(amount, this, timestamp);
    TransactionRecord transactionRecord = new TransactionRecord(this.accountNumber, to.accountNumber, amount,
        timestamp);
    transactionHistory.add(transactionRecord);
    return timestamp;
  }

  /**
   *  Receive a transfer for an amount from a payer's account
   *  Credits the amount to the payee's account
   */
  public void receiveTransfer(MonetaryAmount amount, Account from, Instant timestamp) {
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
