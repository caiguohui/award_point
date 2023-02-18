package org.example.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * Transaction Summary
 */
@Data
public class TransactionSummary {

    // customer id
    private Long customerId;
    // summary amount
    private Double amount;
    // summary reward points
    private Double rewardPoints;
    // summary date
    private LocalDate date;
}
