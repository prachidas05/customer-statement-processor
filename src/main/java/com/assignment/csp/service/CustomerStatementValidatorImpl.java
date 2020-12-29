package com.assignment.csp.service;

import com.assignment.csp.exception.CustomerStatementProcessorException;
import com.assignment.csp.model.CustomerStatement;
import com.assignment.csp.model.CustomerStatementProcessorRequest;
import com.assignment.csp.model.CustomerStatementProcessorResponse;
import com.assignment.csp.model.ErrorRecord;
import com.assignment.csp.processor.StatementProcessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.assignment.csp.ApplicationConstants.*;

/**
 * Implementation of {@link CustomerStatementValidator}.
 *
 * @author Prachi Das
 */
@Service
@Log4j2
public class CustomerStatementValidatorImpl implements CustomerStatementValidator {

    @Autowired
    private StatementProcessor statementProcessor;

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerStatementProcessorResponse validate(final CustomerStatementProcessorRequest request)
            throws CustomerStatementProcessorException {
        log.debug("Validating customer statement");
        final Set<CustomerStatement> duplicateRecords = statementProcessor.getDuplicates(request.getCustomerStatement());
        final Set<CustomerStatement> invalidBalanceRecords = statementProcessor.getInvalidBalanceRecords(request.getCustomerStatement());
        if (CollectionUtils.isEmpty(duplicateRecords) && CollectionUtils.isEmpty(invalidBalanceRecords)) {
            log.debug("Statement is valid");
            final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
            response.setResult(SUCCESSFUL);
            response.setErrorRecords(Collections.emptyList());
            return response;
        } else if (!CollectionUtils.isEmpty(duplicateRecords) && !CollectionUtils.isEmpty(invalidBalanceRecords)) {
            log.debug("Found {} duplicate records and {} invalid end balance records", duplicateRecords.size(), invalidBalanceRecords.size());
            final List<ErrorRecord> errorRecords = createErrorRecords(duplicateRecords);
            errorRecords.addAll(createErrorRecords(invalidBalanceRecords));
            throw new CustomerStatementProcessorException(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, errorRecords);
        } else if (!CollectionUtils.isEmpty(duplicateRecords)) {
            log.debug("Found {} duplicate records", duplicateRecords.size());
            final List<ErrorRecord> errorRecords = createErrorRecords(duplicateRecords);
            throw new CustomerStatementProcessorException(DUPLICATE_REFERENCE, errorRecords);
        } else {
            log.debug("Found {} invalid end balance records", invalidBalanceRecords.size());
            final List<ErrorRecord> errorRecords = createErrorRecords(invalidBalanceRecords);
            throw new CustomerStatementProcessorException(INCORRECT_END_BALANCE, errorRecords);
        }
    }

    private List<ErrorRecord> createErrorRecords(final Set<CustomerStatement> customerStatements) {
        final List<ErrorRecord> errorRecords = new ArrayList<>();
        for (final CustomerStatement statement : customerStatements) {
            final ErrorRecord record = new ErrorRecord();
            record.setAccountNumber(statement.getIban());
            record.setReference(statement.getTransactionRef());
            errorRecords.add(record);
        }
        return errorRecords;
    }

}
