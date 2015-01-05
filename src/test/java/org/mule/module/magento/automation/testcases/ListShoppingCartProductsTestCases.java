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

import com.magento.api.CatalogProductCreateEntity;
import com.magento.api.CatalogProductEntity;
import com.magento.api.ShoppingCartProductEntity;

public class ListShoppingCartProductsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listShoppingCartProducts");

			List<HashMap<String, Object>> productDefinitions = (List<HashMap<String, Object>>) testObjects.get("products");
			
			List<ShoppingCartProductEntity> shoppingCartEntities = new ArrayList<ShoppingCartProductEntity>();
			List<Integer> productIds = new ArrayList<Integer>();
			
			// Iterate over each product definition and insert
			for (HashMap<String, Object> productDefinition : productDefinitions) {
				String productType = (String) productDefinition.get("type");
				int productSet = (Integer) productDefinition.get("set");
				String productSKU = (String) productDefinition.get("sku");
				CatalogProductCreateEntity attributes = (CatalogProductCreateEntity) productDefinition.get("attributesRef");
			
				// Get the product ID and the number of items we want to place in the shopping cart
				int productId = createProduct(productType, productSet, productSKU, attributes);
				double qtyToPurchase = (Double) productDefinition.get("qtyToPurchase");
				
				productIds.add(productId);
				
				ShoppingCartProductEntity shoppingCartEntity = new ShoppingCartProductEntity();
				shoppingCartEntity.setProduct_id(productId + "");
				shoppingCartEntity.setQty(qtyToPurchase);
								
				shoppingCartEntities.add(shoppingCartEntity);
			}
			testObjects.put("productIds", productIds);
			
			String storeId = testObjects.get("storeId").toString();
			
			// Create the shopping cart
			int shoppingCartId = createShoppingCart(storeId);
			testObjects.put("quoteId", shoppingCartId);

			addProductsToShoppingCart(shoppingCartId, shoppingCartEntities);
			testObjects.put("shoppingCartEntities", shoppingCartEntities);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testListShoppingCartProduct() {
		try {
			List<ShoppingCartProductEntity> products = (List<ShoppingCartProductEntity>) testObjects.get("shoppingCartEntities");
						
			MessageProcessor flow = lookupFlowConstruct("list-shopping-cart-products");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<CatalogProductEntity> shoppingCartProducts = (List<CatalogProductEntity>) response.getMessage().getPayload();
			assertTrue(shoppingCartProducts.size() == products.size());
			
			for (ShoppingCartProductEntity shoppingCartEntity : products) {
				boolean inList = MagentoTestHelper.isProductInShoppingCart(shoppingCartProducts, shoppingCartEntity);
				assertTrue(inList);
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
