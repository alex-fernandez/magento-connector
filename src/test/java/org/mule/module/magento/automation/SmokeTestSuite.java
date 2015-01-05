/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.magento.automation.testcases.AddCategoryProductTestCases;
import org.mule.module.magento.automation.testcases.AddOrderShipmentTrackTestCases;
import org.mule.module.magento.automation.testcases.AddProductLinkTestCases;
import org.mule.module.magento.automation.testcases.AddShoppingCartCouponTestCases;
import org.mule.module.magento.automation.testcases.AddShoppingCartProductTestCases;
import org.mule.module.magento.automation.testcases.CreateCategoryTestCases;
import org.mule.module.magento.automation.testcases.CreateCustomerAddressTestCases;
import org.mule.module.magento.automation.testcases.CreateCustomerTestCases;
import org.mule.module.magento.automation.testcases.CreateOrderInvoiceTestCases;
import org.mule.module.magento.automation.testcases.CreateOrderShipmentTestCases;
import org.mule.module.magento.automation.testcases.CreateProductAttributeMediaTestCases;
import org.mule.module.magento.automation.testcases.CreateProductTestCases;
import org.mule.module.magento.automation.testcases.CreateShoppingCartOrderTestCases;
import org.mule.module.magento.automation.testcases.CreateShoppingCartTestCases;
import org.mule.module.magento.automation.testcases.DeleteCategoryTestCases;
import org.mule.module.magento.automation.testcases.DeleteCustomerAddressTestCases;
import org.mule.module.magento.automation.testcases.DeleteCustomerTestCases;
import org.mule.module.magento.automation.testcases.DeleteProductTestCases;
import org.mule.module.magento.automation.testcases.GetCategoryTestCases;
import org.mule.module.magento.automation.testcases.HoldOrderTestCases;
import org.mule.module.magento.automation.testcases.ListCategoryAttributesTestCases;
import org.mule.module.magento.automation.testcases.ListCustomerGroupsTestCases;
import org.mule.module.magento.automation.testcases.ListProductAttributesTestCases;
import org.mule.module.magento.automation.testcases.ListProductLinkTestCases;
import org.mule.module.magento.automation.testcases.ListShoppingCartProductsTestCases;
import org.mule.module.magento.automation.testcases.RegressionTests;
import org.mule.module.magento.automation.testcases.SetShoppingCartCustomerAddressesTestCases;
import org.mule.module.magento.automation.testcases.SetShoppingCartCustomerTestCases;
import org.mule.module.magento.automation.testcases.SetShoppingCartPaymentMethodTestCases;
import org.mule.module.magento.automation.testcases.SetShoppingCartShippingMethodTestCases;

@RunWith(Categories.class)
@IncludeCategory(RegressionTests.class)
@SuiteClasses({
	AddCategoryProductTestCases.class,
	AddOrderShipmentTrackTestCases.class,
	AddProductLinkTestCases.class,
	AddShoppingCartCouponTestCases.class,
	AddShoppingCartProductTestCases.class,
	CreateCategoryTestCases.class,
	CreateCustomerAddressTestCases.class,
	CreateCustomerTestCases.class,
	CreateOrderInvoiceTestCases.class,
	CreateOrderShipmentTestCases.class,
	CreateProductAttributeMediaTestCases.class,
	CreateProductTestCases.class,
	CreateShoppingCartOrderTestCases.class,
	CreateShoppingCartTestCases.class,
	DeleteCategoryTestCases.class,
	DeleteCustomerAddressTestCases.class,
	DeleteCustomerTestCases.class,
	DeleteProductTestCases.class,
	GetCategoryTestCases.class,
	HoldOrderTestCases.class,
	ListCategoryAttributesTestCases.class,
	ListCustomerGroupsTestCases.class,
	ListProductAttributesTestCases.class,
	ListProductLinkTestCases.class,
	ListShoppingCartProductsTestCases.class,
	SetShoppingCartCustomerAddressesTestCases.class,
	SetShoppingCartCustomerTestCases.class,
	SetShoppingCartPaymentMethodTestCases.class,
	SetShoppingCartShippingMethodTestCases.class,
})
public class SmokeTestSuite {

}
