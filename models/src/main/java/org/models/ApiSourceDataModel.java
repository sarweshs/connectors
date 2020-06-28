package org.models;

import java.util.Map;
import java.util.UUID;

public class ApiSourceDataModel {
	
	private String apiUrl;
	
	private String key;
	
	private String resourceName;
	
	private String queueName;
	
	public String getApiUrl() {
		return apiUrl;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	private Map<String, String> queryParams;
	
	public ApiSourceDataModel(String resourceName, String apiUrl, Map<String, String> queryParams, String queueName)
	{
		this.resourceName = resourceName;
		this.apiUrl = apiUrl;
		this.queryParams = queryParams;
		this.queueName = queueName;
	}

	public String getKey() {
		if(key == null)
		{
			key = resourceName +"_" + UUID.randomUUID();
		}
		return key;
	}

	public String getQueueName() {
		return queueName;
	}
	
	

}
