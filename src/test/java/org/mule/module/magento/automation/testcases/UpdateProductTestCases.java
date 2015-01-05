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

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class UpdateProductTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("updateProduct");
			MessageProcessor flow = lookupFlowConstruct("create-product");
			MuleEvent res = flow.process(getTestEvent(testObjects));
			Integer productId = (Integer) res.getMessage().getPayload();
			testObjects.put("productId", productId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testUpdateProduct() {
		try {
			MessageProcessor flow = lookupFlowConstruct("update-product");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			assertTrue("Assert that the update was successful", (Boolean) response.getMessage().getPayload());
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
