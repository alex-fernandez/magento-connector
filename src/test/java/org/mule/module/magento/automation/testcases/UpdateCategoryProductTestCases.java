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

public class UpdateCategoryProductTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("updateCategoryProduct");

			MessageProcessor createCategoryFlow = lookupFlowConstruct("create-category");
			MuleEvent res = createCategoryFlow
					.process(getTestEvent(testObjects));
			Integer categoryId = (Integer) res.getMessage().getPayload();
			testObjects.put("categoryId", categoryId);

			MessageProcessor createProductFlow = lookupFlowConstruct("create-product");
			MuleEvent res2 = createProductFlow
					.process(getTestEvent(testObjects));
			Integer productId = (Integer) res2.getMessage().getPayload();
			testObjects.put("productId", productId);
			
			MessageProcessor addCategoryProductFlow = lookupFlowConstruct("add-category-product");
			addCategoryProductFlow.process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class })
	@Test
	public void testUpdateCategoryProduct() {
		try {
			MessageProcessor flow = lookupFlowConstruct("update-category-product");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			Boolean result = (Boolean) response.getMessage().getPayload();
			assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			int productId = (Integer) testObjects.get("productId");
			int categoryId = (Integer) testObjects.get("categoryId");
			deleteProductById(productId);
			deleteCategory(categoryId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
