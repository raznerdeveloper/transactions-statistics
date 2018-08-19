package com.n26.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
public class Transaction {

    @NotNull
    private Double amount;

    @NotNull
    @PastOrPresent
    private Date timestamp;
}
