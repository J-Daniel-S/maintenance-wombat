package the.best.maintenancewombat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import the.best.maintenancewombat.config.utils.CategoryDeserializer;
import the.best.maintenancewombat.config.utils.RequestTypeDeserializer;
import the.best.maintenancewombat.config.utils.LocationDeserializer;
import the.best.maintenancewombat.config.utils.PriorityDeserializer;
import the.best.maintenancewombat.documents.branches.Category;
import the.best.maintenancewombat.documents.branches.RequestType;
import the.best.maintenancewombat.documents.branches.Location;
import the.best.maintenancewombat.documents.branches.Priority;

@Configuration
public class JacksonConfiguration {

	@Bean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper mapper = builder.createXmlMapper(false).build();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Priority.class, new PriorityDeserializer());
		module.addDeserializer(Category.class, new CategoryDeserializer());
		module.addDeserializer(RequestType.class, new RequestTypeDeserializer());
		module.addDeserializer(Location.class, new LocationDeserializer());
		mapper.registerModule(module);
		return mapper;
	}
	
}
