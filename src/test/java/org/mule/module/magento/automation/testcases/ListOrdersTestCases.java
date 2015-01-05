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
import com.magento.api.SalesOrderListEntity;
import com.magento.api.ShoppingCartCustomerAddressEntity;
import com.magento.api.ShoppingCartCustomerEntity;
import com.magento.api.ShoppingCartPaymentMethodEntity;
import com.magento.api.ShoppingCartProductEntity;

public class ListOrdersTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("listOrders");
			List<Integer> productIds = new ArrayList<Integer>();
			List<String> orderIds = new ArrayList<String>();

			String storeId = testObjects.get("storeId").toString();
			List<HashMap<String, Object>> orderBeans = (List<HashMap<String, Object>>) testObjects.get("orderBeans");

			for (HashMap<String, Object> orderBean : orderBeans) {
				
				List<ShoppingCartProductEntity> cartProducts = new ArrayList<ShoppingCartProductEntity>();
				
				ShoppingCartCustomerEntity customer = (ShoppingCartCustomerEntity) orderBean.get("customer");
				List<ShoppingCartCustomerAddressEntity> addresses = (List<ShoppingCartCustomerAddressEntity>) orderBean.get("customerAddresses");
				String shippingMethod = orderBean.get("shippingMethod").toString();
				ShoppingCartPaymentMethodEntity paymentMethod = (ShoppingCartPaymentMethodEntity) orderBean.get("paymentMethod");
				
				List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) orderBean.get("products");
				for (HashMap<String, Object> product : products) {
					// Get the product data
					String productType = (String) product.get("type");
					int productSet = (Integer) product.get("set");
					String productSKU = (String) product.get("sku");
					CatalogProductCreateEntity attributes = (CatalogProductCreateEntity) product.get("attributesRef");
				
					// Create the product and get the product ID
					int productId = createProduct(productType, productSet, productSKU, attributes);
					productIds.add(productId);
					
					double qtyToPurchase = (Double) product.get("qtyToPurchase");
					
					ShoppingCartProductEntity cartProduct = new ShoppingCartProductEntity();
					cartProduct.setProduct_id(productId + "");
					cartProduct.setQty(qtyToPurchase);
					
					cartProducts.add(cartProduct);
				}
				
				testObjects.put("productIds", productIds);
				
				int quoteId = createShoppingCart(storeId);
				String orderId = createShoppingCartOrder(quoteId, customer, addresses, paymentMethod, shippingMethod, cartProducts);
				orderIds.add(orderId);
				testObjects.put("orderIds", orderIds);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListOrders_NoFilter() {
		try {
			List<String> orderIds = (List<String>) testObjects.get("orderIds");
			
			MessageProcessor flow = lookupFlowConstruct("list-orders-unfiltered");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<SalesOrderListEntity> orders = (List<SalesOrderListEntity>) response.getMessage().getPayload();
			assertTrue(orders.size() == orderIds.size());
			for (SalesOrderListEntity order : orders) {
				assertTrue(orderIds.contains(order.getIncrement_id()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListOrders_Pending() {
		try {
			List<String> orderIds = (List<String>) testObjects.get("orderIds");

			String filter = (String) testObjects.get("pendingOrdersFilter");
			testObjects.put("filter", filter);
			
			MessageProcessor flow = lookupFlowConstruct("list-orders");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<SalesOrderListEntity> orders = (List<SalesOrderListEntity>) response.getMessage().getPayload();
			assertTrue(orders.size() == orderIds.size());
			for (SalesOrderListEntity order : orders) {
				assertTrue(orderIds.contains(order.getIncrement_id()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListOrders_Cancelled() {
		try {
			String filter = (String) testObjects.get("cancelledOrdersFilter");
			testObjects.put("filter", filter);
						
			MessageProcessor flow = lookupFlowConstruct("list-orders");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<SalesOrderListEntity> orders = (List<SalesOrderListEntity>) response.getMessage().getPayload();
			assertTrue(orders.isEmpty());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListOrders_Completed() {
		try {
			String filter = (String) testObjects.get("completedOrdersFilter");
			testObjects.put("filter", filter);
			
			MessageProcessor flow = lookupFlowConstruct("list-orders");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<SalesOrderListEntity> orders = (List<SalesOrderListEntity>) response.getMessage().getPayload();
			assertTrue(orders.isEmpty());
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
			for (int productId : productIds) {
				deleteProductById(productId);
			}
			
			clearSalesTables();
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
