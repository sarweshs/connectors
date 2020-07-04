package org.dummycloud.impl;

import org.serviceinterface.IBucketDataStore;

public class DummyDataStore implements IBucketDataStore {

	@Override
	public void storeData(String bucketName, String key, Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeData(String key, Object obj) {
		// TODO Auto-generated method stub
		System.out.println("Storing object with key name:" + key);
		System.out.println("Object:" + obj);
	}

	@Override
	public Object retrieveData(String bucketName, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object retrieveData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createFolder(String bucketName, String folderName) {
		// TODO Auto-generated method stub

	}

}
