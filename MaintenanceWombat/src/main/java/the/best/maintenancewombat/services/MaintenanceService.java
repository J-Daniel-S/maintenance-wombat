package the.best.maintenancewombat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import the.best.maintenancewombat.documents.Change;
import the.best.maintenancewombat.documents.Task;

@Service
public class MaintenanceService implements WebSocketHandler {
	
	/*
	 * NEED TO FIGURE OUT REACT FRONT END HERE
	 */
	
    private Map<String, Task> tasks = new ConcurrentHashMap<>();
	
	@Autowired
	private RedissonReactiveClient client;
	
	@PostConstruct
	public void initTasksHash() {
		client.getMap("tasks")
			.fastPut("init_key", "init_value")
			.subscribe();
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		
		// later get rooms/topics via query params
		String request = "tasks";
		RTopicReactive topic = this.client.getTopic(request, StringCodec.INSTANCE);
		RListReactive<String> list = this.client.getList(request, StringCodec.INSTANCE);
		
		return session.receive()
			.map(WebSocketMessage::getPayloadAsText)
			.flatMap(msg -> {
				Change change;
				try {
					change = new ObjectMapper().readValue(msg, Change.class);
					return handleTaskChange(change, session)
							.then(session.send(Flux.just(session.textMessage(msg))));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				return Mono.empty();
				})
	            .doOnError(System.out::println)
	            .doFinally(s -> System.out.println("Subscriber finally " + s))
	            .then();
		
	}
	
	private Mono<Void> handleTaskChange(Change change, WebSocketSession session) throws JsonProcessingException {
	    String taskKey = change.getTask().getName();
	    
	    	switch (change.getType()) {
	    	case DELETE:
	    		return client.getMap("tasks")
	    				.remove(taskKey)
	    				.then(Mono.fromRunnable(() -> {
	    					System.out.println(change.getTask() + " deleted");
	    					tasks.remove(taskKey);
	    					sendUpdatedTaskList(session);
	    				}));
	    		
	    	case ADD:
	    		return client.getMap("tasks")
	    				.put(taskKey, new ObjectMapper().writeValueAsString(change.getTask()))
	    				.then(Mono.fromRunnable(() -> {
	    					System.out.println(change.getTask() + " added");
	    					tasks.put(taskKey, change.getTask());
	    					sendUpdatedTaskList(session);
	    				}));
	    		
	    	case UPDATE:
	    		return client.getMap("tasks")
	    				.put(taskKey, new ObjectMapper().writeValueAsString(change.getTask()))
	    				.then(Mono.fromRunnable(() -> {
	    					System.out.println(change.getTask() + " updated");
	    					tasks.put(taskKey, change.getTask());
	    					sendUpdatedTaskList(session);
	    				}));
	    		
	    	default:
	    		return Mono.empty();
	    	}
	}

	private void sendUpdatedTaskList(WebSocketSession session) {
	    List<Task> updatedTasks = new ArrayList<>(tasks.values());
	    try {
	    	session.send(Flux.just(session.textMessage(new ObjectMapper().writeValueAsString(updatedTasks))))
	    	.subscribe();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	
}
