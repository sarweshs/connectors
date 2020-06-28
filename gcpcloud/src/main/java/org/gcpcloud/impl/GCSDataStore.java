package org.gcpcloud.impl;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

import org.serviceinterface.IDataStore;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class GCSDataStore implements IDataStore {

	private Storage storage;

	public GCSDataStore(){
		Credentials credentials;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(System.getenv("AUTH_FILE")));
			storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(System.getenv("GOOGLE_PROJECT")).build().getService();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void storeData(String bucketName, String key, Object obj) {

		// Bucket require globally unique names, so you'll probably need to change this
		Bucket bucket = getBucket(bucketName);

		// Save a simple string
		BlobId blobId = saveString(key, obj.toString(), bucket);

		// Get it by blob id this time
		String value = getString(blobId);
		
		System.out.println("Uploaded data:" + value);

		// log.info("Read data: {}", value);
		/*
		try {
			updateString(blobId, "Bye now!");
			// Get the string by blob name
			value = getString("my-first-blob");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	public Object retrieveData(String bucketName, String key) {
		// TODO Auto-generated method stub
		Bucket bucket = getBucket(bucketName);
		return getString(bucket , key);
	}

	// Check for bucket existence and create if needed.
	private Bucket getBucket(String bucketName) {
		Bucket bucket = storage.get(bucketName);
		if (bucket == null) {
			System.out.println("Creating new bucket.");
			bucket = storage.create(BucketInfo.of(bucketName));
		}
		return bucket;
	}

	// Save a string to a blob
	private BlobId saveString(String blobName, String value, Bucket bucket) {
		byte[] bytes = value.getBytes(UTF_8);
		Blob blob = bucket.create(blobName, bytes);
		return blob.getBlobId();
	}

	// get a blob by id
	private String getString(BlobId blobId) {
		Blob blob = storage.get(blobId);
		return new String(blob.getContent());
	}

	// get a blob by name
	private String getString(Bucket bucket, String name) {
		Page<Blob> blobs = bucket.list();
		for (Blob blob : blobs.getValues()) {
			if (name.equals(blob.getName())) {
				return new String(blob.getContent());
			}
		}
		return "Blob not found";
	}

	// Update a blob
	private void updateString(BlobId blobId, String newString) throws IOException {
		Blob blob = storage.get(blobId);
		if (blob != null) {
			WritableByteChannel channel = blob.writer();
			channel.write(ByteBuffer.wrap(newString.getBytes(UTF_8)));
			channel.close();
		}
	}

	public void createFolder(String bucketName, String folderName) {
		// TODO Auto-generated method stub
		Bucket bucket = storage.create(BucketInfo.of(bucketName + "-" + folderName));
		System.out.println("Bucket created:" + bucket.getName());
	}

	@Override
	public void storeData(String key, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object retrieveData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
