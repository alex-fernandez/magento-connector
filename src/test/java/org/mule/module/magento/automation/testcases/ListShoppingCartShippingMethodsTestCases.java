/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.ShoppingCartCustomerAddressEntity;
import com.magento.api.ShoppingCartShippingMethodEntity;

public class ListShoppingCartShippingMethodsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("listShoppingCartShippingMethods");

			String storeId = testObjects.get("storeId").toString();
			
			int quoteId = createShoppingCart(storeId);
			testObjects.put("quoteId", quoteId); 
			
			List<ShoppingCartCustomerAddressEntity> customerAddresses = (List<ShoppingCartCustomerAddressEntity>) testObjects.get("customerAddresses");
			setCustomerAddressesToShoppingCart(quoteId, customerAddresses);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListShoppingCartShippingMethods() {
		try {
			MessageProcessor flow = lookupFlowConstruct("list-shopping-cart-shipping-methods");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<ShoppingCartShippingMethodEntity> shippingMethods = (List<ShoppingCartShippingMethodEntity>) response.getMessage().getPayload();
			assertNotNull(shippingMethods);
			for (ShoppingCartShippingMethodEntity method : shippingMethods) {
				assertNotNull(method);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
