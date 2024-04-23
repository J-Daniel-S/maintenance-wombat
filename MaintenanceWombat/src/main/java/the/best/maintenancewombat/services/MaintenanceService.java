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
import com.fasterxml.jackson.databind.JsonMappingException;
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
	private void initTasksHash() {
		
		String task1String = "{\"name\":\"Clean gutters\",\"prio\":\"MEDIUM\",\"location\":\"ABILENE\",\"kind\":\"CLEANUP\"}";
		String task2String = "{\"name\":\"Replace light bulbs\",\"prio\":\"HIGH\",\"location\":\"SUGARLAND\",\"kind\":\"ELECTRICAL\"}";
		String task3String = "{\"name\":\"Unclog sink\",\"prio\":\"LOW\",\"location\":\"MCALLEN\",\"kind\":\"PLUMBING\"}";
		String task4String = "{\"name\":\"Install new network switch\",\"prio\":\"HIGH\",\"location\":\"FORT_WORTH\",\"kind\":\"IT\"}";
		String task5String = "{\"name\":\"Paint office walls\",\"prio\":\"MEDIUM\",\"location\":\"LUBBOCK\",\"kind\":\"PAINT\"}";
		String task6String = "{\"name\":\"Clean oil spill\",\"prio\":\"HIGH\",\"location\":\"SAN_ANTONIO\",\"kind\":\"CLEANUP\"}";
		
		try {
			Task task1 = mapper.readValue(task1String, Task.class);
			Task task2 = mapper.readValue(task2String, Task.class);
			Task task3 = mapper.readValue(task3String, Task.class);
			Task task4 = mapper.readValue(task4String, Task.class);
			Task task5 = mapper.readValue(task5String, Task.class);
			Task task6 = mapper.readValue(task6String, Task.class);
			// LIKELY UNNECESSARY
//			client.getMap("tasks")
//				.fastPut("init_key", "init_value")
//				.subscribe();
			client.getMap("SAN_ANTONIO")
				.put("init_key", "init_value")
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
			client.getMap("MCALLEN")
				.fastPut("Unclog sink", task3)
				.subscribe();
			client.getMap("SUGARLAND")
				.fastPut("Replace light bulbs", task2)
				.subscribe();
			client.getMap("SAN_ANTONIO")
				.put("Clean oil spill", task6)
				.subscribe();
			client.getMap("FORT_WORTH")
				.fastPut("Install new network switch", task4)
				.subscribe();
			client.getMap("LUBBOCK")
				.fastPut("Paint office walls", task5)
				.subscribe();
			client.getMap("ABILENE")
				.fastPut("Replace light bulbs", task1)
				.subscribe();
		
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
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
	    		return client.getMap("*").readAllMap()
	    				.then(Mono.fromRunnable(() -> {
	    					sendUpdatedTaskList(session);
//	    					System.out.println("Get all tasks in all locations");
	    				}));

	    	case GETLOCATION:
	    		return client.getMap(location).readAllMap()
	    				.then(Mono.fromRunnable(() -> {
	    					sendUpdatedTaskList(session);
//	    					System.out.println("Get all tasks in " + location);
	    				}));
	    		
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
