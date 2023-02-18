package org.example.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.example.domain.Customer;
import org.example.domain.TransactionRecord;
import org.example.dto.TransactionSummary;
import org.example.service.CustomerService;
import org.example.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * REST API
 */
@RestController
@RequestMapping
public class RestApiController {

    @Resource
    private CustomerService customerService;
    @Resource
    private TransactionService transactionService;

    @GetMapping("/customer/list")
    @Operation(summary = "Get customer list", description = "Get a list of all customer information")
    public Collection<Customer> getCustomers() {
        return customerService.getList();
    }

    @GetMapping("/transaction/list")
    @Operation(summary = "Get transaction records", description = "According to the date range of the query, get the transaction records")
    public List<TransactionRecord> getTransactionRecords(@Parameter(description = "Query all records within the interval from today. must be a positive integer.") int days) {
        return transactionService.getTransactionRecords(days);
    }

    @Operation(summary = "Get transaction summary", description = "According to the summary type, get the transaction summary")
    @GetMapping("/transaction/summary")
    public List<TransactionSummary> getTransactionSummary(@Parameter(description = "Get summary type, support by day/month") String summaryType, @Parameter(description = "The number of days of the latest date that needs to be queried") int days) {
        return transactionService.getSummary(summaryType, days);
    }
}
