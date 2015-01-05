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
import com.magento.api.ShoppingCartProductEntity;

public class UpdateShoppingCartProductTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("updateShoppingCartProduct");
			
			String storeId = testObjects.get("storeId").toString();
			int quoteId = createShoppingCart(storeId);
			testObjects.put("quoteId", quoteId);
			
			List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) testObjects.get("products");
			List<Integer> productIds = new ArrayList<Integer>();
			List<ShoppingCartProductEntity> shoppingCartProductsBefore = new ArrayList<ShoppingCartProductEntity>();
			List<ShoppingCartProductEntity> shoppingCartProductsAfter = new ArrayList<ShoppingCartProductEntity>();
			
			for (HashMap<String, Object> product : products) {
				
				// Get the product data
				String productType = (String) product.get("type");
				int productSet = (Integer) product.get("set");
				String productSKU = (String) product.get("sku");
				CatalogProductCreateEntity attributes = (CatalogProductCreateEntity) product.get("attributesRef");
			
				// Create the product and get the product ID
				int productId = createProduct(productType, productSet, productSKU, attributes);
				
				// Get the quantity to place in the shopping cart (before)
				double qtyToPurchaseBefore = (Double) product.get("qtyToPurchaseBefore");
				// Get the quantity to place in the shopping cart (after)
				double qtyToPurchaseAfter = (Double) product.get("qtyToPurchaseAfter");
				
				// Create the shopping cart product entity (before)
				ShoppingCartProductEntity shoppingCartProductBefore = new ShoppingCartProductEntity();
				shoppingCartProductBefore.setProduct_id(productId + "");
				shoppingCartProductBefore.setQty(qtyToPurchaseBefore);
				shoppingCartProductsBefore.add(shoppingCartProductBefore);

				// Create the shopping cart product entity (after)
				ShoppingCartProductEntity shoppingCartProductAfter = new ShoppingCartProductEntity();
				shoppingCartProductAfter.setProduct_id(productId + "");
				shoppingCartProductAfter.setQty(qtyToPurchaseAfter);
				shoppingCartProductsAfter.add(shoppingCartProductAfter);
				
				productIds.add(productId);
			}
			testObjects.put("productIds", productIds);
			
			addProductsToShoppingCart(quoteId, shoppingCartProductsBefore);
			
			testObjects.put("shoppingCartProductsBefore", shoppingCartProductsBefore);
			testObjects.put("shoppingCartProductsAfter", shoppingCartProductsAfter);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testUpdateShoppingCartProduct() {
		try {
			List<ShoppingCartProductEntity> shoppingCartProductsAfter = (List<ShoppingCartProductEntity>) testObjects.get("shoppingCartProductsAfter");
			testObjects.put("productsRef", shoppingCartProductsAfter);		
		
			MessageProcessor flow = lookupFlowConstruct("update-shopping-cart-product");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
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
