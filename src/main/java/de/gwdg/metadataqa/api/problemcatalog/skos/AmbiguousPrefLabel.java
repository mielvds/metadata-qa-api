package de.gwdg.metadataqa.api.problemcatalog.skos;

import de.gwdg.metadataqa.api.counter.FieldCounter;
import de.gwdg.metadataqa.api.json.JsonBranch;
import de.gwdg.metadataqa.api.model.EdmFieldInstance;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.problemcatalog.ProblemCatalog;
import de.gwdg.metadataqa.api.problemcatalog.ProblemDetector;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.util.Converter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class AmbiguousPrefLabel extends ProblemDetector implements Serializable {

	private static final Logger logger = Logger.getLogger(AmbiguousPrefLabel.class.getCanonicalName());

	private final String NAME = "AmbiguousPrefLabel";
	private static final List<String> labels = Arrays.asList(
		"Agent/skos:prefLabel", 
		"Concept/skos:prefLabel", 
		"Place/skos:prefLabel",
		"Timespan/skos:prefLabel"
	);

	public AmbiguousPrefLabel(ProblemCatalog problemCatalog) {
		this.problemCatalog = problemCatalog;
		this.problemCatalog.addObserver(this);
		this.schema = problemCatalog.getSchema();
	}

	@Override
	public void update(JsonPathCache cache, FieldCounter<Double> results) {
		int value = 0;
		for (String label : labels) {
			JsonBranch branch = ((Schema)schema).getPathByLabel(label);
			String parentPath = branch.getParent().getJsonPath();
			Object rawEntityFragment = cache.getFragment(parentPath);
			if (rawEntityFragment != null) {
				List<Object> entities = Converter.jsonObjectToList(rawEntityFragment);
				for (int i = 0; i < entities.size(); i++) {
					value += countPerEntity(i, branch, cache);
				}
			}
		}
		results.put(NAME, (double)value);
	}

	private int countPerEntity(int entityCounter, JsonBranch branch, JsonPathCache cache) {
		List<EdmFieldInstance> subjects = cache.get(branch.getAbsoluteJsonPath(entityCounter));
		Map<String, Integer> labelCounter = countLabelsPerFields(subjects);
		return countAmbiguousPrefLabels(labelCounter);
	}

	private Map<String, Integer> countLabelsPerFields(List<EdmFieldInstance> subjects) {
		Map<String, Integer> labelCounter = new HashMap<>();
		for (EdmFieldInstance subject : subjects) {
			if (subject.getLanguage() != null) {
				int count = labelCounter.containsKey(subject.getLanguage()) ?
						  labelCounter.get(subject.getLanguage()) : 0;
				labelCounter.put(subject.getLanguage(), ++count);
			}
		}
		return labelCounter;
	}

	private int countAmbiguousPrefLabels(Map<String, Integer> labelCounter) {
		int value = 0;

		for (Map.Entry<String,Integer> entry : labelCounter.entrySet())
			if (entry.getValue() > 1)
				value++;

		return value;
	}

	@Override
	public String getHeader() {
		return NAME;
	}

}
