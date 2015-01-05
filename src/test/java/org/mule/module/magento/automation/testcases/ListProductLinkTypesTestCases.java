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

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class ListProductLinkTypesTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class })
	@Test
	public void testListProductLinkTypes() {
		try {
			MessageProcessor flow = lookupFlowConstruct("list-product-link-types");

			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<String> productLinkTypes = (List<String>) response.getMessage().getPayload();
			assertNotNull(productLinkTypes);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
