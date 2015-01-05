/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertNotNull;
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

import com.magento.api.CatalogInventoryStockItemEntity;
import com.magento.api.CatalogProductCreateEntity;

public class ListInventoryStockItemsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listInventoryStockItems");
			
			List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) testObjects.get("products");
			List<Integer> productIds = new ArrayList<Integer>();
			
			// Iterate over all the defined products and insert them into Magento
			// Record the returned product IDs.
			for (HashMap<String, Object> product : products) {
				String productType = (String) product.get("type");
				int productSet = (Integer) product.get("set");
				String productSKU = (String) product.get("sku");
				CatalogProductCreateEntity attributes = (CatalogProductCreateEntity) product.get("attributesRef");
			
				int productId = createProduct(productType, productSet, productSKU, attributes);
				productIds.add(productId);
			}
								
			testObjects.put("productIds", productIds);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListInventoryStockItems() {
		try {
			List<Integer> productIds = (List<Integer>) testObjects.get("productIds");
			// Connector expects a list of Strings instead of list of integers, so reformat
			List<String> productIdsFormatted = MagentoTestHelper.getStringList(productIds);
			
			testObjects.put("productIdOrSkusRef", productIdsFormatted);
			
			// Get the inventory
			MessageProcessor flow = lookupFlowConstruct("list-inventory-stock-items");
			MuleEvent response = flow.process(getTestEvent(testObjects));
		
			// Get the response and perform the assertions
			List<CatalogInventoryStockItemEntity> stockItems = (List<CatalogInventoryStockItemEntity>) response.getMessage().getPayload();
			assertNotNull(stockItems);
			assertTrue(productIds.size() == stockItems.size());
			
			for (CatalogInventoryStockItemEntity stockItem : stockItems) {
				assertTrue(productIdsFormatted.contains(stockItem.getProduct_id()));
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
			// Delete the products
			List<Integer> productIds = (List<Integer>) testObjects.get("productIds");
			for (Integer productId : productIds) {
				deleteProductById(productId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
