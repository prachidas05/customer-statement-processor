package com.assignment.csp.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Error record model class.
 *
 * @author Prachi Das
 */
@Getter
@Setter
public class ErrorRecord implements Serializable {

	private static final long serialVersionUID = -1161404820246694823L;

	private String reference;

	private String accountNumber;
}
