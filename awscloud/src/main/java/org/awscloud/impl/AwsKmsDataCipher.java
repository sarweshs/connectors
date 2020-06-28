package org.awscloud.impl;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import org.serviceinterface.IDataCipher;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;

public class AwsKmsDataCipher implements IDataCipher {
	final AwsCrypto crypto = new AwsCrypto();
	 // For more information see:
    // blogs.aws.amazon.com/security/post/Tx2LZ6WBJJANTNW/How-to-Protect-the-Integrity-of-Your-Encrypted-Data-by-Using-AWS-Key-Management
    final Map<String, String> encryptionContext = Collections.singletonMap("ExampleContextKey", "ExampleContextValue");
    private String keyArn;
    
    public AwsKmsDataCipher(String keyArn)
    {
    	this.keyArn = keyArn;
    }

    public AwsKmsDataCipher()
    {
    	
    }
    
    public byte[] encryptData( String data) throws Exception {

        final KmsMasterKeyProvider masterKeyProvider = KmsMasterKeyProvider.builder().withKeysForEncryption(keyArn).build();

        // Most encrypted data should have an associated encryption context
        // to protect integrity. This sample uses placeholder values.

        //
        final CryptoResult<byte[], KmsMasterKey> encryptResult = crypto.encryptData(masterKeyProvider, data.getBytes(), encryptionContext);
        final byte[] ciphertext = encryptResult.getResult();
        return Base64.getEncoder().encode(ciphertext);

	}
    
	public byte[] encryptData(String keyArn, String data) throws Exception {

        final KmsMasterKeyProvider masterKeyProvider = KmsMasterKeyProvider.builder().withKeysForEncryption(keyArn).build();

        // Most encrypted data should have an associated encryption context
        // to protect integrity. This sample uses placeholder values.

        //
        final CryptoResult<byte[], KmsMasterKey> encryptResult = crypto.encryptData(masterKeyProvider, data.getBytes(), encryptionContext);
        final byte[] ciphertext = encryptResult.getResult();
        return Base64.getEncoder().encode(ciphertext);

	}

	public String decryptData(String keyArn, byte[] data) throws Exception {
		// TODO Auto-generated method stub
		final KmsMasterKeyProvider masterKeyProvider = KmsMasterKeyProvider.builder().withKeysForEncryption(keyArn).build();
		
		final CryptoResult<byte[], KmsMasterKey> decryptResult = crypto.decryptData(masterKeyProvider, Base64.getDecoder().decode(data));

        // 6. Before verifying the plaintext, verify that the customer master key that
        // was used in the encryption operation was the one supplied to the master key provider.
        if (!decryptResult.getMasterKeyIds().get(0).equals(keyArn)) {
            throw new IllegalStateException("Wrong key ID!");
        }

        // 7. Also, verify that the encryption context in the result contains the
        // encryption context supplied to the encryptData method. Because the
        // SDK can add values to the encryption context, don't require that
        // the entire context matches.
        if (!encryptionContext.entrySet().stream()
                .allMatch(e -> e.getValue().equals(decryptResult.getEncryptionContext().get(e.getKey())))) {
            throw new IllegalStateException("Wrong Encryption Context!");
        }
		return new String(decryptResult.getResult());
	}
	
	public String decryptData(byte[] data) throws Exception {
		// TODO Auto-generated method stub
		final KmsMasterKeyProvider masterKeyProvider = KmsMasterKeyProvider.builder().withKeysForEncryption(this.keyArn).build();
		
		final CryptoResult<byte[], KmsMasterKey> decryptResult = crypto.decryptData(masterKeyProvider, Base64.getDecoder().decode(data));

        // 6. Before verifying the plaintext, verify that the customer master key that
        // was used in the encryption operation was the one supplied to the master key provider.
        if (!decryptResult.getMasterKeyIds().get(0).equals(keyArn)) {
            throw new IllegalStateException("Wrong key ID!");
        }

        // 7. Also, verify that the encryption context in the result contains the
        // encryption context supplied to the encryptData method. Because the
        // SDK can add values to the encryption context, don't require that
        // the entire context matches.
        if (!encryptionContext.entrySet().stream()
                .allMatch(e -> e.getValue().equals(decryptResult.getEncryptionContext().get(e.getKey())))) {
            throw new IllegalStateException("Wrong Encryption Context!");
        }
		return new String(decryptResult.getResult());
	}

	public String getKeyArn() {
		return keyArn;
	}

}
