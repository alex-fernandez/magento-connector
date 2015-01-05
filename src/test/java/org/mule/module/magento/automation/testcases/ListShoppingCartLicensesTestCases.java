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
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.ShoppingCartLicenseEntity;

public class ListShoppingCartLicensesTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listShoppingCartLicenses");
			
			String storeId = testObjects.get("storeId").toString();
			int quoteId = createShoppingCart(storeId);
			testObjects.put("quoteId", quoteId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListShoppingCartLicenses() {
		try {
			List<String> licenses = (List<String>) testObjects.get("licenses");
			
			MessageProcessor flow = lookupFlowConstruct("list-shopping-cart-licenses");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<ShoppingCartLicenseEntity> definedLicenses = (List<ShoppingCartLicenseEntity>) response.getMessage().getPayload();
			for (ShoppingCartLicenseEntity license : definedLicenses) {
				assertTrue(licenses.contains(license.getContent()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	}
}
