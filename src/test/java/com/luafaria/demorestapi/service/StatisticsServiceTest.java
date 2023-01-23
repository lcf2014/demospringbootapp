package com.luafaria.demorestapi.service;

import com.luafaria.demorestapi.model.Statistics;
import com.luafaria.demorestapi.model.Transaction;
import com.luafaria.demorestapi.repository.TransactionItem;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class StatisticsServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;
    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void insertTransaction() {
        var amount = BigDecimal.valueOf(5.7);
        var timestamp = LocalDateTime.now();
        Transaction transaction = Transaction.builder().amount(amount).build();
        TransactionItem transactionItem = TransactionItem.builder()
                .amount(amount)
                .timestamp(timestamp)
                .build();
        when(mongoTemplate.save(transactionItem, "transaction")).thenReturn(transactionItem);
        var actualTransactionItem = statisticsService.insertTransaction(transaction);
        assertEquals(actualTransactionItem.getAmount(), transaction.getAmount());

    }

    @Test
    void getStatistics() {
        var amount = BigDecimal.valueOf(5.7);
        var timestamp = LocalDateTime.now();
        TransactionItem transactionItem = TransactionItem.builder()
                .amount(amount)
                .timestamp(timestamp)
                .build();
        when(mongoTemplate.find(any(), any())).thenReturn(Collections.singletonList(transactionItem));
        Statistics statistics = statisticsService.getStatistics();

        assertEquals(amount, statistics.getSum());
        assertEquals(amount, statistics.getAvg());
        assertEquals(amount, statistics.getMin());
        assertEquals(amount, statistics.getMax());
        assertEquals(1, statistics.getCount());
    }

    @Test
    void deleteStatistics() {
        statisticsService.deleteTransactions();
        verify(mongoTemplate, times(1)).dropCollection("transaction");
    }
}