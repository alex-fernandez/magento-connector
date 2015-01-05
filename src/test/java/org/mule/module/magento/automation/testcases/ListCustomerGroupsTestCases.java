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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CustomerGroupEntity;

public class ListCustomerGroupsTestCases extends MagentoTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = new HashMap<String, Object>();
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testListCustomerGroups() {
		try {
			MessageProcessor flow = lookupFlowConstruct("list-customer-groups");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<CustomerGroupEntity> customerGroups = (List<CustomerGroupEntity>) response.getMessage().getPayload();
			assertNotNull(customerGroups);
			for (CustomerGroupEntity group : customerGroups) {
				// This is the only assertion we can perform since there is currently no way
				// to add a customer group using Soap V2
				assertNotNull(group);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
