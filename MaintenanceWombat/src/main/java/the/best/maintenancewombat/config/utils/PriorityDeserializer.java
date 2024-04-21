package the.best.maintenancewombat.config.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import the.best.maintenancewombat.documents.branches.Priority;

public class PriorityDeserializer extends JsonDeserializer<Priority> {
	
	@Override
	public Priority deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException{
		String prioStr = parser.getValueAsString();
		return Priority.fromString(prioStr);
	}
	
}