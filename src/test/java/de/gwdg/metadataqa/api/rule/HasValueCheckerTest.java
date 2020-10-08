package de.gwdg.metadataqa.api.rule;

import de.gwdg.metadataqa.api.counter.FieldCounter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HasValueCheckerTest extends CheckerTestBase {

  @Test
  public void prefix() {
    assertEquals("hasValue", HasValueChecker.prefix);
  }

  @Test
  public void success() {
    HasValueChecker checker = new HasValueChecker(schema.getPathByLabel("name"), "a");

    FieldCounter fieldCounter = new FieldCounter<>();
    checker.update(cache, fieldCounter);

    assertEquals(1, fieldCounter.size());
    assertEquals("hasValue:name", checker.getHeader());
    assertEquals(RuleCheckingOutput.PASSED, fieldCounter.get("hasValue:name"));
  }

  @Test
  public void failure() {
    HasValueChecker checker = new HasValueChecker(schema.getPathByLabel("name"), "b");

    FieldCounter fieldCounter = new FieldCounter<>();
    checker.update(cache, fieldCounter);

    assertEquals(1, fieldCounter.size());
    assertEquals("hasValue:name", checker.getHeader());
    assertEquals(RuleCheckingOutput.FAILED, fieldCounter.get("hasValue:name"));
  }
}
