package the.best.maintenancewombat.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import the.best.maintenancewombat.services.MaintenanceService;

@Configuration
public class MaintenanceSocketConfig {

	@Autowired
	private MaintenanceService mServ;
	
	@Bean
	public HandlerMapping maintenanceHandlerMapping() {
		Map<String, WebSocketHandler> map = Map.of(
				"/maintenance-wombat", mServ
				);
		
		return new SimpleUrlHandlerMapping(map, -1);
	}
	
	
}
