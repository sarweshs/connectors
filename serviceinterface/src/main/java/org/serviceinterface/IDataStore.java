package org.serviceinterface;

public interface IDataStore {
	
	public void storeData(String bucketName, String key, Object obj);
	
	public void storeData(String key, Object obj);

	
	public Object retrieveData(String bucketName, String key);
	
	public Object retrieveData(String key);
	
	
	public void createFolder(String bucketName, String folderName);

}
