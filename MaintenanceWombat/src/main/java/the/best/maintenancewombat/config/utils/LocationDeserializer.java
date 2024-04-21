package the.best.maintenancewombat.config.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import the.best.maintenancewombat.documents.branches.Location;

public class LocationDeserializer extends JsonDeserializer<Location> {

	@Override
	public Location deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
		String locStr = parser.getValueAsString().replace(' ', '_');
		return Location.fromString(locStr);
	}

}
