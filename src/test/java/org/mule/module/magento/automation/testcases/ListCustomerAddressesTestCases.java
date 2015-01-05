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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CustomerAddressEntityCreate;
import com.magento.api.CustomerAddressEntityItem;
import com.magento.api.CustomerCustomerEntityToCreate;

public class ListCustomerAddressesTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listCustomerAddresses");
			
			CustomerCustomerEntityToCreate customer = (CustomerCustomerEntityToCreate) testObjects.get("customerRef");
			int customerId = createCustomer(customer);
			
			testObjects.put("customerId", customerId);
			
			List<Integer> addressIds = new ArrayList<Integer>();
			List<CustomerAddressEntityCreate> addresses = (List<CustomerAddressEntityCreate>) testObjects.get("customerAddresses");
			for (CustomerAddressEntityCreate address : addresses) {
				int addressId = createCustomerAddress(customerId, address);
				addressIds.add(addressId);
			}
			
			testObjects.put("addressIds", addressIds);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({ RegressionTests.class })
	@Test
	public void testListCustomerAddresses() {
		try {
			List<Integer> addressIds = (List<Integer>) testObjects.get("addressIds");
			
			MessageProcessor flow = lookupFlowConstruct("list-customer-addresses");
			MuleEvent event = flow.process(getTestEvent(testObjects));
			
			List<CustomerAddressEntityItem> customerAddresses = (List<CustomerAddressEntityItem>) event.getMessage().getPayload();
		
			assertTrue(customerAddresses.size() == addressIds.size());
			for (CustomerAddressEntityItem address : customerAddresses) {
				assertTrue(addressIds.contains(address.getCustomer_address_id()));
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@After
	public void tearDown() {
		try {
			List<Integer> addressIds = (List<Integer>) testObjects.get("addressIds");
			for (int addressId : addressIds) {
				deleteCustomerAddress(addressId);
			}
			
			int customerId = (Integer) testObjects.get("customerId");
			deleteCustomer(customerId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
