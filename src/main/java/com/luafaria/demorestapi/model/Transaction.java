package com.luafaria.demorestapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class Transaction {
  BigDecimal amount;
  LocalDateTime timestamp;
}
