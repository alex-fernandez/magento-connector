/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.ShoppingCartCustomerEntity;
import com.magento.api.ShoppingCartInfoEntity;

public class GetInfoShoppingCartTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getInfoShoppingCart");
			
			String storeId = testObjects.get("storeId").toString();
			
			int quoteId = createShoppingCart(storeId);
			testObjects.put("quoteId", quoteId);
			
			ShoppingCartCustomerEntity customer = (ShoppingCartCustomerEntity) testObjects.get("customer");
			setShoppingCartCustomer(quoteId, customer);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetInfoShoppingCart() {
		try {
			int quoteId = (Integer) testObjects.get("quoteId");
			
			MessageProcessor flow = lookupFlowConstruct("get-info-shopping-cart");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			ShoppingCartInfoEntity result = (ShoppingCartInfoEntity) response.getMessage().getPayload();
			assertNotNull(result);
			assertTrue(quoteId == result.getQuote_id());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
		
}
