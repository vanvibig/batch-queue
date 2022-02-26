package com.kv.batchqueue.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CustomerEntity implements Serializable {
    private String name;
    private int year;
    private boolean male;
    private BigDecimal amount;
}
