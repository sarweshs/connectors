package org.gcprestapis;

import org.gcpcloud.impl.GCSDataStore;

/**
 * Unit test for simple App.
 */
public class AppTest {
   
    
    public static void main(String[] args) {
		GCSDataStore store = new GCSDataStore();
		store.storeData("test-intermediate-store", "portalid/test", "testdata");
	}
}
