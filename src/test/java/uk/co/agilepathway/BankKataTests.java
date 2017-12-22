package uk.co.agilepathway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.Instant;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.ArrayList;

public class BankKataTests {

  private Account payer;
  private Account payee;

  @BeforeEach
  public void setUp() {
    payer = new Account("1");
    payee = new Account("2");
  }

  @Test
  public void transferShouldUpdatePayerAndPayeeBalances() {
    payer.transfer(payee, Pound.of(50));

    assertEquals(Pound.of(-50), payer.getBalance());
    assertEquals(Pound.of(50), payee.getBalance());
  }

  @Test
  public void transferShouldUpdatePayerAndPayeeTransactionHistories() {
    Instant firstTimestamp = payer.transfer(payee, Pound.of(50));
    Instant secondTimestamp = payer.transfer(payee, Pound.of(40));

    List<TransactionRecord> expected = new ArrayList<TransactionRecord>();
    expected.add(new TransactionRecord("1", "2", Pound.of(50), firstTimestamp));
    expected.add(new TransactionRecord("1", "2", Pound.of(40), secondTimestamp));
    assertIterableEquals(expected, payer.getTransactionHistory());
    assertIterableEquals(expected, payee.getTransactionHistory());
  }

  @Test
  @SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
  public void queryAccountTransactionHistoryForTransfersToOrFromSpecificAccount() {
    Instant firstTimestamp = payer.transfer(payee, Pound.of(50));
    payer.transfer(new Account("3"), Pound.of(40));
    Instant thirdTimestamp = payer.transfer(payee, Pound.of(30));

    List<TransactionRecord> expected = new ArrayList<TransactionRecord>();
    expected.add(new TransactionRecord("1", "2", Pound.of(50), firstTimestamp));
    expected.add(new TransactionRecord("1", "2", Pound.of(30), thirdTimestamp));
    assertIterableEquals(expected, payer.getTransferHistoryToOrFromAccount("2"));
  }

}
