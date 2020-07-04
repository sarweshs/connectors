package org.dummycloud.impl;

import org.serviceinterface.IDataCipher;

public class DummyDataCipher implements IDataCipher {

	@Override
	public byte[] encryptData(String data) throws Exception {
		// TODO Auto-generated method stub
		return data.getBytes();
	}

	@Override
	public byte[] encryptData(String keyArn, String data) throws Exception {
		// TODO Auto-generated method stub
		return data.getBytes();
	}

	@Override
	public String decryptData(String keyArn, byte[] data) throws Exception {
		// TODO Auto-generated method stub
		return new String (data);
	}

	@Override
	public String decryptData(byte[] data) throws Exception {
		// TODO Auto-generated method stub
		return new String (data);
	}

}
