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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogProductTierPriceEntity;
import com.magento.api.CustomerGroupEntity;

public class UpdateProductAttributeTierPriceTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("updateProductAttributeTierPrice");
			MessageProcessor listCustomerGroupsFlow = lookupFlowConstruct("list-customer-groups");
			MuleEvent response = listCustomerGroupsFlow.process(getTestEvent(testObjects));
			
			List<CustomerGroupEntity> customerGroupEntities = (List<CustomerGroupEntity>) response.getMessage().getPayload();
			CustomerGroupEntity cge = customerGroupEntities.get(0);
			cge.getCustomer_group_id();
			
			CatalogProductTierPriceEntity catalogProductTierPriceEntity = (CatalogProductTierPriceEntity) testObjects.get("catalogProductTierPriceEntityRef");
			catalogProductTierPriceEntity.setCustomer_group_id(String.valueOf(cge.getCustomer_group_id()));
			catalogProductTierPriceEntity.setWebsite("0");
			catalogProductTierPriceEntity.setQty(50);
			catalogProductTierPriceEntity.setPrice(9.90);
			testObjects.put("catalogProductTierPriceEntityRef", catalogProductTierPriceEntity);
			
			MessageProcessor createProductFlow = lookupFlowConstruct("create-product");
			response = createProductFlow.process(getTestEvent(testObjects));
			
			testObjects.put("productId", response.getMessage().getPayload());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testUpdateProductAttributeTierPrice() {
		try {
			CatalogProductTierPriceEntity catalogProductTierPriceEntity = (CatalogProductTierPriceEntity) testObjects.get("catalogProductTierPriceEntityRef");
			MuleEvent event = getTestEvent(catalogProductTierPriceEntity);
			for(String key : testObjects.keySet()) {
				event.setFlowVariable(key, testObjects.get(key));
			}
			
			MessageProcessor flow = lookupFlowConstruct("update-product-attribute-tier-price");
			MuleEvent response = flow.process(event);
			Integer result = (Integer) response.getMessage().getPayload();
			assertNotNull(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			int productId = (Integer) testObjects.get("productId");
			deleteProductById(productId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
