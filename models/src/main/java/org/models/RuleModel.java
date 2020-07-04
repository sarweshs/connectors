package org.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleModel {
	
	private String firstPage;
	
	private String lastPage;
	
	private String replaceQueryParamter;
	
	private int increment;
	
	private Boolean selfCall = true;

	public String getReplaceQueryParamter() {
		return replaceQueryParamter;
	}

	public void setReplaceQueryParamter(String replaceQueryParamter) {
		this.replaceQueryParamter = replaceQueryParamter;
	}
	
	public List<String> getNextApis(Map<String, Object> response, String api)
	{
		Object firstIndex = response.get(firstPage);
		Object secondIndex = response.get(lastPage);
		
		Integer firstIndexIntVal = (Integer)firstIndex;
		Integer lastIndexIntVal = (Integer)secondIndex;

		List<String> nextApis = new ArrayList<>();
		for(;lastIndexIntVal - firstIndexIntVal >0;)
		{
			int firstIndexToReplace = api.indexOf(replaceQueryParamter);
			int secondIndexToReplace = api.indexOf("&",firstIndexToReplace);
			
			String substringToReplace = null;
			if(secondIndexToReplace == -1)
			{
				substringToReplace = api.substring(firstIndexToReplace);
			}
			else
			{
				substringToReplace = api.substring(firstIndexToReplace, secondIndexToReplace);
			}

			firstIndexIntVal = firstIndexIntVal+ increment;
			
			String newApi = api.replace(substringToReplace, replaceQueryParamter + "=" + firstIndexIntVal);
			nextApis.add(newApi);
		}
		
		return nextApis;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public Boolean getSelfCall() {
		return selfCall;
	}

	public void setSelfCall(Boolean selfCall) {
		this.selfCall = selfCall;
	}

}
