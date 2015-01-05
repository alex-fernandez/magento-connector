/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import java.util.ArrayList;
import java.util.List;

import com.magento.api.CatalogProductEntity;
import com.magento.api.ShoppingCartProductEntity;

public class MagentoTestHelper {

	public static List<Integer> getIntegerList(List<String> stringList) {
		List<Integer> integerIds = new ArrayList<Integer>();
		
		for (String string : stringList) {
			integerIds.add(Integer.parseInt(string));
		}
		return integerIds;
	}
	
	public static List<String> getStringList(List<Integer> integerList) {
		List<String> stringList = new ArrayList<String>();
		
		for (Integer integer : integerList) {
			stringList.add(integer + "");
		}
		
		return stringList;
	}

	public static boolean isProductInShoppingCart(List<CatalogProductEntity> catalogProductEntities, ShoppingCartProductEntity shoppingCartProductEntity) {
		return isProductInShoppingCart(catalogProductEntities, shoppingCartProductEntity.getProduct_id());
	}
	
	public static boolean isProductInShoppingCart(List<CatalogProductEntity> catalogProductEntities, String productId) {
		for (CatalogProductEntity catalogProductEntity : catalogProductEntities) {
			if (catalogProductEntity.getProduct_id().equals(productId)) {
				return true;
			}
		}
		return false;
	}
	
}
