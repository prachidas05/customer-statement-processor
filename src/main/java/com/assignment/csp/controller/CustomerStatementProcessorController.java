package com.assignment.csp.controller;

import com.assignment.csp.exception.CustomerStatementProcessorException;
import com.assignment.csp.model.CustomerStatementProcessorRequest;
import com.assignment.csp.model.CustomerStatementProcessorResponse;
import com.assignment.csp.service.CustomerStatementValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * This is controller class for customer statement validator/processor.
 * @author Prachi Das
 */
@RestController
@Log4j2
public class CustomerStatementProcessorController {

    @Autowired
    private CustomerStatementValidator customerStatementValidator;

    /**
     * Retrieve validation output from Customer Statement json
     *
     * @param request {@link CustomerStatementProcessorRequest} instance
     * @return response {@link CustomerStatementProcessorResponse} containing response code/message and error data if applicable
     * @throws CustomerStatementProcessorException if data validation fails
     */
    @PostMapping(value = "/customerStatementValidator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerStatementProcessorResponse> response(
            @Valid @RequestBody final CustomerStatementProcessorRequest request)
            throws CustomerStatementProcessorException {
        log.debug("Calling customer statement validator service ");
        final CustomerStatementProcessorResponse response = customerStatementValidator.validate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
