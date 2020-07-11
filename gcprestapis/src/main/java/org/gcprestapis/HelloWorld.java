package org.gcprestapis;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.gcpcloud.impl.GCSDataStore;

/**
 * Hello world!
 *
 */
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class HelloWorld implements HttpFunction {
	private static final Logger logger = Logger.getLogger(HelloWorld.class.getName());

	private static final Gson gson = new Gson();
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		// Check URL parameters for "name" field
		// "world" is the default value
		String name = request.getFirstQueryParameter("name").orElse("world");

		// Parse JSON request and check for "name" field
		try {
			//JsonElement requestParsed = gson.fromJson(request.getReader(), JsonElement.class);
			String extractPostRequestBody = extractPostRequestBody(request);
			System.out.println("Extracted request body :" + extractPostRequestBody);
			JsonElement requestParsed = gson.fromJson(extractPostRequestBody, JsonElement.class);
			JsonObject requestJson = null;

			if (requestParsed != null && requestParsed.isJsonObject()) {
				requestJson = requestParsed.getAsJsonObject();
			}

			if (requestJson != null && requestJson.has("name")) {
				name = requestJson.get("name").getAsString();
			}
			if (requestJson != null && requestJson.has("portalid")) {
				String portalId = requestJson.get("portalid").getAsString();
				GCSDataStore store = new GCSDataStore();
				store.storeData("test-intermediate-store", portalId + "/" + UUID.randomUUID(), requestJson);
			}
		} catch (JsonParseException e) {
			logger.severe("Error parsing JSON: " + e.getMessage());
		}

		///var writer = new PrintWriter(response.getWriter());
		//writer.printf("Hello %s!", name);
		String res = String.format("Hello %s!", name);
		
		OutputStream os = response.getOutputStream();
        os.write(res.getBytes());
        os.flush(); 
	}
	
	private  String extractPostRequestBody(HttpRequest request) throws IOException {
	   
	    String requestObject = IOUtils.toString(request.getInputStream(), "UTF-8");
	    System.out.println("---------Received String = " + requestObject);
		return requestObject;
	}
}
