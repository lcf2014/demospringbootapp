package com.luafaria.demorestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luafaria.demorestapi.model.Transaction;
import com.luafaria.demorestapi.repository.TransactionItem;
import com.luafaria.demorestapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class StatisticsControllerTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MockMvc mvc;

    @BeforeAll
    void setup() {

        TransactionItem transaction1 =
                TransactionItem.builder()
                        .amount(BigDecimal.valueOf(10.0))
                        .timestamp(LocalDateTime.now())
                        .build();

        TransactionItem transaction2 =
                TransactionItem.builder()
                        .amount(BigDecimal.valueOf(10.0))
                        .timestamp(LocalDateTime.now())
                        .build();
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
    }

    @Test
    public void shouldGetStatisticsSuccessfully() throws Exception {

        mvc.perform(get("/v1/statistics")).andExpect(status().isOk());
    }

    @Test
    public void shouldInsertTransactionSuccessfully() throws Exception {
        LocalDateTime timestamp = LocalDateTime.now();
        mvc.perform(
                        post("/v1/transactions")
                                .content(
                                        asJsonString(
                                                Transaction.builder()
                                                        .amount(BigDecimal.valueOf(10.0))
                                                        .build()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(10.0)));

    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
