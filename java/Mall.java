
public class Mall extends Location {
	
	public Mall() {
		maxEncounters = 8;
		encounters = 0;
		maxSupplies = 14;
		supplies = 0;
		supply = 14;
		food = 7;
		weapon = 12;
		name = "Mall";
		minimum = 13;
		scavengable = true;
		days = 0;
	}
	
	public String toString() {
		return name;
	}
	
}
