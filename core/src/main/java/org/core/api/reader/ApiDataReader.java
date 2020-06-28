package org.core.api.reader;

import java.util.Map;

import org.core.util.RestApiUtil;
import org.models.ApiSourceDataModel;
import org.serviceinterface.IDataCipher;
import org.serviceinterface.IDataStore;

public class ApiDataReader {
	
	private IDataStore dataStore;
	private IDataCipher dataCipher;
	private RestApiUtil apiUtil = new RestApiUtil();
	
	public ApiDataReader(IDataStore dataStore, IDataCipher dataCipher)
	{
		this.dataCipher = dataCipher;
		this.dataStore = dataStore;
	}
	
	public void readAndProcessData(ApiSourceDataModel model, Map<String, String> queryParams) throws Exception
	{
		String response = apiUtil.callGetApi(model.getApiUrl(), queryParams);
		
		byte[] encryptedString = dataCipher.encryptData( response);
		
		dataStore.storeData(model.getKey(), new String(encryptedString));
		System.out.println("Stored in S3");
	}
}
