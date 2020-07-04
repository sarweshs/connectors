package org.core;

import java.io.IOException;

import org.models.NextIterationModel;
import org.models.RuleModel;
import org.models.SourceTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) throws IOException {
    	/*SourceTemlate model = new SourceTemlate("weather", "https://www.metaweather.com/api/location/search/?query=san", null, null);
    	ObjectMapper objectMapper = new ObjectMapper();
    	System.out.println(objectMapper.writeValueAsString(model));
    	RestApiUtil apiutil = new RestApiUtil();
    	String response = apiutil.callGetApi("https://reqres.in/api/users?page=1" , null);
		System.out.println(response);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> responseObj = objectMapper.readValue(response.getBytes(), Map.class);
		
		System.out.println("Response Object:" + ((List<Map<String, Object>>)responseObj.get("data")).get(0));*/
    	SourceTemplate source = new SourceTemplate("users", "data", "id", "https://reqres.in/api/users?page=1", null, "test-reqresqueue");
    	
    	RuleModel rule = new RuleModel();
    	rule.setFirstPage("page");
    	rule.setLastPage("total_pages");
    	rule.setIncrement(1);
    	rule.setReplaceQueryParamter("page");
    	
    	source.setRule(rule);
    	NextIterationModel next = new NextIterationModel();
    	next.setResponseFor("users");
    	
    	source.setNext(next);
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	String jsonString = objectMapper.writeValueAsString(source);
		System.out.println(jsonString);
    	
    	SourceTemplate templateFromMessage = objectMapper.readValue(jsonString.getBytes(), SourceTemplate.class);
    	
	}
}
