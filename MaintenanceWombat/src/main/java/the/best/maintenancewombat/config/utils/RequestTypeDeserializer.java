package the.best.maintenancewombat.config.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import the.best.maintenancewombat.documents.branches.RequestType;

public class RequestTypeDeserializer  extends JsonDeserializer<RequestType> {

	@Override
	public RequestType deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
		String typeStr = parser.getValueAsString();
		return RequestType.fromString(typeStr);
	}

}
