package com.assignment.csp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
public class CustomerStatement {

	@NotNull
	private String transactionRef;

	@NotNull
	private String iban;

	/**
	 * Property startBalance. startBalance may contain , (comma) separator. e.g: 9762,25
	 */

	private String startBalance;

	/**
	 * Property mutation. mutation may contain , (comma) separator. e.g: 9762,25
	 */

	private String mutation;

	private String description;

	/**
	 * Property endBalance. endBalance may contain , (comma) separator. e.g: 9762,25
	 */

	private String endBalance;

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof CustomerStatement) {
			final CustomerStatement stmt = (CustomerStatement) obj;
			return getTransactionRef().equals(stmt.getTransactionRef());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getTransactionRef());
	}

}
