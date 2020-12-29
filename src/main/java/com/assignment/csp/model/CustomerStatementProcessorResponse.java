package com.assignment.csp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerStatementProcessorResponse {
	
	/**
	 * Property <em>result</em>
	 */
	private String result;
	
	/**
	 * Property <em>errorRecords</em>
	 */
	private List<ErrorRecord> errorRecords;

}
