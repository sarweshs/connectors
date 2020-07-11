package org.gcprestapis;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.gcpcloud.impl.GCSDataStore;
import org.serviceinterface.IBucketDataStore;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class WebhookApi implements HttpFunction {
	private static final Logger logger = Logger.getLogger(HelloWorld.class.getName());

	private static final Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		// Check URL parameters for "name" field
		// "world" is the default value
		//String name = request.getFirstQueryParameter("name").orElse("world");

		// Parse JSON request and check for "name" field
		String res = String.format("Failed to upload events.");
		try {
			// JsonElement requestParsed = gson.fromJson(request.getReader(),
			// JsonElement.class);
			String extractPostRequestBody = extractPostRequestBody(request);
			//System.out.println("Extracted request body :" + extractPostRequestBody);
			Map<String, Object>[] requestParsed = gson.fromJson(extractPostRequestBody, Map[].class);
			for (Map<String, Object> requestJson : requestParsed) {
				System.out.println("Request json:" + requestJson);
				if (requestJson.get("portalId") != null ) {
					String portalId = requestJson.get("portalId").toString();
					System.out.println("PortalID:" + portalId);
					IBucketDataStore store = new GCSDataStore();
					store.storeData("test-intermediate-store", "portalids/" + portalId + "/" + UUID.randomUUID(), requestJson);
				}
			}
			res = "Uploaded all events in GCS";
		} catch (JsonParseException e) {
			logger.severe("Error parsing JSON: " + e.getMessage());
		}
		

		OutputStream os = response.getOutputStream();
		os.write(res.getBytes());
		os.flush();
		/// var writer = new PrintWriter(response.getWriter());
		// writer.printf("Hello %s!", name);
		
	}

	private String extractPostRequestBody(HttpRequest request) throws IOException {

		String requestObject = IOUtils.toString(request.getInputStream(), "UTF-8");
		//System.out.println("---------Received String = " + requestObject);
		return requestObject;
	}
}
