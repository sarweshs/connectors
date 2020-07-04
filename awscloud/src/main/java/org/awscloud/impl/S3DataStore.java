package org.awscloud.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.serviceinterface.IBucketDataStore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3DataStore implements IBucketDataStore {
	AmazonS3 s3Client = null;
	
	private String bucketName;

	public S3DataStore() {
		s3Client = AmazonS3ClientBuilder.standard().withRegion(System.getenv("AWS_REGION")).build();
	}
	
	public S3DataStore(String bucketName) {
		s3Client = AmazonS3ClientBuilder.standard().withRegion(System.getenv("AWS_REGION")).build();
		this.bucketName = bucketName;
	}

	public void storeData(String bucketName, String key, Object obj) {
		try {
			// This code expects that you have AWS credentials set up per:

			s3Client.putObject(bucketName, key, obj.toString());

			// Upload a file as a new object with ContentType and title specified.
			/*
			 * PutObjectRequest request = new PutObjectRequest(bucketName, key, new
			 * File(fileName)); ObjectMetadata metadata = new ObjectMetadata();
			 * metadata.setContentType("plain/text"); metadata.addUserMetadata("title",
			 * "someTitle"); request.setMetadata(metadata); s3Client.putObject(request);
			 */
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}

	}
	
	public void storeData(String key, Object obj) {
		try {
			// This code expects that you have AWS credentials set up per:

			s3Client.putObject(bucketName, key, obj.toString());

			// Upload a file as a new object with ContentType and title specified.
			/*
			 * PutObjectRequest request = new PutObjectRequest(bucketName, key, new
			 * File(fileName)); ObjectMetadata metadata = new ObjectMetadata();
			 * metadata.setContentType("plain/text"); metadata.addUserMetadata("title",
			 * "someTitle"); request.setMetadata(metadata); s3Client.putObject(request);
			 */
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}

	}

	public Object retrieveData(String bucketName, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createFolder(String bucketName, String folderName) {
		// TODO Auto-generated method stub
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + folderName, emptyContent,
				metadata);

		// send request to S3 to create folder
		s3Client.putObject(putObjectRequest);

	}

	@Override
	public Object retrieveData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
