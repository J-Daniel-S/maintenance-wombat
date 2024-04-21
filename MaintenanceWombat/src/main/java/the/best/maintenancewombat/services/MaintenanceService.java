package the.best.maintenancewombat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.redisson.api.RedissonReactiveClient;
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
import the.best.maintenancewombat.documents.MaintenanceRequest;
import the.best.maintenancewombat.documents.Task;
import the.best.maintenancewombat.services.utils.InvalidModifierException;

@Service
public class MaintenanceService implements WebSocketHandler {
	
	/*
	 * NEED TO FIGURE OUT REACT FRONT END HERE
	 */
	
	private final ObjectMapper mapper;
    private Map<String, Task> tasks = new ConcurrentHashMap<>();
	
	@Autowired
	private RedissonReactiveClient client;
	
	public MaintenanceService(@Autowired ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	@PostConstruct
	public void initTasksHash() {
		client.getMap("tasks")
			.fastPut("init_key", "init_value")
			.subscribe();
		client.getMap("SAN_ANTONIO")
			.fastPut("init_key", "init_value")
			.subscribe();
		client.getMap("FORT_WORTH")
			.fastPut("init_key", "init_value")
			.subscribe();
		client.getMap("ABILENE")
			.fastPut("init_key", "init_value")
			.subscribe();
		client.getMap("LUBBOCK")
			.fastPut("init_key", "init_value")
			.subscribe();
		client.getMap("AMARILLO")
			.fastPut("init_key", "init_value")
			.subscribe();
		client.getMap("MCALLEN")
			.fastPut("init_key", "init_value")
			.subscribe();
		client.getMap("SUGARLAND")
			.fastPut("init_key", "init_value")
			.subscribe();
	}
	
	public void newLocation(String location) {
		client.getMap(location)
			.fastPut("init_key", "init_value")
			.subscribe();
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		
		return session.receive()
			.map(WebSocketMessage::getPayloadAsText)
			.flatMap(msg -> {
				MaintenanceRequest change;
				try {
					change = mapper.readValue(msg, MaintenanceRequest.class);
					return handleTaskChange(change, session)
							.then(session.send(Flux.just(session.textMessage(msg))));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return Mono.error(e);
				}
				})
	            .doOnError(System.out::println)
	            .doFinally(s -> System.out.println("Subscriber finally " + s))
	            .then();
		
	}
	
	private Mono<Void> handleTaskChange(MaintenanceRequest type, WebSocketSession session) throws JsonProcessingException {
	    String taskKey = type.getTask().getName();
	    String location = type.getTask().getLocation().toString();
	    
	    	switch (type.getType()) {
	    	case DELETE:
	    		return client.getMap(location)
	    				.remove(taskKey)
	    				.then(Mono.fromRunnable(() -> {
	    					tasks.remove(taskKey);
	    					sendUpdatedTaskList(session);
//	    					System.out.println(change.getTask() + " deleted");
	    				}));
	    		
	    	case ADDORUPDATE:
	    		return client.getMap(location)
	    				.put(taskKey, new ObjectMapper().writeValueAsString(type.getTask()))
	    				.then(Mono.fromRunnable(() -> {
	    					tasks.put(taskKey, type.getTask());
	    					sendUpdatedTaskList(session);
//	    					System.out.println(change.getTask() + " added || updated");
	    				}));

	    	case GETALL:
	    		return client.getMap(location).readAllMap()
	    				.then(Mono.fromRunnable(() -> {
	    					sendUpdatedTaskList(session);
//	    					System.out.println(change.getTask() + " added || updated");
	    				}));

	    	case GETLOCATION:
	    		// TODO: implement
//	    		return client.getMap(location)
//	    				.put(taskKey, new ObjectMapper().writeValueAsString(type.getTask()))
//	    				.then(Mono.fromRunnable(() -> {
//	    					tasks.put(taskKey, type.getTask());
//	    					sendUpdatedTaskList(session);
////	    					System.out.println(change.getTask() + " added || updated");
//	    				}));
	    		
	    	default:
	    		return Mono.error(new InvalidModifierException("Value change type not properly specified:  aceeptable values: 'delete' or 'addorupdate'"));
	    	}
	}

	private void sendUpdatedTaskList(WebSocketSession session) {
	    List<Task> updatedTasks = new ArrayList<>(tasks.values());
	    try {
	    	session.send(Flux.just(session.textMessage(new ObjectMapper().writeValueAsString(updatedTasks))))
	    	.subscribe();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			session.send(Mono.error(e));
		}
	}
	
	
}
