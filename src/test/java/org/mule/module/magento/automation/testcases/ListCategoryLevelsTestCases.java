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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogCategoryEntityCreate;
import com.magento.api.CatalogCategoryEntityNoChildren;

public class ListCategoryLevelsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listCategoryLevels");

			int parentCategoryId = (Integer) testObjects.get("parentCategoryId");
			List<Integer> categoryIds = new ArrayList<Integer>();
			List<CatalogCategoryEntityCreate> categories = (List<CatalogCategoryEntityCreate>) testObjects.get("categories");
			
			for (CatalogCategoryEntityCreate category : categories) {
				int categoryId = createCategory(parentCategoryId, category);
				categoryIds.add(categoryId);
			}
			
			testObjects.put("categoryIds", categoryIds);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListCategoryLevels_WithParentCategory() {
		try {
			List<Integer> categoryIds = (List<Integer>) testObjects.get("categoryIds");
			MessageProcessor flow = lookupFlowConstruct("list-category-levels-with-parent-category");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<CatalogCategoryEntityNoChildren> categories = (List<CatalogCategoryEntityNoChildren>) response.getMessage().getPayload();
			
			assertTrue(categories.size() == categoryIds.size());
			for (CatalogCategoryEntityNoChildren category : categories) {
				categoryIds.contains(category.getCategory_id());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListCategoryLevels() {
		try {
			MessageProcessor flow = lookupFlowConstruct("list-category-levels");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<CatalogCategoryEntityNoChildren> categories = (List<CatalogCategoryEntityNoChildren>) response.getMessage().getPayload();
			assertTrue(categories.size() == 1); // Only the default category should be there

			CatalogCategoryEntityNoChildren category = categories.get(0);
			assertTrue(category.getCategory_id() == DEFAULT_CATEGORY_ID); // The default category
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@After
	public void tearDown() {
		try {
			List<Integer> categoryIds = (List<Integer>) testObjects.get("categoryIds");
			for (int categoryId : categoryIds) {
				deleteCategory(categoryId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
