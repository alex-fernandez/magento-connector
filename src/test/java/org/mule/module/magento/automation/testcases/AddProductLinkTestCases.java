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

public class AddProductLinkTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("addProductLink");

			MessageProcessor createProductFlow = lookupFlowConstruct("create-product");
			MuleEvent res = createProductFlow.process(getTestEvent(testObjects));
			Integer productId = (Integer) res.getMessage().getPayload();
			testObjects.put("productId", productId);
			
			// change the sku for the second product
			testObjects.put("sku", ((String) testObjects.get("sku")) + "abc");
			res = createProductFlow.process(getTestEvent(testObjects));
			Integer linkedProductId = (Integer) res.getMessage().getPayload();
			testObjects.put("linkedProductIdOrSku", linkedProductId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	// This test fails because according to http://www.magentocommerce.com/api/soap/catalog/catalogProductLink/catalog_product_link.assign.html
	// I'm expecting a boolean result but I get a HashMap
	public void testAddProductLink() {
		try {
			String linkType = "related";
			testObjects.put("type", linkType);
			MessageProcessor addProductLinkFlow = lookupFlowConstruct("add-product-link");
			MuleEvent res = addProductLinkFlow.process(getTestEvent(testObjects));
			
			assertTrue("Assert that the return type of the operation should be boolean (true)", (Boolean) res.getMessage().getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			int productId = (Integer) testObjects.get("productId");
			int linkedProductId = (Integer) testObjects.get("linkedProductIdOrSku");
			deleteProductById(productId);
			deleteProductById(linkedProductId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
