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
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.CatalogProductCreateEntity;
import com.magento.api.ShoppingCartProductEntity;

public class AddShoppingCartProductTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("addShoppingCartProduct");
			
			String storeId = testObjects.get("storeId").toString();

			// Initialise hashmaps
			HashMap<Integer, Double> productIds = new HashMap<Integer, Double>();
			List<HashMap<String, Object>> productDefinitions = (List<HashMap<String, Object>>) testObjects.get("products");
		
			// Iterate over each product definition and insert
			for (HashMap<String, Object> productDefinition : productDefinitions) {
				String productType = (String) productDefinition.get("type");
				int productSet = (Integer) productDefinition.get("set");
				String productSKU = (String) productDefinition.get("sku");
				CatalogProductCreateEntity attributes = (CatalogProductCreateEntity) productDefinition.get("attributesRef");
			
				// Get the product ID and the number of items we want to place in the shopping cart
				int productId = createProduct(productType, productSet, productSKU, attributes);
				double qtyToPurchase = (Double) productDefinition.get("qtyToPurchase");
				productIds.put(productId, qtyToPurchase);
			}
			testObjects.put("productIds", productIds);
			
			// Create the shopping cart
			int shoppingCartId = createShoppingCart(storeId);
			testObjects.put("shoppingCartId", shoppingCartId);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testAddShoppingCartProduct() {
		try {
			HashMap<Integer, Double> productIds = (HashMap<Integer, Double>) testObjects.get("productIds");
			Integer shoppingCartId = (Integer) testObjects.get("shoppingCartId");
			
			testObjects.put("quoteId", shoppingCartId);

			List<ShoppingCartProductEntity> productsRef = new ArrayList<ShoppingCartProductEntity>();
			
			// Iterate over each created product
			// Create a shopping cart product, and assign the product id and the quantity
			for (Map.Entry<Integer, Double> entry : productIds.entrySet()) {
				Integer productId = entry.getKey();
				Double quantity = entry.getValue();
				
				ShoppingCartProductEntity entity = new ShoppingCartProductEntity();
				entity.setProduct_id(productId + "");
				entity.setQty(quantity);
				productsRef.add(entity);
			}
			
			testObjects.put("productsRef", productsRef);
			
			// Add the shopping cart products
			MessageProcessor flow = lookupFlowConstruct("add-shopping-cart-product");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			// Assert that the call was successful
			Boolean result = (Boolean) response.getMessage().getPayload();
			assertTrue(result);
			
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
			// Get the product Ids of those which were created
			HashMap<Integer, Double> productIds = (HashMap<Integer, Double>) testObjects.get("productIds");
			// Iterate over the hashmap entries and get the product id, then delete
			for (Map.Entry<Integer, Double> entry : productIds.entrySet()) {
				Integer productId = entry.getKey();
				deleteProductById(productId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
