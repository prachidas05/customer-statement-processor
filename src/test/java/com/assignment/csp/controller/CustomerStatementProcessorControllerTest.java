package com.assignment.csp.controller;

import com.assignment.csp.exception.CustomerStatementProcessorException;
import com.assignment.csp.model.CustomerStatementProcessorRequest;
import com.assignment.csp.model.CustomerStatementProcessorResponse;
import com.assignment.csp.service.CustomerStatementValidator;
import com.assignment.csp.util.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.assignment.csp.util.TestUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Prachi Das
 */
@SpringBootTest
public class CustomerStatementProcessorControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private final CustomerStatementProcessorController controller = new CustomerStatementProcessorController();

    @Mock
    private CustomerStatementValidator customerStatementValidator;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ExceptionHandler.class).build();
    }

    @Test
    public void testResponse_returnsStatusOKForValidRequest()
            throws Exception, CustomerStatementProcessorException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), CustomerStatementProcessorRequest.class);
        final CustomerStatementProcessorResponse response = mapper.readValue(TestUtils.readFile("valid-response.json"), CustomerStatementProcessorResponse.class);
        when(customerStatementValidator.validate(any(CustomerStatementProcessorRequest.class))).thenReturn(response);
        mockMvc.perform(
                post("/customerStatementValidator").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(request)))
                .andExpect(status().isOk()).andExpect(content().string(asJsonString(response)));
        verify(customerStatementValidator, times(1)).validate(any(CustomerStatementProcessorRequest.class));
    }

    @Test
    public void testResponse_returnsStatusOKForIncorrectEndBalance()
            throws Exception, CustomerStatementProcessorException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-invalid-balance-request.json"), CustomerStatementProcessorRequest.class);
        final CustomerStatementProcessorResponse response = mapper.readValue(TestUtils.readFile("incorrect-end-balance-response.json"), CustomerStatementProcessorResponse.class);
        when(customerStatementValidator.validate(any(CustomerStatementProcessorRequest.class))).thenThrow(mockedException(response));

        mockMvc.perform(
                post("/customerStatementValidator").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(request)))
                .andExpect(status().isOk()).andExpect(content().string(asJsonString(response)));
        verify(customerStatementValidator, times(1)).validate(any(CustomerStatementProcessorRequest.class));
    }

    @Test
    public void testResponse_returnsStatusOKForDuplicateReference()
            throws Exception, CustomerStatementProcessorException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-duplicate-ref-request.json"), CustomerStatementProcessorRequest.class);
        final CustomerStatementProcessorResponse response = mapper.readValue(TestUtils.readFile("duplicate-ref-response.json"), CustomerStatementProcessorResponse.class);
        when(customerStatementValidator.validate(any(CustomerStatementProcessorRequest.class))).thenThrow(mockedException(response));

        mockMvc.perform(
                post("/customerStatementValidator").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(request)))
                .andExpect(status().isOk()).andExpect(content().string(asJsonString(response)));
        verify(customerStatementValidator, times(1)).validate(any(CustomerStatementProcessorRequest.class));
    }

    @Test
    public void testResponse_returnsStatusOKForDuplicateRefAndIncorrectBalance()
            throws Exception, CustomerStatementProcessorException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-duplicate-ref-request.json"), CustomerStatementProcessorRequest.class);
        final CustomerStatementProcessorResponse response = mapper.readValue(TestUtils.readFile("duplicate-ref-incorrect-balance-response.json"), CustomerStatementProcessorResponse.class);
        when(customerStatementValidator.validate(any(CustomerStatementProcessorRequest.class))).thenThrow(mockedException(response));

        mockMvc.perform(
                post("/customerStatementValidator").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(request)))
                .andExpect(status().isOk()).andExpect(content().string(asJsonString(response)));
        verify(customerStatementValidator, times(1)).validate(any(CustomerStatementProcessorRequest.class));
    }

    @Test
    public void testResponse_returnsStatus400forBadRequest()
            throws Exception, CustomerStatementProcessorException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-invalid-balance-request.json"), CustomerStatementProcessorRequest.class);
        request.getCustomerStatement().get(0).setTransactionRef(null);
        final CustomerStatementProcessorResponse response = mapper.readValue(TestUtils.readFile("bad-request-response.json"), CustomerStatementProcessorResponse.class);

        mockMvc.perform(
                post("/customerStatementValidator").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(request)))
                .andExpect(status().isBadRequest()).andExpect(content().string(asJsonString(response)));
    }

    @Test
    public void testResponse_returnsStatus500forInternalServerError()
            throws Exception, CustomerStatementProcessorException {
        final CustomerStatementProcessorRequest request = mapper.readValue(TestUtils.readFile("test-invalid-balance-request.json"), CustomerStatementProcessorRequest.class);
        final CustomerStatementProcessorResponse response = mapper.readValue(TestUtils.readFile("internal-server-error-response.json"), CustomerStatementProcessorResponse.class);
        ReflectionTestUtils.setField(controller,"customerStatementValidator",null);

        mockMvc.perform(
                post("/customerStatementValidator").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(request)))
                .andExpect(status().isInternalServerError()).andExpect(content().string(asJsonString(response)));
    }

    private CustomerStatementProcessorException mockedException(final CustomerStatementProcessorResponse response) {
        return new CustomerStatementProcessorException(response.getResult(), response.getErrorRecords());
    }

}
