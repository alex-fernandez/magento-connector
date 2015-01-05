/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class DeleteProductAttributeMediaTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	// This test depends on there being an img.gif file in the classpath (when writing this test there was such a file in src/test/resources).
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("deleteProductAttributeMedia");
			MessageProcessor createProductFlow = lookupFlowConstruct("create-product");
			MuleEvent response = createProductFlow.process(getTestEvent(testObjects));
			response.getMessage().getPayload();
			
			testObjects.put("productId", response.getMessage().getPayload());
			
			MessageProcessor flow = lookupFlowConstruct("create-product-attribute-media");
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("img.gif");
			MuleEvent event = getTestEvent(is);
			
			for(String key : testObjects.keySet()) {
				event.setFlowVariable(key, testObjects.get(key));
			}
			
			MuleEvent responseFileName = flow.process(event);
			String newImageFilename = (String) responseFileName.getMessage().getPayload();
			
			testObjects.put("fileName", newImageFilename);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testDeleteProductAttributeMedia() {
		try {
			MessageProcessor flow = lookupFlowConstruct("delete-product-attribute-media");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Boolean result = (Boolean) response.getMessage().getPayload();
			assertTrue(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			int productId = (Integer) testObjects.get("productId");
			deleteProductById(productId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
