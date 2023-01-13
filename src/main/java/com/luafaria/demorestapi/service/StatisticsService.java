package com.luafaria.demorestapi.service;

import com.luafaria.demorestapi.model.Statistics;
import com.luafaria.demorestapi.model.Transaction;
import com.luafaria.demorestapi.repository.TransactionItem;
import lombok.RequiredArgsConstructor;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableMongoRepositories(basePackages = "com.luanafaria.repository")
@RequiredArgsConstructor
public class StatisticsService {

    private final MongoTemplate mongoTemplate;

    public Transaction insertTransaction(final Transaction transaction) {
        mongoTemplate.save(
                TransactionItem.builder()
                        .amount(transaction.getAmount())
                        .timestamp(transaction.getTimestamp())
                        .build(), "transaction");
        return transaction;
    }

    public void deleteTransactions() {
        mongoTemplate.dropCollection("transaction");
    }

    public Statistics getStatistics() {
        Query query = new Query();
        List<TransactionItem> transactions = mongoTemplate.find(query, TransactionItem.class);
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
