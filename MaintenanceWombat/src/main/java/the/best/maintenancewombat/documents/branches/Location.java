package the.best.maintenancewombat.documents.branches;

public enum Location {
	SAN_ANTONIO, FORT_WORTH, ABILENE, LUBBOCK, AMARILLO, MCALLEN, SUGARLAND;
	
	public static Location fromString(String value) {
		if (value != null) {
			for (Location loc : Location.values()) {
				if (loc.name().equalsIgnoreCase(value)) {
					return loc;
				}
			}
		}
		throw new IllegalArgumentException("Invalid enum value: " + value);
	}

}
