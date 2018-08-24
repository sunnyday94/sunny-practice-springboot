package com.vphoto.practice.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class JsonDoubleFormat extends JsonSerializer<Double> {

	private DecimalFormat df = new DecimalFormat("##.00");   //默认保留两位小数
	  
	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)throws IOException {
//		System.out.println("=========="+value);
		gen.writeString(df.format(value));  
	}

}
