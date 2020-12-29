package com.assignment.csp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Model class for customer statement processor request.
 * Request will look like:
 <pre>
 {
	 "customerStatement": [
 		{
		 "transactionRef": "1286",
		 "iban": "NL23ABNA0897000078",
		 "startBalance": "8500,00",
		 "mutation": "-100,00",
		 "description": "jan days",
		 "endBalance": "8400,00"
 		}
 	]
 }
 </pre>
 * @author Prachi Das
 */
@Getter
@Setter
public class CustomerStatementProcessorRequest {

	/**
	 * Property <em>customerStatements</em>
	 */
	@NotNull
	@Valid
	private List<CustomerStatement> customerStatement;
}
