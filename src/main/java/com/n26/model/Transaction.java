package com.n26.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Transaction {

    @NotNull
    private BigDecimal amount;

    @NotNull
    @PastOrPresent
    private Date timestamp;
}
