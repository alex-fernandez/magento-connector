/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogProductEntity;

public class ListProductsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listProducts");
			MessageProcessor flow = lookupFlowConstruct("create-product");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			testObjects.put("productId", response.getMessage().getPayload());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListProducts() {
		try {
			MessageProcessor flow = lookupFlowConstruct("list-products");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<CatalogProductEntity> products = (List<CatalogProductEntity>) response.getMessage().getPayload();
			assertEquals("There should be 1 product", 1, products.size());
			
			CatalogProductEntity product = products.get(0);
			assertEquals("Product sku should be " + testObjects.get("sku"), testObjects.get("sku"), product.getSku());
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
