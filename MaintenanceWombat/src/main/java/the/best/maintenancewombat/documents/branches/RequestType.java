package the.best.maintenancewombat.documents.branches;

public enum RequestType {
	ADDORUPDATE, GETALL, GETLOCATION, DELETE;
	
	public static RequestType fromString(String value) {
		if (value != null) {
			for (RequestType type : RequestType.values()) {
				if (type.name().equalsIgnoreCase(value)) {
					return type;
				}
			}
		}
		throw new IllegalArgumentException("Invalid enum value: " + value);
	}
	
}