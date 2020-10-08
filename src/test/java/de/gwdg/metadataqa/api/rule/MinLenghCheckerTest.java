package de.gwdg.metadataqa.api.rule;

import de.gwdg.metadataqa.api.counter.FieldCounter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinLenghCheckerTest extends CheckerTestBase {

  @Test
  public void prefix() {
    assertEquals("minLength", MinLengthChecker.prefix);
  }

  @Test
  public void success() {
    MinLengthChecker checker = new MinLengthChecker(schema.getPathByLabel("name"), 1);

    FieldCounter fieldCounter = new FieldCounter<>();
    checker.update(cache, fieldCounter);

    assertEquals(1, fieldCounter.size());
    assertEquals("minLength:name", checker.getHeader());
    assertEquals(RuleCheckingOutput.PASSED, fieldCounter.get(checker.getHeader()));
  }

  @Test
  public void failure() {
    MinLengthChecker checker = new MinLengthChecker(schema.getPathByLabel("name"), 2);

    FieldCounter fieldCounter = new FieldCounter<>();
    checker.update(cache, fieldCounter);

    assertEquals(1, fieldCounter.size());
    assertEquals("minLength:name", checker.getHeader());
    assertEquals(RuleCheckingOutput.FAILED, fieldCounter.get(checker.getHeader()));
  }
}