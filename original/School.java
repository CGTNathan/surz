
public class School extends Location {
	
	public School() {
		maxEncounters = 12;
		encounters = 0;
		maxSupplies = 12;
		supplies = 0;
		supply = 12;
		food = 5;
		weapon = 15;
		name = "School";
		minimum = 13;
		scavengable = true;
		days = 0;
	}
	
	public String toString() {
		return name;
	}
	
}
