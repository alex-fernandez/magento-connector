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

import com.magento.api.CustomerCustomerEntityToCreate;

public class UpdateCustomerTestCases extends MagentoTestParent {
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("updateCustomer");
			
			CustomerCustomerEntityToCreate customerBefore = (CustomerCustomerEntityToCreate) testObjects.get("customerBefore");
			int customerId = createCustomer(customerBefore);
			
			testObjects.put("customerId", customerId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({ RegressionTests.class })
	@Test
	public void testUpdateCustomer() {
		try {
			CustomerCustomerEntityToCreate customerAfter = (CustomerCustomerEntityToCreate) testObjects.get("customerAfter");
			
			testObjects.put("customerRef", customerAfter);
			
			MessageProcessor flow = lookupFlowConstruct("update-customer");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			boolean result = (Boolean) response.getMessage().getPayload();		
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
			int customerId = (Integer) testObjects.get("customerId");
			deleteCustomer(customerId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
