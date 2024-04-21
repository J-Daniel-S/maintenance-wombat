package the.best.maintenancewombat.config.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import the.best.maintenancewombat.documents.branches.Priority;

public class PriorityDeserializer extends StdDeserializer<Priority> {
	
	/*
	 * THIS CLASS IS LIKELY USELESS
	 */
	
	public PriorityDeserializer() {
		this(null);
	}
	
	public PriorityDeserializer(Class<?> c) {
		super(c);
	}
	
	@Override
	public Priority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{
		String prioStr = p.getValueAsString();
		return Priority.valueOf(prioStr.toUpperCase());
	}
	
}
