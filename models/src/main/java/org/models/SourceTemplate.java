package org.models;

import java.util.Map;

public class SourceTemplate {
	
	private String apiUrl;
	
	private String apiKeyHint;
	
	private String primaryKeyName;
	
	private RuleModel rule;
	
	private NextIterationModel next;
	
	private String responseQueue;
	
	private String objectKeyName;
	
	private String resourceIdentifier;
	
	private String listAttributeNameInResponse;
	
	public String getApiUrl() {
		return apiUrl;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	private Map<String, String> queryParams;
	
	public SourceTemplate(String resourceIdentifier, String listAttributeNameInResponse, String primaryKeyName, String apiUrl, Map<String, String> queryParams, String queueName)
	{
		this.resourceIdentifier = resourceIdentifier;
		this.listAttributeNameInResponse = listAttributeNameInResponse;
		this.primaryKeyName = primaryKeyName;
		this.apiUrl = apiUrl;
		this.queryParams = queryParams;
	}

	protected SourceTemplate()
	{
		
	}
	
	public RuleModel getRule() {
		return rule;
	}

	public void setRule(RuleModel rule) {
		this.rule = rule;
	}

	public String getApiKeyHint() {
		return apiKeyHint;
	}

	public NextIterationModel getNext() {
		return next;
	}

	public void setNext(NextIterationModel next) {
		this.next = next;
	}

	public String getResponseQueue() {
		return responseQueue;
	}

	public void setResponseQueue(String responseQueue) {
		this.responseQueue = responseQueue;
	}

	public String getObjectKeyName() {
		return objectKeyName;
	}

	public void setObjectKeyName(String objectKeyName) {
		this.objectKeyName = objectKeyName;
	}

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public String getListAttributeNameInResponse() {
		return listAttributeNameInResponse;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}


}
