package uk.co.agilepathway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static uk.co.agilepathway.Pound.pounds;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TransactionRecordTests {

  @Test
  public void isToOrFromShouldReturnTrueWhenPayerMatches() {
    assertTrue(new TransactionRecordBuilder().from("12345678").build().isToOrFrom("12345678"));
  }

  @Test
  public void isToOrFromShouldReturnTrueWhenPayeeMatches() {
    assertTrue(new TransactionRecordBuilder().to("12345678").build().isToOrFrom("12345678"));
  }

  @Test
  public void isToOrFromShouldReturnFalseWhenNeitherPayerNorPayeeMatch() {
    assertFalse(new TransactionRecordBuilder().from("12345678").to("87654321").build().isToOrFrom("43211234"));
  }

  @Test
  public void recordsWithSamePropertiesShouldBeEqual() {
    assertEqualsAndAlsoHashCodeEquals(new TransactionRecordBuilder().build(), new TransactionRecordBuilder().build());
  }

  @Test
  public void recordsWithDifferentPayersShouldNotBeEqual() {
    TransactionRecord firstRecord = new TransactionRecordBuilder().from("12345678").build();
    TransactionRecord secondRecord = new TransactionRecordBuilder().from("87654321").build();
    assertNotEqualsAndAlsoHashCodeNotEquals(firstRecord, secondRecord);
  }

  @Test
  public void recordsWithDifferentPayeesShouldNotBeEqual() {
    TransactionRecord firstRecord = new TransactionRecordBuilder().to("12345678").build();
    TransactionRecord secondRecord = new TransactionRecordBuilder().to("87654321").build();
    assertNotEqualsAndAlsoHashCodeNotEquals(firstRecord, secondRecord);
  }

  @Test
  public void recordsWithDifferentAmountsShouldNotBeEqual() {
    TransactionRecord firstRecord = new TransactionRecordBuilder().amount(pounds(5)).build();
    TransactionRecord secondRecord = new TransactionRecordBuilder().amount(pounds(10)).build();
    assertNotEqualsAndAlsoHashCodeNotEquals(firstRecord, secondRecord);
  }

  @Test
  public void recordsWithDifferentTimestampsShouldNotBeEqual() {
    TransactionRecord firstRecord = new TransactionRecordBuilder().timestamp(Instant.MIN).build();
    TransactionRecord secondRecord = new TransactionRecordBuilder().timestamp(Instant.MAX).build();
    assertNotEqualsAndAlsoHashCodeNotEquals(firstRecord, secondRecord);
  }

  @Test
  public void sameObjectsShouldBeEqual() {
    TransactionRecord record = new TransactionRecordBuilder().build();
    assertEquals(record, record);
  }

  @Test
  public void differentTypesShouldNotBeEqual() {
    assertNotEquals(new TransactionRecordBuilder().build(), new Object());
  }

  private static void assertEqualsAndAlsoHashCodeEquals(Object expected, Object actual) {
    assertEquals(expected, actual);
    assertEquals(expected.hashCode(), actual.hashCode());
  }

  private static void assertNotEqualsAndAlsoHashCodeNotEquals(Object expected, Object actual) {
    assertNotEquals(expected, actual);
    assertNotEquals(expected.hashCode(), actual.hashCode());
  }

}
