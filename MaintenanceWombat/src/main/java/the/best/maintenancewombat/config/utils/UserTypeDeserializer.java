package the.best.maintenancewombat.config.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import the.best.maintenancewombat.documents.branches.UserType;

public class UserTypeDeserializer extends JsonDeserializer<UserType> {

	@Override
	public UserType deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
		String uTString = parser.getValueAsString();
		return UserType.fromString(uTString);
	}

}
