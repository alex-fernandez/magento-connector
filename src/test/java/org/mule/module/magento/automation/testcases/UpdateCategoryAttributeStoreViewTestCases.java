/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class UpdateCategoryAttributeStoreViewTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("updateCategoryAttributeStoreView");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testUpdateCategoryAttributeStoreViewTestCases() {
		try {
			MessageProcessor flow = lookupFlowConstruct("update-category-attribute-store-view");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Integer result = (Integer) response.getMessage().getPayload();
			assertNotNull(result);
			Integer storeViewId = Integer.parseInt((String) testObjects.get("storeViewIdOrCode"));
			assertEquals("Assert that the integer returned is the store view id which was set", storeViewId, result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
		
}
