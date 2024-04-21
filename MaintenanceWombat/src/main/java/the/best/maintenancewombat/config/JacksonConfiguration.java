package the.best.maintenancewombat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import the.best.maintenancewombat.config.utils.CategoryDeserializer;
import the.best.maintenancewombat.config.utils.PriorityDeserializer;
import the.best.maintenancewombat.config.utils.ChangeTypeDeserializer;
import the.best.maintenancewombat.documents.branches.Category;
import the.best.maintenancewombat.documents.branches.ChangeType;
import the.best.maintenancewombat.documents.branches.Priority;

@Configuration
public class JacksonConfiguration {

	@Bean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper mapper = builder.createXmlMapper(false).build();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Priority.class, new PriorityDeserializer());
		module.addDeserializer(Category.class, new CategoryDeserializer());
		module.addDeserializer(ChangeType.class, new ChangeTypeDeserializer());
		mapper.registerModule(module);
		return mapper;
	}
	
}
