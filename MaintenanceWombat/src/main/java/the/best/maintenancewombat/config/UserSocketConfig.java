package the.best.maintenancewombat.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import the.best.maintenancewombat.services.UserService;

@Configuration
public class UserSocketConfig {
	
	@Autowired
	private UserService uServ;
	
	@Bean
	public HandlerMapping userHandlerMapping() {
		Map<String, WebSocketHandler> map = Map.of(
				"/wombat-users", uServ
				);
		return new SimpleUrlHandlerMapping(map, -1);
	}

}
