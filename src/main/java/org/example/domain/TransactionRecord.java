package org.example.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Transaction Record
 */
@Data
public class TransactionRecord {

    // transaction id, private key
    private Long transactionId;
    // customer id
    private Long customerId;
    // transaction amount
    private Double transactionAmount;
    // transaction time
    private LocalDateTime transactionTime;
    // reward points
    private Integer rewardPoints;

}
