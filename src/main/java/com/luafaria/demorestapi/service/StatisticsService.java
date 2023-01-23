package com.luafaria.demorestapi.service;

import com.luafaria.demorestapi.exception.ResourceNotFoundException;
import com.luafaria.demorestapi.model.Statistics;
import com.luafaria.demorestapi.model.Transaction;
import com.luafaria.demorestapi.repository.TransactionItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableMongoRepositories(basePackages = "com.luanafaria.repository")
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    private final MongoTemplate mongoTemplate;

    public Transaction insertTransaction(final Transaction transaction) {
        log.info("Inserting new transaction: amount {}", transaction.getAmount());
        try {
            mongoTemplate.save(
                    TransactionItem.builder()
                            .amount(transaction.getAmount())
                            .timestamp(LocalDateTime.now())
                            .build(), "transaction");
        } catch (Exception e) {
            log.error("Error while inserting new transaction: amount {}. Exception: {}", transaction.getAmount(), e.getMessage());
        }
        return transaction;
    }

    public void deleteTransactions() {
        log.info("Deleting all transactions");
        try {
            mongoTemplate.dropCollection("transaction");
        } catch (Exception e) {
            log.error("Error while deleting all transactions. Exception: {}", e.getMessage());
        }
    }

    public Statistics getStatistics() {
        Query query = new Query();
        log.info("Getting Statistics from Transactions");
        List<TransactionItem> transactions;
        try {
            transactions = mongoTemplate.find(query, TransactionItem.class);
        } catch (Exception e) {
            log.error("Error while getting statistics from last 60 seconds transactions. Exception: {}", e.getMessage());
               throw new ResourceNotFoundException("Statistics");
        }
        List<BigDecimal> amounts = new ArrayList<>();
        transactions.forEach(transaction -> amounts.add(transaction.getAmount()));

        BigDecimalSummaryStatistics stats =
                amounts.stream().collect(Collectors2.summarizingBigDecimal(each -> each));

        return Statistics.builder()
                .sum(stats.getSum())
                .max(stats.getMax())
                .avg(stats.getAverage())
                .min(stats.getMin())
                .count(stats.getCount())
                .build();
    }
}
