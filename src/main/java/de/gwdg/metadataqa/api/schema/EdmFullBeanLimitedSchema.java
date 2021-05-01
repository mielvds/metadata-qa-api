package de.gwdg.metadataqa.api.schema;

import de.gwdg.metadataqa.api.json.FieldGroup;
import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.model.Category;
import de.gwdg.metadataqa.api.rule.RuleChecker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Europeana Data Model (EDM) representation of the metadata schema interface.
 * This class represents what fields will be analyzed in different measurements.
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class EdmFullBeanLimitedSchema extends EdmSchema implements Serializable {

  private static final long serialVersionUID = 5248200128650498403L;
  private static final List<JsonBranch> PATHS = new ArrayList<>();
  private static final List<FieldGroup> FIELD_GROUPS = new ArrayList<>();
  private static final List<String> NO_LANGUAGE_FIELDS = new ArrayList<>();
  private static final Map<String, String> SOLR_FIELDS = new LinkedHashMap<>();
  private static Map<String, String> extractableFields = new LinkedHashMap<>();
  private static final List<String> EMPTY_STRINGS = new ArrayList<>();
  private static List<String> categories = null;
  private static List<RuleChecker> ruleCheckers;

  private static final String LONG_SUBJECT_PATH =
    "$.['proxies'][?(@['europeanaProxy'] == false)]['dcSubject']";
  private static final String TITLE_PATH =
    "$.['proxies'][?(@['europeanaProxy'] == false)]['dcTitle']";
  private static final String DESCRIPTION_PATH =
    "$.['proxies'][?(@['europeanaProxy'] == false)]['dcDescription']";

  static {
    addPath(new JsonBranch("edm:ProvidedCHO/@about",
      "$.['providedCHOs'][0]['about']")
      .setCategories(Category.MANDATORY));
    addPath(new JsonBranch("Proxy/dc:title",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcTitle']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
      Category.IDENTIFICATION, Category.MULTILINGUALITY));
    addPath(new JsonBranch("Proxy/dcterms:alternative",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsAlternative']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
      Category.IDENTIFICATION, Category.MULTILINGUALITY));
    addPath(new JsonBranch("Proxy/dc:description",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcDescription']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
      Category.CONTEXTUALIZATION, Category.IDENTIFICATION,
      Category.MULTILINGUALITY));
    addPath(new JsonBranch("Proxy/dc:creator",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcCreator']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
      Category.CONTEXTUALIZATION, Category.BROWSING));
    addPath(new JsonBranch("Proxy/dc:publisher",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcPublisher']")
      .setCategories(Category.SEARCHABILITY, Category.REUSABILITY));
    addPath(new JsonBranch("Proxy/dc:contributor",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcContributor']")
      .setCategories(Category.SEARCHABILITY));
    addPath(new JsonBranch("Proxy/dc:type",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcType']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.IDENTIFICATION, Category.BROWSING));
    addPath(new JsonBranch("Proxy/dc:identifier",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcIdentifier']")
      .setCategories(Category.IDENTIFICATION));
    addPath(new JsonBranch("Proxy/dc:language",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcLanguage']")
      .setCategories(Category.DESCRIPTIVENESS, Category.MULTILINGUALITY));
    addPath(new JsonBranch("Proxy/dc:coverage",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcCoverage']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new JsonBranch("Proxy/dcterms:temporal",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsTemporal']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new JsonBranch("Proxy/dcterms:spatial",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsSpatial']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new JsonBranch("Proxy/dc:subject",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcSubject']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
      Category.CONTEXTUALIZATION, Category.MULTILINGUALITY));
    addPath(new JsonBranch("Proxy/dc:date",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcDate']")
      .setCategories(Category.IDENTIFICATION, Category.BROWSING,
      Category.REUSABILITY));
    addPath(new JsonBranch("Proxy/dcterms:created",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsCreated']")
      .setCategories(Category.IDENTIFICATION, Category.REUSABILITY));
    addPath(new JsonBranch("Proxy/dcterms:issued",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsIssued']")
      .setCategories(Category.IDENTIFICATION, Category.REUSABILITY));
    addPath(new JsonBranch("Proxy/dcterms:extent",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsExtent']")
      .setCategories(Category.DESCRIPTIVENESS, Category.REUSABILITY));
    addPath(new JsonBranch("Proxy/dcterms:medium",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsMedium']")
      .setCategories(Category.DESCRIPTIVENESS, Category.REUSABILITY));
    addPath(new JsonBranch("Proxy/dcterms:provenance",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsProvenance']")
      .setCategories(Category.DESCRIPTIVENESS));
    addPath(new JsonBranch("Proxy/dcterms:hasPart",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsHasPart']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new JsonBranch("Proxy/dcterms:isPartOf",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dctermsIsPartOf']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new JsonBranch("Proxy/dc:format",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcFormat']")
      .setCategories(Category.DESCRIPTIVENESS, Category.REUSABILITY));
    addPath(new JsonBranch("Proxy/dc:source",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcSource']")
      .setCategories(Category.DESCRIPTIVENESS));
    addPath(new JsonBranch("Proxy/dc:rights",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcRights']")
      .setCategories(Category.REUSABILITY));
    addPath(new JsonBranch("Proxy/dc:relation",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['dcRelation']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new JsonBranch("Proxy/edm:isNextInSequence",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['edmIsNextInSequence']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new JsonBranch("Proxy/edm:type",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['edmType']")
      .setCategories(Category.SEARCHABILITY, Category.BROWSING));
    /*
    addPath(new JsonBranch("Proxy/edm:rights",
      "$.['proxies'][?(@['europeanaProxy'] == false)]['edm:rights']",
      Category.MANDATORY, Category.REUSABILITY));
    */
    addPath(new JsonBranch("Aggregation/edm:rights",
      "$.['aggregations'][0]['edmRights']")
      .setCategories(Category.MANDATORY, Category.REUSABILITY));
    addPath(new JsonBranch("Aggregation/edm:provider",
      "$.['aggregations'][0]['edmProvider']")
      .setCategories(Category.MANDATORY, Category.SEARCHABILITY,
      Category.IDENTIFICATION));
    addPath(new JsonBranch("Aggregation/edm:dataProvider",
      "$.['aggregations'][0]['edmDataProvider']")
      .setCategories(Category.MANDATORY, Category.SEARCHABILITY,
      Category.IDENTIFICATION));
    addPath(new JsonBranch("Aggregation/edm:isShownAt",
      "$.['aggregations'][0]['edmIsShownAt']")
      .setCategories(Category.BROWSING, Category.VIEWING));
    addPath(new JsonBranch("Aggregation/edm:isShownBy",
      "$.['aggregations'][0]['edmIsShownBy']")
      .setCategories(Category.BROWSING, Category.VIEWING,
      Category.REUSABILITY));
    addPath(new JsonBranch("Aggregation/edm:object",
      "$.['aggregations'][0]['edmObject']")
      .setCategories(Category.VIEWING, Category.REUSABILITY));
    addPath(new JsonBranch("Aggregation/edm:hasView",
      "$.['aggregations'][0]['hasView']")
      .setCategories(Category.BROWSING, Category.VIEWING));

    FIELD_GROUPS.add(
      new FieldGroup(
        Category.MANDATORY,
        "Proxy/dc:title", "Proxy/dc:description"));
    FIELD_GROUPS.add(
      new FieldGroup(
        Category.MANDATORY,
        "Proxy/dc:type", "Proxy/dc:subject", "Proxy/dc:coverage",
        "Proxy/dcterms:temporal", "Proxy/dcterms:spatial"));
    FIELD_GROUPS.add(
      new FieldGroup(
        Category.MANDATORY,
        "Aggregation/edm:isShownAt", "Aggregation/edm:isShownBy"));

    NO_LANGUAGE_FIELDS.addAll(Arrays.asList(
      "edm:ProvidedCHO/@about", "Proxy/edm:isNextInSequence",
      "Proxy/edm:type", "Aggregation/edm:isShownAt",
      "Aggregation/edm:isShownBy", "Aggregation/edm:object",
      "Aggregation/edm:hasView"));

    SOLR_FIELDS.put("dc:title", "dc_title_txt");
    SOLR_FIELDS.put("dcterms:alternative", "dcterms_alternative_txt");
    SOLR_FIELDS.put("dc:description", "dc_description_txt");

    extractableFields.put("recordId", "$.identifier");
    extractableFields.put("dataset", "$.sets[0]");
    extractableFields.put("dataProvider", "$.['aggregations'][0]['edmDataProvider'][0]");

    EMPTY_STRINGS.add("$.['proxies'][?(@['europeanaProxy'] == false)]['dcTitle']");
    EMPTY_STRINGS.add("$.['proxies'][?(@['europeanaProxy'] == false)]['dcDescription']");
    EMPTY_STRINGS.add("$.['proxies'][?(@['europeanaProxy'] == false)]['dcSubject']");
  }

  private static void addPath(JsonBranch branch) {
    PATHS.add(branch);
  }

  @Override
  public List<JsonBranch> getPaths() {
    return PATHS;
  }

  @Override
  public List<FieldGroup> getFieldGroups() {
    return FIELD_GROUPS;
  }

  @Override
  public List<String> getNoLanguageFields() {
    return NO_LANGUAGE_FIELDS;
  }

  @Override
  public Map<String, String> getSolrFields() {
    return SOLR_FIELDS;
  }

  @Override
  public Map<String, String> getExtractableFields() {
    return extractableFields;
  }

  @Override
  public void setExtractableFields(Map<String, String> extractableFields) {
    this.extractableFields = extractableFields;
  }

  @Override
  public void addExtractableField(String label, String jsonPath) {
    extractableFields.put(label, jsonPath);
  }

  @Override
  public List<String> getCategories() {
    if (categories == null) {
      categories = Category.extractCategories(PATHS, true);
    }
    return categories;
  }

  @Override
  public List<String> getEmptyStringPaths() {
    return EMPTY_STRINGS;
  }

  @Override
  public String getSubjectPath() {
    return LONG_SUBJECT_PATH;
  }

  @Override
  public String getTitlePath() {
    return TITLE_PATH;
  }

  @Override
  public String getDescriptionPath() {
    return DESCRIPTION_PATH;
  }

  @Override
  public Format getFormat() {
    return Format.JSON;
  }

  @Override
  public List<JsonBranch> getCollectionPaths() {
    return new ArrayList();
  }

  @Override
  public List<JsonBranch> getRootChildrenPaths() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public JsonBranch getPathByLabel(String label) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<RuleChecker> getRuleCheckers() {
    if (ruleCheckers == null) {
      ruleCheckers = SchemaUtils.getRuleCheckers(this);
    }
    return ruleCheckers;
  }
}
