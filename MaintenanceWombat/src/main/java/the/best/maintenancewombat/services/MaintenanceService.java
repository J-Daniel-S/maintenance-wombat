package the.best.maintenancewombat.services;

import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MaintenanceService implements WebSocketHandler {
	
	/*
	 * NEED TO FIGURE OUT REACT FRONT END HERE
	 */
	
	@Autowired
	private RedissonReactiveClient client;

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		
		// later get rooms/topics via query params
		String request = "Maintenance request";
		RTopicReactive topic = this.client.getTopic(request, StringCodec.INSTANCE);
		RListReactive<String> list = this.client.getList(request, StringCodec.INSTANCE);
		
		session.receive()
			.map(WebSocketMessage::getPayloadAsText)
			.flatMap(msg -> list.add(msg).then(topic.publish(msg)))
			//add logging
			.doOnError(System.out::println)
			.doFinally(s -> System.out.println("Subscriber finally " + s))
			.subscribe();
		
		Flux<WebSocketMessage> flux = topic.getMessages(String.class)
				.startWith(list.iterator())
				.map(session::textMessage)
				.doOnError(System.out::println)
				.doFinally(s -> System.out.println("Publisher finally " + s));
		
		return session.send(flux);
	}
	
	
}
