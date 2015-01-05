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

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogProductCreateEntity;

public class UpdateProductSpecialPriceTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("updateProductSpecialPrice");
			
			String type = (String) testObjects.get("type");
			String sku = (String) testObjects.get("sku");
			int set = (Integer) testObjects.get("set");
			
			CatalogProductCreateEntity productAttributes = (CatalogProductCreateEntity) testObjects.get("attributesRef");
			int productId = createProduct(type, set, sku, productAttributes);
			
			testObjects.put("productId", productId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testUpdateProductSpecialPrice() {
		try {
			MessageProcessor flow = lookupFlowConstruct("update-product-special-price");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Integer result = (Integer) response.getMessage().getPayload();
			assertTrue(result == 1);
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
