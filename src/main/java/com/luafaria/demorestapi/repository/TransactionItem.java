package com.luafaria.demorestapi.repository;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transaction")
@Builder
@Getter
public class TransactionItem {
  @Id
  String id;

  private BigDecimal amount;

  private LocalDateTime timestamp;
}
