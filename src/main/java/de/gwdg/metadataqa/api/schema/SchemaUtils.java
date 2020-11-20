package de.gwdg.metadataqa.api.schema;

import de.gwdg.metadataqa.api.configuration.Rule;
import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.rule.DisjointChecker;
import de.gwdg.metadataqa.api.rule.EnumerationChecker;
import de.gwdg.metadataqa.api.rule.EqualityChecker;
import de.gwdg.metadataqa.api.rule.HasValueChecker;
import de.gwdg.metadataqa.api.rule.MaxCountChecker;
import de.gwdg.metadataqa.api.rule.MaxLengthChecker;
import de.gwdg.metadataqa.api.rule.MinCountChecker;
import de.gwdg.metadataqa.api.rule.MinLengthChecker;
import de.gwdg.metadataqa.api.rule.NumericValueChecker;
import de.gwdg.metadataqa.api.rule.PatternChecker;
import de.gwdg.metadataqa.api.rule.RuleChecker;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SchemaUtils {

  private static final Logger LOGGER = Logger.getLogger(SchemaUtils.class.getCanonicalName());

  /**
   * @param schema
   * @return
   */
  public static List<RuleChecker> getRuleCheckers(Schema schema) {
    setSchemaForFields(schema);
    List<RuleChecker> ruleCheckers = new ArrayList<>();
    for (JsonBranch branch : schema.getPaths()) {
      if (branch.getRules() != null) {
        List<Rule> rules = branch.getRules();
        for (Rule rule : rules) {
          if (StringUtils.isNotBlank(rule.getPattern()))
            ruleCheckers.add(new PatternChecker(branch, rule.getPattern()));

          if (StringUtils.isNotBlank(rule.getEquals()))
            pair(schema, ruleCheckers, branch, rule.getEquals(), "equals");

          if (StringUtils.isNotBlank(rule.getDisjoint()))
            pair(schema, ruleCheckers, branch, rule.getDisjoint(), "disjoint");

          if (rule.getIn() != null && !rule.getIn().isEmpty())
            ruleCheckers.add(new EnumerationChecker(branch, rule.getIn()));

          if (rule.getMinCount() != null)
            ruleCheckers.add(new MinCountChecker(branch, rule.getMinCount()));

          if (rule.getMaxCount() != null)
            ruleCheckers.add(new MaxCountChecker(branch, rule.getMaxCount()));

          if (rule.getMinLength() != null)
            ruleCheckers.add(new MinLengthChecker(branch, rule.getMinLength()));

          if (rule.getMaxLength() != null)
            ruleCheckers.add(new MaxLengthChecker(branch, rule.getMaxLength()));

          if (StringUtils.isNotBlank(rule.getHasValue()))
            ruleCheckers.add(new HasValueChecker(branch, rule.getHasValue()));

          if (rule.getMinInclusive() != null)
            ruleCheckers.add(new NumericValueChecker(branch, rule.getMinInclusive(),
              NumericValueChecker.TYPE.MinInclusive));

          if (rule.getMaxInclusive() != null)
            ruleCheckers.add(new NumericValueChecker(branch, rule.getMinInclusive(),
              NumericValueChecker.TYPE.MaxInclusive));

          if (rule.getMinExclusive() != null)
            ruleCheckers.add(new NumericValueChecker(branch, rule.getMinInclusive(),
              NumericValueChecker.TYPE.MinExclusive));

          if (rule.getMaxExclusive() != null)
            ruleCheckers.add(new NumericValueChecker(branch, rule.getMinInclusive(),
              NumericValueChecker.TYPE.MaxExclusive));
        }

        //  private Integer lessThan;
        //  private Integer lessThanOrEquals;
      }
    }
    return ruleCheckers;
  }

  private static void pair(Schema schema,
                           List<RuleChecker> ruleCheckers,
                           JsonBranch branch,
                           String fieldReference,
                           String type) {
    JsonBranch field2 = schema.getPathByLabel(fieldReference);
    if (field2 != null) {
      RuleChecker ruleChecker = null;
      switch (type) {
        case "equals":   ruleChecker = new EqualityChecker(branch, field2); break;
        case "disjoint": ruleChecker = new DisjointChecker(branch, field2); break;
      }
      if (ruleChecker != null)
        ruleCheckers.add(ruleChecker);
    } else
      LOGGER.warning(
        String.format("invalid field reference in %s: %s", type, fieldReference));
  }

  public static void setSchemaForFields(Schema schema) {
    for (JsonBranch branch : schema.getPaths())
      branch.setSchema(schema);
  }
}
