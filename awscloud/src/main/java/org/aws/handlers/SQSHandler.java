package org.aws.handlers;

import java.util.concurrent.CompletableFuture;

import org.awscloud.impl.AwsKmsDataCipher;
import org.awscloud.impl.S3DataStore;
import org.core.api.reader.ApiDataReader;
import org.serviceinterface.IDataCipher;
import org.serviceinterface.IDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.model.GetAccountSettingsRequest;
import software.amazon.awssdk.services.lambda.model.GetAccountSettingsResponse;

public class SQSHandler implements RequestHandler<SQSEvent, String> {

	private static final Logger logger = LoggerFactory.getLogger(SQSHandler.class);
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final LambdaAsyncClient lambdaClient = LambdaAsyncClient.create();

	public SQSHandler() {
		CompletableFuture<GetAccountSettingsResponse> accountSettings = lambdaClient
				.getAccountSettings(GetAccountSettingsRequest.builder().build());
		try {
			GetAccountSettingsResponse settings = accountSettings.get();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@Override
	public String handleRequest(SQSEvent event, Context context) {
		String response = new String();
		// call Lambda API
		logger.info("Getting account settings");
		CompletableFuture<GetAccountSettingsResponse> accountSettings = lambdaClient
				.getAccountSettings(GetAccountSettingsRequest.builder().build());
		// log execution details
		logger.info("ENVIRONMENT VARIABLES: {}", gson.toJson(System.getenv()));
		logger.info("CONTEXT: {}", gson.toJson(context));
		logger.info("EVENT: {}", gson.toJson(event));
		System.out.println("SQS source:");
		String s3Store = System.getenv("test_intermediate_store");
		IDataStore dataStore = new S3DataStore(s3Store);

		String keyArn = System.getenv("data_encryption_key");// "arn:aws:kms:us-east-1:254359228911:key/3408b345-87d2-4a66-b988-1225a415a419"
		IDataCipher dataCipher = new AwsKmsDataCipher(keyArn);
		ApiDataReader reader = new ApiDataReader(dataStore, dataCipher);
		for (SQSMessage msg : event.getRecords()) {
			System.out.println("Processing : " + new String(msg.getBody()));
			// System.out.println(event.);

		}

		// process Lambda API response
		try {
			GetAccountSettingsResponse settings = accountSettings.get();
			response = gson.toJson(settings.accountUsage());
			logger.info("Account usage: {}", response);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return "success";
	}
}
