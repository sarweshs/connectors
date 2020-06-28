package org.core.util;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestApiUtil {

	public String callGetApi(String api, Map<String, String> queryParams) {
		/*
		 * String responseStr = null; try { DefaultHttpClient httpClient = new
		 * DefaultHttpClient(); HttpGet getRequest = new HttpGet(api);
		 * getRequest.addHeader("accept", "application/json");
		 * 
		 * HttpResponse response = httpClient.execute(getRequest);
		 * 
		 * if (response.getStatusLine().getStatusCode() != 200) { throw new
		 * RuntimeException("Failed : HTTP error code : " +
		 * response.getStatusLine().getStatusCode()); }
		 * 
		 * BufferedReader br = new BufferedReader( new
		 * InputStreamReader((response.getEntity().getContent())));
		 * 
		 * System.out.println("Output from Server .... \n"); String output = null; while
		 * ((output = br.readLine()) != null) { responseStr = responseStr + output; }
		 * 
		 * httpClient.getConnectionManager().shutdown();
		 * 
		 * } catch (ClientProtocolException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } catch (IOException e) {
		 * 
		 * e.printStackTrace(); } //http://dummy.restapiexample.com/api/v1/employee/1
		 * 
		 * return responseStr;
		 */
		// Creating a HttpClient object
		String responseStr = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// Creating a HttpGet object
		HttpGet httpget = new HttpGet(api);

		// Printing the method used
		System.out.println("Request Type: " + httpget.getMethod());

		// Executing the Get request
		HttpResponse httpresponse;
		try {
			httpresponse = httpclient.execute(httpget);
			Scanner sc = new Scanner(httpresponse.getEntity().getContent());

			// Printing the status line
			System.out.println(httpresponse.getStatusLine());
			while (sc.hasNext()) {
				//System.out.println(sc.nextLine());
				responseStr = responseStr + sc.nextLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseStr;

	}
}
