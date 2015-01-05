/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.query;
import org.mule.common.query.DefaultOperatorVisitor;


public class MagentoOperatorVisitor extends DefaultOperatorVisitor {
	
	public static final MagentoOperatorVisitor INSTANCE = new MagentoOperatorVisitor();
	
	private MagentoOperatorVisitor(){}

	@Override
	public String equalsOperator() {
		return "eq(%s,%s)";
	}

	@Override
	public String greaterOperator() {
		return "gt(%s,%s)";
	}

	@Override
	public String greaterOrEqualsOperator() {
		return "gteq(%s,%s)";
	}

	@Override
	public String lessOperator() {
		return "lt(%s,%s)";
	}

	@Override
	public String lessOrEqualsOperator() {
		return "lteq(%s,%s)";
	}

	@Override
	public String likeOperator() {
		return "like(%s,%s)";
	}

	@Override
	public String notEqualsOperator() {
		return "neq(%s,%s)";
	}
}
