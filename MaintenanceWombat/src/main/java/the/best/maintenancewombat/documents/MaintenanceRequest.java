package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.Location;
import the.best.maintenancewombat.documents.branches.RequestType;

public class MaintenanceRequest {
	
	Task task;
	Location location;
	RequestType type;
	
	public MaintenanceRequest() {
		
	}
	
	public MaintenanceRequest(Task task, RequestType type, Location location) {
		this.task = task;
		this.type = type;
		this.location = location;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Change [task=" + task + ", type=" + type + "]";
	}
	

}


