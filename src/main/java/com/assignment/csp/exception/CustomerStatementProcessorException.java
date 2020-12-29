package com.assignment.csp.exception;

import com.assignment.csp.model.ErrorRecord;
import lombok.Getter;

import java.util.List;

/**
 * Customer statement processor exception class.
 *
 * @author Prachi Das
 */
@Getter
public class CustomerStatementProcessorException extends Exception {

    private static final long serialVersionUID = -5069613560102103106L;

    private final String message;

    private final List<ErrorRecord> errorRecords;

    /**
     * {@link CustomerStatementProcessorException} constructor.
     *
     * @param message      exception message
     * @param errorRecords list of {@link ErrorRecord}s
     */
    public CustomerStatementProcessorException(final String message, final List<ErrorRecord> errorRecords) {
        this.message = message;
        this.errorRecords = errorRecords;
    }
}
