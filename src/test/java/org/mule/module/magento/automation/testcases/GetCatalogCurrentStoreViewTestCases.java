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

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class GetCatalogCurrentStoreViewTestCases extends MagentoTestParent {

	@Category({RegressionTests.class})
	@Test
	public void testGetCatalogCurrentStoreView() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-catalog-current-store-view");
			MuleEvent response = flow.process(getTestEvent(null));
			
			Integer defaultStoreId = (Integer) response.getMessage().getPayload();
			assertNotNull(defaultStoreId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
