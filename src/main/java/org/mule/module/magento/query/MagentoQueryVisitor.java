/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.magento.query;
import java.util.List;

import org.mule.common.query.DefaultQueryVisitor;
import org.mule.common.query.Field;
import org.mule.common.query.expression.OperatorVisitor;
import org.mule.common.query.expression.Value;


public class MagentoQueryVisitor extends DefaultQueryVisitor {

	private StringBuilder nativeQuery = new StringBuilder();

	@Override
	public OperatorVisitor operatorVisitor() {
		return MagentoOperatorVisitor.INSTANCE;
	}
	
	@Override
	public void visitComparison(String operator, Field field, Value value) {
		Object v = value.getValue();
		
		if (v instanceof String) {
			v = String.format("'%s'", (String) v);
		}
		
		this.append(String.format(operator, field.getName(), v));
	}
	
	
	public void visitOrderByFields(List<Field> orderByFields) {
		for (Field field : orderByFields) {
			this.append(String.format("order(%s)", field.getName()));
		}
	}

	@Override
	public void visitLimit(int limit) {
		this.append(String.format("to(%d)", limit));
	}

	@Override
	public void visitOffset(int offset) {
		this.append(String.format("from(%d)", offset));
	}
	
	public String toQuery() {
		return this.nativeQuery.toString();
	}
	
	private void append(String value) {
		if (this.nativeQuery.length() > 0) {
			this.nativeQuery.append(", ");
		}
		
		this.nativeQuery.append(value);
	}

}
