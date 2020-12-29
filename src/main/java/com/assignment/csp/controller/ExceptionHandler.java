package com.assignment.csp.controller;

import com.assignment.csp.exception.CustomerStatementProcessorException;
import com.assignment.csp.model.CustomerStatementProcessorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

/**
 * @author Prachi Das
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerStatementProcessorException.class)
    public ResponseEntity<CustomerStatementProcessorResponse> handleCustomException(final CustomerStatementProcessorException exception) {
        final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
        response.setResult(exception.getMessage());
        response.setErrorRecords(exception.getErrorRecords());
        return ResponseEntity.ok(response);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<CustomerStatementProcessorResponse> handleException(final Exception exception) {
        final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
        response.setResult("INTERNAL_SERVER_ERROR");
        response.setErrorRecords(Collections.emptyList());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
        response.setResult("BAD_REQUEST");
        response.setErrorRecords(Collections.emptyList());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
