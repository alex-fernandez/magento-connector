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

import com.magento.api.CatalogAttributeEntity;
import com.magento.api.CatalogAttributeOptionEntity;

public class ListCategoryAttributeOptionsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = new HashMap<String, Object>();
			
			MessageProcessor flow = lookupFlowConstruct("list-category-attributes");
			MuleEvent response = flow.process(getTestEvent(null));
			
			List<CatalogAttributeEntity> attributes = (List<CatalogAttributeEntity>) response.getMessage().getPayload();
			testObjects.put("attributes", attributes);			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListCategoryAttributeOptions() {
		try {
			List<CatalogAttributeEntity> attributes = (List<CatalogAttributeEntity>) testObjects.get("attributes");
			
			for (CatalogAttributeEntity attribute : attributes) {				
				if (attribute.getAttribute_id() != null) {				
					testObjects.put("attributeId", attribute.getAttribute_id());
					MessageProcessor flow = lookupFlowConstruct("list-category-attribute-options");
					MuleEvent response = flow.process(getTestEvent(testObjects));
					
					List<CatalogAttributeOptionEntity> attributeOptions = (List<CatalogAttributeOptionEntity>) response.getMessage().getPayload();
					assertNotNull(attributeOptions);
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
		
}
