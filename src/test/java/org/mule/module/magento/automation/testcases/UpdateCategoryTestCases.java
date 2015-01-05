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

import com.magento.api.CatalogCategoryEntityCreate;

public class UpdateCategoryTestCases extends MagentoTestParent {
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("updateCategory");
			
			int parentId = (Integer) testObjects.get("parentId");
			
			CatalogCategoryEntityCreate beforeCategory = (CatalogCategoryEntityCreate) testObjects.get("beforeCategory");
			int categoryId = createCategory(parentId, beforeCategory);
			
			testObjects.put("categoryId", categoryId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({ RegressionTests.class })
	@Test
	public void testUpdateCategory() {
		try {
			CatalogCategoryEntityCreate afterCategory = (CatalogCategoryEntityCreate) testObjects.get("afterCategory");
			
			testObjects.put("catalogCategoryEntityRef", afterCategory);
			
			MessageProcessor flow = lookupFlowConstruct("update-category");
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
			int categoryId = (Integer) testObjects.get("categoryId");
			deleteCategory(categoryId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
