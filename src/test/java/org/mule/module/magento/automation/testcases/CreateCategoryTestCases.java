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

public class CreateCategoryTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("createCategory");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCreateCategory() {
		try {
			MessageProcessor flow = lookupFlowConstruct("create-category");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Integer categoryId = (Integer) response.getMessage().getPayload();
			assertTrue(categoryId != null);
			
			testObjects.put("categoryId", categoryId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			int categoryId = (Integer) testObjects.get("categoryId");
			deleteCategory(categoryId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
