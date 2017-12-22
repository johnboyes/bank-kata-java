package uk.co.agilepathway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import static uk.co.agilepathway.Pound.pounds;

import java.util.List;
import java.util.ArrayList;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class FunctionalTests {

  private String firstAccountNumber;
  private String secondAccountNumber;
  private String thirdAccountNumber;
  private Account firstAccount;
  private Account secondAccount;
  private Account thirdAccount;

  @BeforeEach
  public void setUp() {
    firstAccountNumber = "12345678";
    secondAccountNumber = "87654321";
    thirdAccountNumber = "43211234";
    firstAccount = new Account(firstAccountNumber);
    secondAccount = new Account(secondAccountNumber);
    thirdAccount = new Account(thirdAccountNumber);
  }

  @Test
  public void transferShouldUpdatePayerAndPayeeBalances() {
    firstAccount.transfer(pounds(50), secondAccount);

    assertEquals(pounds(-50), firstAccount.getBalance());
    assertEquals(pounds(50), secondAccount.getBalance());
  }

  @Test
  public void transferShouldUpdatePayerAndPayeeTransactionHistories() {
    Instant firstTimestamp = firstAccount.transfer(pounds(50), secondAccount);
    Instant secondTimestamp = firstAccount.transfer(pounds(40), secondAccount);

    List<TransactionRecord> expected = new ArrayList<TransactionRecord>();
    expected.add(new TransactionRecord(firstAccountNumber, secondAccountNumber, pounds(50), firstTimestamp));
    expected.add(new TransactionRecord(firstAccountNumber, secondAccountNumber, pounds(40), secondTimestamp));
    assertIterableEquals(expected, firstAccount.getTransactionHistory());
    assertIterableEquals(expected, secondAccount.getTransactionHistory());
  }

  @Test
  @SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
  public void queryAccountTransactionHistoryForTransfersToOrFromSpecificAccount() {
    Instant firstTimestamp = firstAccount.transfer(pounds(50), secondAccount);
    firstAccount.transfer(pounds(40), thirdAccount);
    Instant thirdTimestamp = secondAccount.transfer(pounds(30), firstAccount);

    List<TransactionRecord> expected = new ArrayList<TransactionRecord>();
    expected.add(new TransactionRecord(firstAccountNumber, secondAccountNumber, pounds(50), firstTimestamp));
    expected.add(new TransactionRecord(secondAccountNumber, firstAccountNumber, pounds(30), thirdTimestamp));
    assertIterableEquals(expected, firstAccount.getTransferHistoryToOrFromAccount(secondAccountNumber));
  }

}
