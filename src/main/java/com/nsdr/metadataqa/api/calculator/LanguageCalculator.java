package com.nsdr.metadataqa.api.calculator;

import com.nsdr.metadataqa.api.schema.EdmSchema;
import com.nsdr.metadataqa.api.json.JsonBranch;
import com.nsdr.metadataqa.api.abbreviation.DatasetManager;
import com.nsdr.metadataqa.api.abbreviation.DataProviderManager;
import com.nsdr.metadataqa.api.counter.Counters;
import com.nsdr.metadataqa.api.counter.BasicCounter;
import com.nsdr.metadataqa.api.interfaces.Calculator;
import com.nsdr.metadataqa.api.model.EdmFieldInstance;
import com.nsdr.metadataqa.api.model.JsonPathCache;
import com.jayway.jsonpath.InvalidJsonException;
import com.nsdr.metadataqa.api.model.XmlFieldInstance;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import com.nsdr.metadataqa.api.schema.Schema;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class LanguageCalculator implements Calculator, Serializable {

	private static final Logger LOGGER = Logger.getLogger(LanguageCalculator.class.getCanonicalName());

	private String inputFileName;

	private Counters counters;
	private Map<String, String> languageMap;

	private boolean verbose = false;
	private Schema branches;

	public LanguageCalculator() {
		// this.recordID = null;
	}

	public LanguageCalculator(String recordID) {
		// this.recordID = recordID;
	}

	public LanguageCalculator(Schema branches) {
		this.branches = branches;
	}

	@Override
	public void measure(JsonPathCache cache, Counters counters) throws InvalidJsonException {
		this.counters = counters;

		languageMap = new LinkedHashMap<>();
		for (JsonBranch jsonBranch : branches.getPaths()) {
			if (!branches.getNoLanguageFields().contains(jsonBranch.getLabel()))
				extractLanguageTags(jsonBranch, cache, languageMap);
		}
	}

	public String getResult() {
		String result = String.format("%s,%s,%s,%s",
			counters.getField("datasetCode"),
			counters.getField("dataProviderCode"),
			counters.getRecordId(),
			StringUtils.join(languageMap.values(), ",")
		);
		return result;
	}

	private void extractLanguageTags(JsonBranch jsonBranch, JsonPathCache cache,
			Map<String, String> languageMap) {
		List<EdmFieldInstance> values = cache.get(jsonBranch.getJsonPath());
		Map<String, BasicCounter> languages = new HashMap<>();
		if (values != null && !values.isEmpty()) {
			for (EdmFieldInstance field : values) {
				if (field.hasValue()) {
					if (field.hasLanguage()) {
						increase(languages, field.getLanguage());
					} else {
						increase(languages, "_0");
					}
				} else {
					increase(languages, "_2");
				}
			}
		} else {
			increase(languages, "_1");
		}
		languageMap.put(jsonBranch.getLabel(), extractLanguages(languages));
	}

	private void increase(Map<String, BasicCounter> languages, String key) {
		if (!languages.containsKey(key)) {
			languages.put(key, new BasicCounter(1));
		} else {
			languages.get(key).increaseTotal();
		}
	}

	private String extractLanguages(Map<String, BasicCounter> languages) {
		String result = "";
		for (String lang : languages.keySet()) {
			if (result.length() > 0)
				result += ";";
			result += lang + ":" + ((Double)languages.get(lang).getTotal()).intValue();
		}
		return result;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
}
