package com.vphoto.practice.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateFormat extends JsonSerializer<Date> {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)throws IOException {
//		System.out.println("=========="+value);
		gen.writeString(df.format(value));  
	}

}
