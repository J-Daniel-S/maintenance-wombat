package the.best.maintenancewombat.config.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import the.best.maintenancewombat.documents.branches.ChangeType;

public class ChangeTypeDeserializer  extends JsonDeserializer<ChangeType> {

	@Override
	public ChangeType deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
		String typeStr = parser.getValueAsString();
		return ChangeType.fromString(typeStr);
	}

}
