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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogAttributeEntity;

public class ListCategoryAttributesTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String,Object>) context.getBean("listCategoryAttributes");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testListCategoryAttributes() {
		try {
			List<String> definedAttributes = (List<String>) testObjects.get("attributes");
			
			MessageProcessor flow = lookupFlowConstruct("list-category-attributes");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<CatalogAttributeEntity> attributes = (List<CatalogAttributeEntity>) response.getMessage().getPayload();
			
			assertTrue(attributes.size() == definedAttributes.size());
			for (CatalogAttributeEntity attribute : attributes) {
				assertTrue(definedAttributes.contains(attribute.getCode()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
