package de.gwdg.metadataqa.api.calculator;

import de.gwdg.metadataqa.api.schema.EdmFullBeanSchema;
import de.gwdg.metadataqa.api.schema.EdmOaiPmhJsonSchema;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class TfIdfCalculatorTest {
  
  public TfIdfCalculatorTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  @Test
  public void testGetHeaders() {
    TfIdfCalculator calculator = new TfIdfCalculator(new EdmOaiPmhJsonSchema());
    List<String> expected = Arrays.asList(
      "Proxy/dc:title:sum", "Proxy/dc:title:avg",
      "Proxy/dcterms:alternative:sum", "Proxy/dcterms:alternative:avg",
      "Proxy/dc:description:sum", "Proxy/dc:description:avg"
    );
    assertEquals(6, calculator.getHeader().size());
    assertEquals(expected, calculator.getHeader());

    calculator = new TfIdfCalculator(new EdmFullBeanSchema());
    assertEquals(6, calculator.getHeader().size());
    assertEquals(expected, calculator.getHeader());
  }

  @Test
  public void getCalculatorName() throws Exception {
    TfIdfCalculator calculator = new TfIdfCalculator(new EdmOaiPmhJsonSchema());
    assertEquals("uniqueness", calculator.getCalculatorName());
  }

  @Test
  public void measure() throws Exception {
  }

  @Test
  public void getTermsCollection() throws Exception {
  }

  @Test
  public void enableTermCollection() throws Exception {
  }

  @Test
  public void getResultMap() throws Exception {
  }

  @Test
  public void getLabelledResultMap() throws Exception {
  }

  @Test
  public void getCsv() throws Exception {
  }

  @Test
  public void getHeader() throws Exception {
  }

  @Test
  public void getSolrHost() throws Exception {
  }

  @Test
  public void setSolrHost() throws Exception {
  }

  @Test
  public void getSolrPort() throws Exception {
  }

  @Test
  public void setSolrPort() throws Exception {
  }

  @Test
  public void getSolrPath() throws Exception {
  }

  @Test
  public void setSolrPath() throws Exception {
  }

  @Test
  public void setSolr() throws Exception {
  }

  @Test
  public void getSolrSearchPath() throws Exception {
  }


}
