package org.serviceinterface;

public interface IDataCipher {
	 
	byte[] encryptData(final String data) throws Exception;
	
	byte[] encryptData(final String keyArn, final String data) throws Exception;
	 
	String decryptData(final String keyArn, final byte[] data) throws Exception;
	
	String decryptData(final byte[] data) throws Exception;


}
