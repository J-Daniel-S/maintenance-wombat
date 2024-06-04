package the.best.maintenancewombat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.redisson.api.RMapReactive;
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
import the.best.maintenancewombat.documents.branches.Category;
import the.best.maintenancewombat.documents.branches.Location;
import the.best.maintenancewombat.documents.branches.Priority;
import the.best.maintenancewombat.services.utils.InvalidModifierException;

@Service
public class MaintenanceService implements WebSocketHandler {
	
	private final ObjectMapper mapper;
    private Map<String, Task> tasks = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong();
	
	@Autowired
	private RedissonReactiveClient client;
	
	public MaintenanceService(@Autowired ObjectMapper mapper) {
		this.mapper = mapper;
	}
	
	@PostConstruct
	private void initTasksHash() {
		
		Task task1 = new Task("Clean gutters", Priority.LOW, Category.CLEANUP, Location.ABILENE);
		Task task2 = new Task("Replace light bulbs", Priority.HIGH, Category.ELECTRICAL, Location.SUGARLAND);
		Task task3 = new Task("Unclog sink", Priority.LOW, Category.PLUMBING, Location.MCALLEN);
		Task task4 = new Task("Install new network switch", Priority.HIGH, Category.IT, Location.FORT_WORTH);
		Task task5 = new Task("Paint office walls", Priority.MEDIUM, Category.STRUCTURAL, Location.LUBBOCK);
		Task task6 = new Task("Clean oil spill", Priority.HIGH, Category.CLEANUP, Location.SAN_ANTONIO);
		Task task7 = new Task("Add new computers to network", Priority.MEDIUM, Category.IT, Location.SAN_ANTONIO);
		Task task8 = new Task("Install new coffee maker", Priority.LOW, Category.OTHER, Location.ABILENE);
		Task task9 = new Task("Repair broken drywall in 3rd floor conference room", Priority.MEDIUM, Category.STRUCTURAL, Location.MCALLEN);
		
		try {
			client.getMap("ABILENE")
				.fastPut(task1.getName(), task1)
				.then(Mono.fromRunnable(() -> {
					tasks.put(task1.getName(), task1);
				})).subscribe();
			client.getMap("SUGARLAND")
				.fastPut(task2.getName(), mapper.writeValueAsString(task2))
				.then(Mono.fromRunnable(() -> {
					tasks.put(task2.getName(), task2);
				}))
				.subscribe();
			client.getMap("MCALLEN")
				.put(task3.getName(), task3)
				.then(Mono.fromRunnable(() -> {
					tasks.put(task3.getName(), task3);
				}))
				.subscribe();
			client.getMap("FORT_WORTH")
				.fastPut(task4.getName(), task4)
				.then(Mono.fromRunnable(() -> {
					tasks.put(task4.getName(), task4);
				}))
				.subscribe();
			client.getMap("LUBBOCK")
				.fastPut(task5.getName(), task5)
				.then(Mono.fromRunnable(() -> {
					tasks.put(task5.getName(), task5);
				}))
				.subscribe();
			client.getMap("SAN_ANTONIO")
				.fastPut(task6.getName(), mapper.writeValueAsString(task6))
				.then(Mono.fromRunnable(() -> {
					tasks.put(task6.getName(), task6);
				}))
				.subscribe();
			client.getMap("SAN_ANTONIO")
				.fastPut(task7.getName(), mapper.writeValueAsString(task7))
				.then(Mono.fromRunnable(() -> {
					tasks.put(task7.getName(), task7);
				}))
				.subscribe();
			client.getMap("ABILENE")
				.fastPut(task8.getName(), mapper.writeValueAsString(task8))
				.then(Mono.fromRunnable(() -> {
					tasks.put(task8.getName(), task8);
				}))
				.subscribe();
			client.getMap("MCALLEN")
				.fastPut(task9.getName(), mapper.writeValueAsString(task9))
				.then(Mono.fromRunnable(() -> {
					tasks.put(task9.getName(), task9);
				}))
				.subscribe();
		
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Mono<Void> handle(WebSocketSession session) {
		
		return session.receive()
			.map(WebSocketMessage::getPayloadAsText)
			.flatMap(msg -> {
				System.out.println("1:\n" + msg);
				MaintenanceRequest change;
				try {
					change = mapper.readValue(msg, MaintenanceRequest.class);
					System.out.println("2:" + change);
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
	
	private Mono<Void> handleTaskChange(MaintenanceRequest request, WebSocketSession session) throws JsonProcessingException {
	    String taskKey = String.valueOf(request.getTask().getId());
	    String location = request.getTask().getLocation().toString();
	    
	    	switch (request.getType()) {
	    	case DELETE:
	    		return client.getMap(location)
	    				.remove(taskKey)
	    				.then(Mono.fromRunnable(() -> {
	    					tasks.remove(taskKey);
	    					sendUpdatedTaskList(session);
	    				}));
	    	case ADDORUPDATE:
	    		List<String> taskNames = new ArrayList<>();
	    		tasks.forEach((id, t) -> {
	    			if (t.getLocation().toString().equalsIgnoreCase(location)) {
	    				taskNames.add(t.getName());
	    			}
	    		});
	    		
	    		if (taskNames.contains(request.getTask().getName())) {
	    			return 	session.send(Flux.just(session.textMessage("Task already exists for " + location)))
	    			    	.then();
	    		} 
	    		
	    		return client.getMap(location)
	    				.put(taskKey, new ObjectMapper().writeValueAsString(request.getTask()))
	    				.then(Mono.fromRunnable(() -> {
	    					tasks.put(taskKey, request.getTask());
	    					sendUpdatedTaskList(session);
	    				}));
	    	case GETALL:
	    		return client.getMap("*").readAllMap()
	    				.then(Mono.fromRunnable(() -> {
	    					sendUpdatedTaskList(session);
	    				}));
	    	case GETLOCATION:
	    		return client.getMap(location).readAllMap()
	    				.then(Mono.fromRunnable(() -> {
	    					sendUpdatedTaskListFromLocation(session, location);
	    				}));
	    		
	    	default:
	    		return Mono.error(new InvalidModifierException("Value change type not properly specified:  acceptable values: 'delete' or 'addorupdate'"));
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
	
	
	private void sendUpdatedTaskListFromLocation(WebSocketSession session, String location) {
		
			List<Location> locations = tasks.values().stream().map(t -> t.getLocation()).toList();
			
			if (locations.stream().map(l -> l.toString().trim().equalsIgnoreCase(location)).findAny().isEmpty()) {
				session.send(Flux.just(session.textMessage("No location matches " + location)))
		    	.subscribe();
				return;
			}
		
	    List<Task> updatedTasks = new ArrayList<>(tasks.values()).stream().filter(t -> t.compareLocation(location)).toList();
	    try {
	    	session.send(Flux.just(session.textMessage(new ObjectMapper().writeValueAsString(updatedTasks))))
	    	.subscribe();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			session.send(Mono.error(e));
		}
	}
	
	public static long generateId() {
		return idGenerator.incrementAndGet();
	}
	
}
