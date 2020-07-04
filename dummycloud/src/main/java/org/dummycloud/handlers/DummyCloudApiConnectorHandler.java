package org.dummycloud.handlers;

import java.util.List;
import java.util.Map;

import org.core.api.reader.ApiDataReader;
import org.dummycloud.impl.DummyDataCipher;
import org.dummycloud.impl.DummyDataStore;
import org.models.SourceTemplate;
import org.serviceinterface.IBucketDataStore;
import org.serviceinterface.IDataCipher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DummyCloudApiConnectorHandler {

	public static void main(String[] args) throws Exception {
		String jsonString = "{\"apiUrl\":\"https://reqres.in/api/users?page=1\",\"apiKeyHint\":null,\"primaryKeyName\":\"id\",\"rule\":{\"firstPage\":\"page\",\"lastPage\":\"total_pages\",\"replaceQueryParamter\":\"page\",\"increment\":1,\"selfCall\":true},\"next\":{\"responseKeys\":[],\"responseFor\":\"users\"},\"responseQueue\":null,\"objectKeyName\":null,\"resourceIdentifier\":\"users\",\"listAttributeNameInResponse\":\"data\",\"queryParams\":null}";
		IBucketDataStore dataStore = new DummyDataStore();

		IDataCipher dataCipher = new DummyDataCipher();
		ApiDataReader reader = new ApiDataReader(dataStore, dataCipher);
		
		ObjectMapper objectMapper = new ObjectMapper();
    	SourceTemplate templateFromMessage = objectMapper.readValue(jsonString.getBytes(), SourceTemplate.class);
    	
    	List<Map<String, Object>>response  = reader.readAndProcessData(templateFromMessage, null);
    	System.out.println(response);

	}
}
