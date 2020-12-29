package com.assignment.csp.service;

import com.assignment.csp.exception.CustomerStatementProcessorException;
import com.assignment.csp.model.CustomerStatementProcessorRequest;
import com.assignment.csp.model.CustomerStatementProcessorResponse;

/**
 * Customer statement validator service interface.
 *
 * @author Prachi Das
 */
public interface CustomerStatementValidator {

    /**
     * This method validates customer statement for duplicate reference ids and valid end balance or both.
     * @param request {@link CustomerStatementProcessorRequest} instance
     * @return CustomerStatementProcessorResponse instance
     * @throws CustomerStatementProcessorException if data is invalid
     */
    CustomerStatementProcessorResponse validate(final CustomerStatementProcessorRequest request) throws CustomerStatementProcessorException;

}
