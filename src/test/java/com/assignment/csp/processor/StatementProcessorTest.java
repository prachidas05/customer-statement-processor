package com.assignment.csp.processor;

import com.assignment.csp.model.CustomerStatement;
import com.assignment.csp.model.CustomerStatementProcessorRequest;
import com.assignment.csp.util.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Prachi Das
 */
@SpringBootTest
public class StatementProcessorTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private final StatementProcessor statementProcessor = new StatementProcessor();

   @Test
    public void testGetDupilcates_ReturnsDuplicateRecords() throws IOException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-duplicate-ref-request.json"),CustomerStatementProcessorRequest.class);
        final Set<CustomerStatement> duplicates = statementProcessor.getDuplicates(request.getCustomerStatement());
        assertNotNull(duplicates);
        assertEquals(1,duplicates.size());
    }

    @Test
    public void testGetDupilcates_ReturnsNoDuplicateRecords() throws IOException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-valid-request.json"),CustomerStatementProcessorRequest.class);
        final Set<CustomerStatement> duplicates = statementProcessor.getDuplicates(request.getCustomerStatement());
        assertNotNull(duplicates);
        assertTrue(duplicates.isEmpty());
    }

    @Test
    public void testGetInvalidBalanceRecords_ReturnsRecordsWithInvalidEndBalance() throws IOException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-invalid-balance-request.json"),CustomerStatementProcessorRequest.class);
        final Set<CustomerStatement> invalidBalanceRecords = statementProcessor.getInvalidBalanceRecords(request.getCustomerStatement());
        assertNotNull(invalidBalanceRecords);
        assertEquals(3,invalidBalanceRecords.size());

    }
    @Test
    public void testGetInvalidBalanceRecords_ReturnsEmptyInvalidBalanceRecordSet() throws IOException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-valid-request.json"),CustomerStatementProcessorRequest.class);
        final Set<CustomerStatement> invalidBalanceRecords = statementProcessor.getInvalidBalanceRecords(request.getCustomerStatement());
        assertNotNull(invalidBalanceRecords);
        assertTrue(invalidBalanceRecords.isEmpty());
    }
}
