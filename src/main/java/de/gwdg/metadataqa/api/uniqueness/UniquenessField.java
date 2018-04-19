package de.gwdg.metadataqa.api.uniqueness;

public class UniquenessField {
	String label;
	String solrField;
	String jsonPath;
	int total;

	public UniquenessField(String label) {
		this.label = label;
	}

	public String getSolrField() {
		return solrField;
	}

	public void setSolrField(String solrField) {
		this.solrField = solrField;
	}

	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}