/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.magento.api.DirectoryRegionEntity;

public class ListDirectoryRegionsTestCases extends MagentoTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("listDirectoryRegions");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testListDirectoryRegions() {
		try {
			MessageProcessor flow = lookupFlowConstruct("list-directory-regions");
			MuleEvent response = flow.process(getTestEvent(testObjects));
		
			List<DirectoryRegionEntity> regions = (List<DirectoryRegionEntity>) response.getMessage().getPayload();
			assertNotNull(regions);
			for (DirectoryRegionEntity region : regions) {
				assertNotNull(region);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
		
}
