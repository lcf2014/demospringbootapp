package com.luafaria.demorestapi.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Statistics {
  BigDecimal sum;
  BigDecimal avg;
  BigDecimal min;
  BigDecimal max;
  Long count;
}
