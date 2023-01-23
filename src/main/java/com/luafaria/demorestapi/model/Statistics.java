package com.luafaria.demorestapi.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    BigDecimal sum;
    BigDecimal avg;
    BigDecimal min;
    BigDecimal max;
    Long count;
}
