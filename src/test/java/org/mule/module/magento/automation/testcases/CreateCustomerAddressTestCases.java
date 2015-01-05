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

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CustomerCustomerEntityToCreate;

public class CreateCustomerAddressTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("createCustomerAddress");
			
			CustomerCustomerEntityToCreate customer = (CustomerCustomerEntityToCreate) testObjects.get("customerRef");
			int customerId = createCustomer(customer);
			
			testObjects.put("customerId", customerId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCreateCustomerAddress() {
		try {
			MessageProcessor flow = lookupFlowConstruct("create-customer-address");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Integer customerAddressId = (Integer) response.getMessage().getPayload();
			assertNotNull(customerAddressId);
		
			testObjects.put("customerAddressId", customerAddressId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			int customerAddressId = (Integer) testObjects.get("customerAddressId");
			deleteCustomerAddress(customerAddressId);
			
			int customerId = (Integer) testObjects.get("customerId");
			deleteCustomer(customerId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
