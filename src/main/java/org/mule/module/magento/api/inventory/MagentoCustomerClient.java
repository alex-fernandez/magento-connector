/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.api.inventory;

import com.magento.api.*;

import java.util.List;

import javax.validation.constraints.NotNull;

public interface MagentoCustomerClient<ExceptionType extends Exception>
{

    /**
     * Creates a customer with th given attributes
     * 
     * @param customerToCreate the attributes of the new customer
     * @return the new customer id
     * 
     */
    int createCustomer(@NotNull CustomerCustomerEntityToCreate customerToCreate) throws ExceptionType;

    /**
     * Deletes a customer given its id
     * 
     * @param customerId
     * @return if the customer is deleted
     */
    boolean deleteCustomer(int customerId) throws ExceptionType;

    /**
     * Answers customer attributes for the given id. Only the selected attributes are
     * retrieved
     * 
     *
     * @param customerId
     * @param attributeNames the attributes to retrieve. Not empty
     * @return the attributes map
     * 
     */
    @NotNull
    CustomerCustomerEntity getCustomer(int customerId, @NotNull List<String> attributeNames) throws ExceptionType;

    /**
     * Answers a list of customer attributes for the given filter expression.
     * 
     *
     * @param filters a filtering expression.
     * @return the attributes map
     * 
     */
    @NotNull
    List<CustomerCustomerEntity> listCustomers(String filters) throws ExceptionType;

    /**
     * Updates the given customer attributes, for the given customer id. Password can
     * not be updated using this method
     *
     * @param customerId
     * @param attributes the attributes map
     * 
     */
    boolean updateCustomer(int customerId, @NotNull CustomerCustomerEntityToCreate attributes) throws ExceptionType;

    /**
     * Creates a new address for the given customer using the given address
     * attributes
     * 
     * @param customerId
     * @param attributes
     * @return a new customer address id
     * 
     */
    int createCustomerAddress(int customerId, @NotNull CustomerAddressEntityCreate attributes) throws ExceptionType;

    /**
     * Deletes a Customer Address
     * 
     * @param addressId
     * @return if the customer address is deleted
     */
    boolean deleteCustomerAddress(int addressId) throws ExceptionType;

    /**
     * Answers the customer address attributes
     *
     * @param addressId
     * @return the customer address attributes
     * 
     */
    CustomerAddressEntityItem getCustomerAddress(int addressId) throws ExceptionType;

    /**
     * Lists the customer address for a given customer id
     * 
     *
     * @param customerId the id of the customer
     * @return a listing of addresses
     */
    List<CustomerAddressEntityItem> listCustomerAddresses(int customerId) throws ExceptionType;

    /**
     * Updates the given map of customer address attributes, for the given customer address
     *
     * @param addressId the customer address to update
     * @param attributes  the address attributes to update
     */
    boolean updateCustomerAddress(int addressId, @NotNull CustomerAddressEntityCreate attributes) throws ExceptionType;

    /**
     * Lists all the customer groups
     * 
     * @return a listing of groups attributes
     */
    List<CustomerGroupEntity> listCustomerGroups() throws ExceptionType;

}
