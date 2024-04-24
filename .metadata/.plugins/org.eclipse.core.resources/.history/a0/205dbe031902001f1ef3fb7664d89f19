package the.best.maintenancewombat.config.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import the.best.maintenancewombat.documents.branches.Category;

public class CategoryDeserializer extends JsonDeserializer<Category> {

	@Override
	public Category deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
		String catStr = parser.getValueAsString();
		return Category.fromString(catStr);
	}

}
