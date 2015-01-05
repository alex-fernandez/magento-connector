--
-- Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
--
-- The software in this package is published under the terms of the CPAL v1.0
-- license, a copy of which has been included with this distribution in the
-- LICENSE.md file.
--

DELIMITER $$

CREATE PROCEDURE `magento`.`truncate_sales` ()
BEGIN
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `sales_bestsellers_aggregated_daily`;
TRUNCATE TABLE `sales_bestsellers_aggregated_monthly`;
TRUNCATE TABLE `sales_bestsellers_aggregated_yearly`;
TRUNCATE TABLE `sales_billing_agreement`;
TRUNCATE TABLE `sales_billing_agreement_order`;
TRUNCATE TABLE `sales_flat_creditmemo`;
TRUNCATE TABLE `sales_flat_creditmemo_comment`;
TRUNCATE TABLE `sales_flat_creditmemo_grid`;
TRUNCATE TABLE `sales_flat_creditmemo_item`;
TRUNCATE TABLE `sales_flat_invoice`;
TRUNCATE TABLE `sales_flat_invoice_comment`;
TRUNCATE TABLE `sales_flat_invoice_grid`;
TRUNCATE TABLE `sales_flat_invoice_item`;
TRUNCATE TABLE `sales_flat_order`;
TRUNCATE TABLE `sales_flat_order_address`;
TRUNCATE TABLE `sales_flat_order_grid`;
TRUNCATE TABLE `sales_flat_order_item`;
TRUNCATE TABLE `sales_flat_order_payment`;
TRUNCATE TABLE `sales_flat_order_status_history`;
TRUNCATE TABLE `sales_flat_quote`;
TRUNCATE TABLE `sales_flat_quote_address`;
TRUNCATE TABLE `sales_flat_quote_address_item`;
TRUNCATE TABLE `sales_flat_quote_item`;
TRUNCATE TABLE `sales_flat_quote_item_option`;
TRUNCATE TABLE `sales_flat_quote_payment`;
TRUNCATE TABLE `sales_flat_quote_shipping_rate`;
TRUNCATE TABLE `sales_flat_shipment`;
TRUNCATE TABLE `sales_flat_shipment_comment`;
TRUNCATE TABLE `sales_flat_shipment_grid`;
TRUNCATE TABLE `sales_flat_shipment_item`;
TRUNCATE TABLE `sales_flat_shipment_track`;
TRUNCATE TABLE `sales_invoiced_aggregated`;
TRUNCATE TABLE `sales_invoiced_aggregated_order`;
TRUNCATE TABLE `sales_order_aggregated_created`;
TRUNCATE TABLE `sales_order_tax`;
TRUNCATE TABLE `sales_payment_transaction`;
TRUNCATE TABLE `sales_recurring_profile`;
TRUNCATE TABLE `sales_recurring_profile_order`;
TRUNCATE TABLE `sales_refunded_aggregated`;
TRUNCATE TABLE `sales_refunded_aggregated_order`;
TRUNCATE TABLE `sales_shipping_aggregated`;
TRUNCATE TABLE `sales_shipping_aggregated_order`;
SET FOREIGN_KEY_CHECKS = 1;
END