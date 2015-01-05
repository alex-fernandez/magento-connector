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
import com.magento.api.ShoppingCartCustomerAddressEntity;
import com.magento.api.ShoppingCartProductEntity;
import com.magento.api.ShoppingCartTotalsEntity;

public class ListShoppingCartTotalsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listShoppingCartTotals");

			List<HashMap<String, Object>> productDefinitions = (List<HashMap<String, Object>>) testObjects.get("products");

			double totalProductPrice = 0;
			HashMap<Integer, Double> productPrices = new HashMap<Integer, Double>();
			List<ShoppingCartProductEntity> shoppingCartEntities = new ArrayList<ShoppingCartProductEntity>();
			
			// Iterate over each product definition and insert
			for (HashMap<String, Object> productDefinition : productDefinitions) {
				String productType = (String) productDefinition.get("type");
				int productSet = (Integer) productDefinition.get("set");
				String productSKU = (String) productDefinition.get("sku");
				CatalogProductCreateEntity attributes = (CatalogProductCreateEntity) productDefinition.get("attributesRef");

				double price = Double.parseDouble(attributes.getPrice());
				
				// Get the product ID and the number of items we want to place in the shopping cart
				int productId = createProduct(productType, productSet, productSKU, attributes);
				double qtyToPurchase = (Double) productDefinition.get("qtyToPurchase");
				
				ShoppingCartProductEntity shoppingCartEntity = new ShoppingCartProductEntity();
				shoppingCartEntity.setProduct_id(productId + "");
				shoppingCartEntity.setQty(qtyToPurchase);
								
				shoppingCartEntities.add(shoppingCartEntity);
			
				double totalPrice = price * qtyToPurchase;
				totalProductPrice += totalPrice;
				productPrices.put(productId, totalPrice);
			}
			testObjects.put("productPrices", productPrices);
			testObjects.put("shoppingCartEntities", shoppingCartEntities);
			testObjects.put("totalPrice", totalProductPrice);
			
			// Create the shopping cart
			String storeId = testObjects.get("storeId").toString();
			int shoppingCartId = createShoppingCart(storeId);
			testObjects.put("quoteId", shoppingCartId);
			
			// Add the products to the shopping cart
			addProductsToShoppingCart(shoppingCartId, shoppingCartEntities);
			
			// Add the customer addresses
			List<ShoppingCartCustomerAddressEntity> addresses = (List<ShoppingCartCustomerAddressEntity>) testObjects.get("addresses");
			setCustomerAddressesToShoppingCart(shoppingCartId, addresses);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListShoppingCartTotals() {
		try {
			double totalPrice = (Double) testObjects.get("totalPrice");
			
			MessageProcessor flow = lookupFlowConstruct("list-shopping-cart-totals");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<ShoppingCartTotalsEntity> totals = (List<ShoppingCartTotalsEntity>) response.getMessage().getPayload();
			for (ShoppingCartTotalsEntity total : totals) {
				if (total.getTitle().equals("Subtotal")) {
					assertTrue(total.getAmount().doubleValue() == totalPrice);
				}
				else if (total.getTitle().equals("Grant Total")) {
					assertTrue(total.getAmount().doubleValue() == totalPrice);
				}
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
			HashMap<Integer, Double> productPrices = (HashMap<Integer, Double>) testObjects.get("productPrices");
			for (Map.Entry<Integer, Double> product : productPrices.entrySet()) {
				Integer productId = product.getKey();
				deleteProductById(productId);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
