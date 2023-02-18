package org.example.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Customer Info
 */
@Data
public class Customer {

    // customer id, private key
    private Long customerId;
    // customer name
    private String customerName;
    // customer create time
    private LocalDateTime createTime;
}
