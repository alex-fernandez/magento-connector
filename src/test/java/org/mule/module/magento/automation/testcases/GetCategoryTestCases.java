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
import com.magento.api.CatalogCategoryInfo;

public class GetCategoryTestCases extends MagentoTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getCategory");
			
			int parentId = (Integer) testObjects.get("parentId");
			CatalogCategoryEntityCreate category = (CatalogCategoryEntityCreate) testObjects.get("catalogCategoryEntityRef");
			int categoryId = createCategory(parentId, category);
		
			testObjects.put("categoryId", categoryId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetCategory() {
		try {
		
			int categoryId = (Integer) testObjects.get("categoryId");
			int parentId = (Integer) testObjects.get("parentId");
			
			MessageProcessor flow = lookupFlowConstruct("get-category");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			CatalogCategoryInfo category = (CatalogCategoryInfo) response.getMessage().getPayload();
			assertTrue(category.getParent_id().equals(Integer.toString(parentId)));
			assertTrue(category.getCategory_id().equals(Integer.toString(categoryId)));
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
