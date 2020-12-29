package com.assignment.csp.processor;

import com.assignment.csp.model.CustomerStatement;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is customer statement processor which contains miscellaneous methods to process customer statement.
 *
 * @author Prachi Das
 */
@Component
public class StatementProcessor {


    /**
     * This method finds and returns duplicate transactions from passed statement.
     *
     * @param customerStatements list of {@link CustomerStatement}
     * @return set of duplicate {@link CustomerStatement}s
     */
    public Set<CustomerStatement> getDuplicates(final List<CustomerStatement> customerStatements) {
        return customerStatements.stream()
                .collect(Collectors.groupingBy(CustomerStatement::getTransactionRef)).entrySet().stream().filter(statement -> statement.getValue().size() > 1).flatMap(d -> d.getValue().stream()).collect(Collectors.toSet());
    }


    /**
     * This method finds and returns invalid end balance records from passed statement.
     *
     * @param customerStatements list of {@link CustomerStatement}
     * @return set of invalid balance {@link CustomerStatement}s
     */
    public Set<CustomerStatement> getInvalidBalanceRecords(final List<CustomerStatement> customerStatements) {
        return customerStatements.stream().map(this::validateBalance).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    private CustomerStatement validateBalance(final CustomerStatement stmt) {
        if (!getAmount(stmt.getStartBalance()).add(getAmount(stmt.getMutation())).equals(getAmount(stmt.getEndBalance()))) {
            return stmt;
        } else {
            return null;
        }
    }

    private BigDecimal getAmount(final String balance) {
        return new BigDecimal(balance.replaceAll("[\\.]", "").replace(',', '.'));
    }

}
