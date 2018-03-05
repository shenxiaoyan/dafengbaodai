package com.liyang.util;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Administrator
 *
 */
public class CustomDoubleSerialize extends JsonSerializer<Double>{

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		BigDecimal b = new BigDecimal(value);
		double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		gen.writeNumber(d);
		
//		DecimalFormat df = new DecimalFormat("#0.00");
//		gen.writeNumber(df.format(value));
	}

}
