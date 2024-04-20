package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.ChangeType;

public class Change {
	
	Task task;
	ChangeType type;
	
	public Change() {
		
	}
	
	public Change(Task task, ChangeType type) {
		this.task = task;
		this.type = type;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public ChangeType getType() {
		return type;
	}

	public void setType(ChangeType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Change [task=" + task + ", type=" + type + "]";
	}
	

}


