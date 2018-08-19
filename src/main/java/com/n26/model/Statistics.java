package com.n26.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.serializer.AmountJsonSerializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonPropertyOrder({ "sum", "avg", "max", "min", "count" })
public class Statistics {

    @JsonSerialize(using = AmountJsonSerializer.class)
    private BigDecimal sum;

    @JsonProperty("avg")
    @JsonSerialize(using = AmountJsonSerializer.class)
    private BigDecimal average;

    @JsonProperty("max")
    @JsonSerialize(using = AmountJsonSerializer.class)
    private BigDecimal maximum;

    @JsonProperty("min")
    @JsonSerialize(using = AmountJsonSerializer.class)
    private BigDecimal minimum;

    private int count;

    public Statistics() {
        this.reset();
    }

    public void reset() {
        this.sum = BigDecimal.ZERO;
        this.average = BigDecimal.ZERO;
        this.maximum = BigDecimal.ZERO;
        this.minimum = BigDecimal.ZERO;
        this.count = 0;
    }

    public void addTransaction(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();

        if (this.count == 0 || amount.compareTo(this.minimum) < 0) {
            this.minimum = amount;
        }

        if (amount.compareTo(this.maximum) > 0) {
            this.maximum = amount;
        }

        this.average = (this.average.multiply(new BigDecimal(this.count)).add(amount)).divide(new BigDecimal(this.count + 1), BigDecimal.ROUND_HALF_UP);
        this.sum = sum.add(amount);

        this.count += 1;
    }
}
