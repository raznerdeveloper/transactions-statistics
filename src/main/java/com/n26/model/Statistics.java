package com.n26.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.serializer.AmountJsonSerializer;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
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

    private long count;

    public Statistics(TransactionStore store) {
        this.sum = BigDecimal.valueOf(store.getSum());
        this.count = store.getCount();

        if (this.count == 0) {
            this.average = BigDecimal.ZERO;
        } else {
            BigDecimal scaledSum = this.sum.setScale(2, BigDecimal.ROUND_HALF_UP);
            this.average = scaledSum.divide(new BigDecimal(this.count), BigDecimal.ROUND_HALF_UP);
        }

        this.maximum = store.getMaximum().compareTo(Double.MIN_VALUE) == 0
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(store.getMaximum());

        this.minimum = store.getMinimum().compareTo(Double.MAX_VALUE) == 0
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(store.getMinimum());

    }
}
