package the.best.maintenancewombat.config;

import java.util.Objects;

import org.redisson.Redisson;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedissonReactiveClientConfig {

	@Autowired
	private Environment env;
	
	private RedissonReactiveClient client;
	
	@Bean
	public RedissonReactiveClient client() {
		
		if (Objects.isNull(client)) {
			Config config = new Config();
			config.useSingleServer()
				.setAddress(env.getProperty("redis.address"));
			client = Redisson.create(config).reactive();
		}
		
		return client;
	}
	
}
