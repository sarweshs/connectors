package org.core.api.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.core.util.RestApiUtil;
import org.models.SourceTemplate;
import org.serviceinterface.IBucketDataStore;
import org.serviceinterface.IDataCipher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiDataReader {

	private IBucketDataStore dataStore;
	private IDataCipher dataCipher;
	private RestApiUtil apiUtil = new RestApiUtil();
	private List<Map<String, Object>> responseList = new ArrayList<>();

	public ApiDataReader(IBucketDataStore dataStore, IDataCipher dataCipher) {
		this.dataCipher = dataCipher;
		this.dataStore = dataStore;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> readAndProcessData(SourceTemplate model, Map<String, String> queryParams) {
		int objectKeyNameCounter = 1;

		String response = apiUtil.callGetApi(model.getApiUrl(), queryParams);

		try {
			encryptAndStore(objectKeyNameCounter + "_" + model.getResourceIdentifier(), response);

			if (model.getRule() != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> responseObj = objectMapper.readValue(response.getBytes(), Map.class);

				populateResponse(responseObj, model.getListAttributeNameInResponse(), model.getPrimaryKeyName());

				Map<String, Object> ruleInput = new HashMap<>();
				ruleInput.put(model.getRule().getFirstPage(), responseObj.get(model.getRule().getFirstPage()));
				ruleInput.put(model.getRule().getLastPage(), responseObj.get(model.getRule().getLastPage()));
				List<String> nextApis = model.getRule().getNextApis(ruleInput, model.getApiUrl());

				System.out.println(nextApis);
				if (model.getRule().getSelfCall() == Boolean.TRUE) {
					for (String api : nextApis) {
						objectKeyNameCounter = objectKeyNameCounter + 1;
						response = apiUtil.callGetApi(api, queryParams);
						encryptAndStore(objectKeyNameCounter + "_" + model.getResourceIdentifier(), response);
						responseObj = objectMapper.readValue(response.getBytes(), Map.class);

						populateResponse(responseObj, model.getListAttributeNameInResponse(),
								model.getPrimaryKeyName());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseList;
	}

	@SuppressWarnings("unchecked")
	private void populateResponse(Map<String, Object> responseObj, String listAttributeNameInResponse,
			String primaryKeyName) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> data = (List<Map<String, Object>>) responseObj.get(listAttributeNameInResponse);
		for (Map<String, Object> row : data) {
			Map<String, Object> primaryKeyData = new HashMap<>();
			primaryKeyData.put(primaryKeyName, row.get(primaryKeyName));
			responseList.add(primaryKeyData);
		}
	}

	private void encryptAndStore(String objectKeyName, String response) throws Exception {
		byte[] encryptedString;
		try {
			encryptedString = dataCipher.encryptData(response);
			dataStore.storeData(objectKeyName, new String(encryptedString));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}
}
