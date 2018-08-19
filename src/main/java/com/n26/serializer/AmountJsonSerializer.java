package com.n26.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class AmountJsonSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        DecimalFormat formatter = new DecimalFormat("#.##");
        formatter.setMinimumFractionDigits(2);
        final String output = formatter.format(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        gen.writeString(output);
    }
}
