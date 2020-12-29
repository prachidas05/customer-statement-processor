package com.assignment.csp.service;

import com.assignment.csp.exception.CustomerStatementProcessorException;
import com.assignment.csp.model.CustomerStatement;
import com.assignment.csp.model.CustomerStatementProcessorRequest;
import com.assignment.csp.model.CustomerStatementProcessorResponse;
import com.assignment.csp.processor.StatementProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerStatementValidatorImplTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private final CustomerStatementValidator cspService = new CustomerStatementValidatorImpl();

    @Mock
    private StatementProcessor statementProcessor;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidate_returnsSuccessfulResponse() throws CustomerStatementProcessorException {
        final CustomerStatementProcessorRequest request = new CustomerStatementProcessorRequest();
        when(statementProcessor.getDuplicates(anyList())).thenReturn(new HashSet<>());
        when(statementProcessor.getInvalidBalanceRecords(anyList())).thenReturn(new HashSet<>());
        final CustomerStatementProcessorResponse response = cspService.validate(request);
        assertNotNull(response);
        assertEquals("SUCCESSFUL", response.getResult());
        assertTrue(response.getErrorRecords().isEmpty());
    }

    @Test
    public void testValidate_returnsDuplicateReferenceAndIncorrectBalanceErrorList() {
        final CustomerStatementProcessorRequest request = new CustomerStatementProcessorRequest();
        when(statementProcessor.getDuplicates(anyList())).thenReturn(mockedDuplicates(3));
        when(statementProcessor.getInvalidBalanceRecords(anyList())).thenReturn(mockedInvalidBalance(2));
        try {
            final CustomerStatementProcessorResponse response = cspService.validate(request);
        } catch (final CustomerStatementProcessorException e) {
            assertNotNull(e.getMessage());
            assertEquals("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE", e.getMessage());
            assertNotNull(e.getErrorRecords());
            assertEquals(5, e.getErrorRecords().size());
        }
    }

    @Test
    public void testValidate_returnsDuplicateReferenceErrorList() {
        final CustomerStatementProcessorRequest request = new CustomerStatementProcessorRequest();
        when(statementProcessor.getDuplicates(anyList())).thenReturn(mockedDuplicates(3));
        when(statementProcessor.getInvalidBalanceRecords(anyList())).thenReturn(new HashSet<>());
        try {
            final CustomerStatementProcessorResponse response = cspService.validate(request);
        } catch (final CustomerStatementProcessorException e) {
            assertNotNull(e.getMessage());
            assertEquals("DUPLICATE_REFERENCE", e.getMessage());
            assertNotNull(e.getErrorRecords());
            assertEquals(3, e.getErrorRecords().size());
        }
    }

    @Test
    public void testValidate_returnsIncorrectBalanceErrorList() {
        final CustomerStatementProcessorRequest request = new CustomerStatementProcessorRequest();
        when(statementProcessor.getDuplicates(anyList())).thenReturn(new HashSet<>());
        when(statementProcessor.getInvalidBalanceRecords(anyList())).thenReturn(mockedInvalidBalance(2));
        try {
            final CustomerStatementProcessorResponse response = cspService.validate(request);
        } catch (final CustomerStatementProcessorException e) {
            assertNotNull(e.getMessage());
            assertEquals("INCORRECT_END_BALANCE", e.getMessage());
            assertNotNull(e.getErrorRecords());
            assertEquals(2, e.getErrorRecords().size());
        }
    }

    private Set<CustomerStatement> mockedInvalidBalance(final int number) {
        final Set<CustomerStatement> duplicates = new HashSet<>();
        for (int i = 0; i < number; i++) {
            final CustomerStatement statement = new CustomerStatement();
            statement.setTransactionRef(String.valueOf(i));
            statement.setIban("NL40ABNA012345678");
            statement.setStartBalance("1000,00");
            statement.setMutation("100,00");
            statement.setEndBalance("900,00");
            duplicates.add(statement);
        }
        return duplicates;
    }

    private Set<CustomerStatement> mockedDuplicates(final int number) {
        final Set<CustomerStatement> duplicates = new HashSet<>();
        for (int i = 0; i < number; i++) {
            final CustomerStatement statement = new CustomerStatement();
            statement.setTransactionRef(String.valueOf(i));
            statement.setIban("NL40ABNA012345678");
            statement.setStartBalance("1000,00");
            statement.setMutation("-100,00");
            statement.setEndBalance("900,00");
            duplicates.add(statement);
        }
        return duplicates;
    }

}


