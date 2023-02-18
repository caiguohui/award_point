package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.TransactionRecord;
import org.example.dto.TransactionSummary;
import org.example.exception.BizException;
import org.example.util.RewardPointsCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

/**
 * Transaction Service
 */
@Slf4j
@Service
public class TransactionService {

    /**
     * get transaction records
     *
     * @param days Query all records within the interval from today.
     * @return
     */
    public List<TransactionRecord> getTransactionRecords(int days) {
        log.debug("getTransactionRecords --> days: {}", days);
        // TODO Currently no database is used, the test data used
        log.warn("init transaction test data!!!");
        List<TransactionRecord> result = new ArrayList<>();
        Random random = new Random();
        LocalDateTime currentTime = LocalDateTime.now();
        long counter = 1;
        for (int i = 0; i < days; i++) { // make days of transaction records
            for (int j = 0; j < 100; j++) { // make 100 random records per day
                TransactionRecord record = new TransactionRecord();
                record.setTransactionId(counter++);
                record.setCustomerId(random.nextLong(1, 10));
                record.setTransactionAmount(random.nextDouble(1, 1000));
                record.setRewardPoints(RewardPointsCalculator.calculateRewardPoints(record.getTransactionAmount()));
                record.setTransactionTime(currentTime.minusDays(i));
                result.add(record);
            }
        }
        return result;
    }

    /**
     * Get day/month transaction summary information
     *
     * @param summaryType Get summary type, support by day/month
     * @param days        The number of days of the latest date that needs to be queried
     * @return Returns summary information for each day/month within a date
     */
    public List<TransactionSummary> getSummary(String summaryType, int days) {
        log.debug("getSummary --> summaryType: {}, days: {}", summaryType, days);
        if (!("day".equals(summaryType) || "month".equals(summaryType))) {
            log.warn("getSummary --> error. the parameter is invalid. summaryType: " + summaryType + ", days: " + days);
            throw new BizException(500, "The type parameter is wrong, only day or month is supported.");
        }
        Map<String, Map<Long, TransactionSummary>> summaryDateMap = new TreeMap<>();
        List<TransactionRecord> records = getTransactionRecords(days);
        for (TransactionRecord transactionRecord : records) {
            LocalDate date = transactionRecord.getTransactionTime().toLocalDate();
            if ("month".equals(summaryType)) date = date.with(DAY_OF_MONTH, 1);
            String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Map<Long, TransactionSummary> summaryCustomerMap = summaryDateMap.get(dateStr);
            if (summaryCustomerMap == null) { // Initialize if data does not exist
                summaryCustomerMap = new TreeMap<>();
                summaryDateMap.put(dateStr, summaryCustomerMap);
            }
            long customerId = transactionRecord.getCustomerId();
            TransactionSummary customerTransactionSummary = summaryCustomerMap.get(customerId);
            if (customerTransactionSummary == null) {
                customerTransactionSummary = new TransactionSummary();
                customerTransactionSummary.setCustomerId(customerId);
                customerTransactionSummary.setAmount(Double.valueOf(0));
                customerTransactionSummary.setRewardPoints(Double.valueOf(0));
                customerTransactionSummary.setDate(date);
                summaryCustomerMap.put(customerId, customerTransactionSummary);
            }
            customerTransactionSummary.setAmount(customerTransactionSummary.getAmount() + transactionRecord.getTransactionAmount());
            customerTransactionSummary.setRewardPoints(customerTransactionSummary.getRewardPoints() + transactionRecord.getRewardPoints());
        }
        List<TransactionSummary> result = new ArrayList<>();
        summaryDateMap.values().forEach(summaryDate -> summaryDate.values().forEach((item) -> result.add(item)));
        return result;
    }
}
