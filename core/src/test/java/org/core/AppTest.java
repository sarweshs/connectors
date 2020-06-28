package org.core;

import org.models.ApiSourceDataModel;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    
    public static void main(String[] args) throws JsonProcessingException {
    	ApiSourceDataModel model = new ApiSourceDataModel("weather", "https://www.metaweather.com/api/location/search/?query=san", null, null);
    	ObjectMapper objectMapper = new ObjectMapper();
    	System.out.println(objectMapper.writeValueAsString(model));
	}
}
