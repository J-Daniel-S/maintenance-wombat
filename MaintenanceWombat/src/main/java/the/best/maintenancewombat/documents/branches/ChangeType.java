package the.best.maintenancewombat.documents.branches;

public enum ChangeType {
	ADDORUPDATE, DELETE;
	
	public static ChangeType fromString(String value) {
		if (value != null) {
			for (ChangeType type : ChangeType.values()) {
				if (type.name().equalsIgnoreCase(value)) {
					return type;
				}
			}
		}
		throw new IllegalArgumentException("Invalid enum value: " + value);
	}
	
}