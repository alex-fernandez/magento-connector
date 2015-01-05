/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.api.catalog.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test for {@link ProductIdentifiers}
 * @author flbulgarelli
 */
public class ProductIdentifiersUnitTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testFromBothIds()
    {
        ProductIdentifiers.from("XAF4596", 100, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromNoIds()
    {
        ProductIdentifiers.from(null, null, null);
    }
    
    @Test
    public void testFromSku()
    {
        String sku = "FO4G896";
        ProductIdentifier id = ProductIdentifiers.from(sku, null, null);
        assertEquals("sku", id.getIdentifierType());
        assertEquals(sku, id.getIdentifierAsString());
    }
    
    @Test
    public void testFromId()
    {
        int id = 98602;
        ProductIdentifier identifier = ProductIdentifiers.from(null, id, null);
        assertEquals("id", identifier.getIdentifierType());
        assertEquals("98602", identifier.getIdentifierAsString());
    }
    
    @Test
    public void testFromIdOrSku()
    {
        String idOrSku = "98602";
        ProductIdentifier identifier = ProductIdentifiers.from(null, null, idOrSku);
        assertEquals(null, identifier.getIdentifierType());
        assertEquals("98602", identifier.getIdentifierAsString());
    }

}
