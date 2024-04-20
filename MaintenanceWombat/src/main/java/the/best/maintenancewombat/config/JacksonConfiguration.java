package the.best.maintenancewombat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Configuration
public class JacksonConfiguration {
	/*
	 * NOT WORKING
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return JsonMapper.builder()
				.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
				.build();
	}
	
}
