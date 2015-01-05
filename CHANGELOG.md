Magento Connector Release Notes
===============================

Date: 12-MAY-2014

Version: 2.1.2

Supported Mule Runtime Versions
-------------------------------
- Mule EE 3.5.0

Magento Supported Versions
--------------------------
- Magento CE 1.7.0.2
- Magento SOAP v2 API

New Features and Functionality
------------------------------
- Upgraded to Devkit 3.5.0
- Added Query Support
- Supported Operations
	- addCategoryProduct
	- addOrderComment
	- addOrderInvoiceComment
	- addOrderShipmentComment
	- addOrderShipmentTrack
	- addProductLink
	- addShoppingCartCoupon
	- addShoppingCartProduct
	- cancelOrderInvoice
	- cancelOrder
	- captureOrderInvoice
	- createCategory
	- createCustomerAddress
	- createCustomer
	- createOrderInvoice
	- createOrderShipment
	- createProductAttributeMedia
	- createProduct
	- createShoppingCartOrder
	- createShoppingCart
	- deleteCategoryProduct
	- deleteCategory
	- deleteCustomerAddress
	- deleteCustomer
	- deleteOrderShipmentTrack
	- deleteProductAttributeMedia
	- deleteProductLink
	- deleteProduct
	- getCatalogCurrentStoreView
	- getCategory
	- getCategoryTree
	- getCustomerAddress
	- getCustomer
	- getInfoShoppingCart
	- getOrderInvoice
	- getOrderShipmentCarriers
	- getOrderShipment
	- getOrder
	- getProductAttributeMedia
	- getProductSpecialPrice
	- getProduct
	- holdOrder
	- listCategoryAttributeOptions
	- listCategoryAttributes
	- listCategoryLevels
	- listCategoryProducts
	- listCustomerAddresses
	- listCustomerGroups
	- listCustomers
	- listDirectoryCountries
	- listDirectoryRegions
	- listInventoryStockItems
	- listOrdersInvoices
	- listOrdersShipments
	- listOrders
	- listProductAttributeMedia
	- listProductAttributeMediaTypes
	- listProductAttributeOptions
	- listProductAttributeSets
	- listProductAttributes
	- listProductAttributeTierPrices
	- listProductLinkAttributes
	- listProductLink
	- listProductLinkTypes
	- listProducts
	- listProductTypes
	- listShoppingCartLicenses
	- listShoppingCartPaymentMethods
	- listShoppingCartProducts
	- listShoppingCartShippingMethods
	- listShoppingCartTotals
	- listStockItems
	- moveCategory
	- removeShoppingCartCoupon
	- removeShoppingCartProduct
	- setShoppingCartCustomerAddresses
	- setShoppingCartCustomer
	- setShoppingCartPaymentMethod
	- setShoppingCartShippingMethod
	- unholdOrder
	- updateCategoryAttributeStoreView
	- updateCategoryProduct
	- updateCategory
	- updateCustomerAddress
	- updateCustomer
	- updateInventoryStockItem
	- updateProductAttributeMedia
	- updateProductAttributeTierPrice
	- updateProductLink
	- updateProductSpecialPrice
	- updateProduct
	- updateShoppingCartProduct
	- updateStockItem
	- voidOrderInvoice

Closed Issues in this release
------------------------------
N/A

Known Issues
------------
N/A



Earlier Release Notes
=====================

1.8
===
- Refactor operations for returning objects. Removing "void" operations.

1.7
===
- Migration to 3.3.1
- Added support for full shopping cart API
- Bug fixing
