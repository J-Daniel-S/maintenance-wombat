package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.RequestType;

public class MaintenanceRequest {
	
	Task task;
	RequestType type;
	
	public MaintenanceRequest() {
		
	}
	
	public MaintenanceRequest(Task task, RequestType type) {
		this.task = task;
		this.type = type;
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

	@Override
	public String toString() {
		return "Change [task=" + task + ", type=" + type + "]";
	}
	

}


