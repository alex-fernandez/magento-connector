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

import com.magento.api.CustomerCustomerEntity;
import com.magento.api.CustomerCustomerEntityToCreate;

public class ListCustomersTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listCustomers");
			
			List<Integer> customerIds = new ArrayList<Integer>();
			
			List<CustomerCustomerEntityToCreate> customers = (List<CustomerCustomerEntityToCreate>) testObjects.get("customers");
			
			// Create the customers
			for (CustomerCustomerEntityToCreate customer : customers) {
				Integer customerId = createCustomer(customer);
				customerIds.add(customerId);
			}

			testObjects.put("customerIds", customerIds);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListCustomers_WithoutFilter() {
		try {
			List<Integer> customerIds = (List<Integer>) testObjects.get("customerIds");
			
			MessageProcessor flow = lookupFlowConstruct("list-customers-without-filter");
			MuleEvent event = flow.process(getTestEvent(testObjects));
			
			List<CustomerCustomerEntity> customers = (List<CustomerCustomerEntity>) event.getMessage().getPayload();
			for (CustomerCustomerEntity customer : customers) {
				Integer temp = customer.getCustomer_id();
				assertTrue(customerIds.contains(temp));
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListCustomers_WithFilter() {
		try {
			
			String emailFilter = testObjects.get("emailFilter").toString();
			List<Integer> customerIds = (List<Integer>) testObjects.get("customerIds");
			
			MessageProcessor flow = lookupFlowConstruct("list-customers-with-filter");
			MuleEvent event = flow.process(getTestEvent(testObjects));
			
			List<CustomerCustomerEntity> retrievedCustomers = (List<CustomerCustomerEntity>) event.getMessage().getPayload();
			assertTrue(retrievedCustomers.size() == 1);
			
			CustomerCustomerEntity customer = retrievedCustomers.get(0);
			assertTrue(customerIds.contains(customer.getCustomer_id()));
			assertTrue(customer.getEmail().equals(emailFilter));
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
			List<Integer> customerIds = (List<Integer>) testObjects.get("customerIds");

			// Delete the customers
			for (Integer customerId : customerIds) {
				deleteCustomer(customerId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
