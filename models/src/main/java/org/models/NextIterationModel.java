package org.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NextIterationModel {
	private List<Map<String, String>> responseKeys = new ArrayList<>();
	
	private String responseFor;
	
	public List<Map<String, String>> getResponseKeys() {
		return responseKeys;
	}

	public String getResponseFor() {
		return responseFor;
	}

	public void setResponseFor(String responseFor) {
		this.responseFor = responseFor;
	}

}
