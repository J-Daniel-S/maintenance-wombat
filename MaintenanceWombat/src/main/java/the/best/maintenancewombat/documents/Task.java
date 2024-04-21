package the.best.maintenancewombat.documents;

import the.best.maintenancewombat.documents.branches.Category;
import the.best.maintenancewombat.documents.branches.Priority;

public class Task {
	
	String name;
	Priority prio;
//	String branch;
	Category kind;
	
	public Task() {
		this.prio = Priority.LOW;
		this.kind = Category.OTHER;
	}
	
	public Task(String name) {
		this.name = name;
		this.prio = Priority.LOW;
		this.kind = Category.OTHER;
	}
	
	public Task(String name, Priority prio) {
		this.name = name;
		this.prio = prio;
		this.kind = Category.OTHER;
	}

	public Task(String name, Priority prio, Category kind) {
		this.name = name;
		this.prio = prio;
		this.kind = kind;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Priority getPrio() {
		return prio;
	}

	public void setPrio(Priority prio) {
		this.prio = prio;
	}

	public Category getKind() {
		return kind;
	}

	public void setKind(Category kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return "Task [name=" + name + ", prio=" + prio + ", kind=" + kind + "]";
	}
	
}