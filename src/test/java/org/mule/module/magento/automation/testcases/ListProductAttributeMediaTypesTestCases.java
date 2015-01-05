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

import com.magento.api.CatalogProductAttributeMediaTypeEntity;

public class ListProductAttributeMediaTypesTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listProductAttributeMediaTypes");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testListProductAttributeMediaTypes() {
		try {
			MessageProcessor listProductLinkFlow = lookupFlowConstruct("list-product-attribute-media-types");
			MuleEvent res = listProductLinkFlow.process(getTestEvent(testObjects));
			List<CatalogProductAttributeMediaTypeEntity> catalogProductAttributeMediaTypes = (List<CatalogProductAttributeMediaTypeEntity>) res.getMessage().getPayload();
			
			assertNotNull(catalogProductAttributeMediaTypes);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
