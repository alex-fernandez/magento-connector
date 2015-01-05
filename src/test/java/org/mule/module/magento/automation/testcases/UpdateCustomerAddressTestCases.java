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

import com.magento.api.CustomerAddressEntityCreate;
import com.magento.api.CustomerCustomerEntityToCreate;

public class UpdateCustomerAddressTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("updateCustomerAddress");
			
			CustomerCustomerEntityToCreate customer = (CustomerCustomerEntityToCreate) testObjects.get("customerRef");
			int customerId = createCustomer(customer);
					
			testObjects.put("customerId", customerId);
		
			CustomerAddressEntityCreate address = (CustomerAddressEntityCreate) testObjects.get("customerAddressBefore");
			int addressId = createCustomerAddress(customerId, address);
			
			testObjects.put("addressId", addressId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({ RegressionTests.class })
	@Test
	public void testUpdateCustomerAddress() {
		try {
			CustomerAddressEntityCreate addressAfter = (CustomerAddressEntityCreate) testObjects.get("customerAddressAfter");
			testObjects.put("customerAddressRef", addressAfter);
									
			MessageProcessor flow = lookupFlowConstruct("update-customer-address");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Boolean result = (Boolean) response.getMessage().getPayload();
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
			int addressId = (Integer) testObjects.get("addressId");
			deleteCustomerAddress(addressId);
			
			int customerId = (Integer) testObjects.get("customerId");
			deleteCustomer(customerId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
