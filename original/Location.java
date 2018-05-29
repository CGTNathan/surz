
public class Location {

	String name;				//Name
	int maxEncounters;			//Maximum amount of encounters
	int encounters;				//Encounters had
	int maxSupplies;			//Maximum amount of supplies
	int supplies;				//Supplies found
	int supply;					//Minimum value to find supplies
	int food;					//Minimum value to find food
	int weapon;					//Minimum value to find weapons
	boolean scavengable;		//Scavengable
	int days;					//Days since became unscavengable
	int minimum;				//Minimum value to encounter Zombies
	boolean reset = false;		//Reset supplies and encounters
	boolean isHere;				//Survivor location
	
	public int getMaxEncounters() {
		return maxEncounters;
	}
	
	public void addEncounters() {
		encounters++;
		if (encounters >= maxEncounters) {
			
		}
	}
	
	public int getEncounters() {
		return encounters;
	}
	
	public int getMaxSupplies() {
		return maxSupplies;
	}
	
	public void addSupplies() {
		supplies++;
	}
	
	public int getSupplies() {
		return supplies;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSupply() {
		return supply;
	}
	
	public int getFood() {
		return food;
	}
	
	public int getWeapon() {
		return weapon;
	}
	
	public int getMinimum() {
		return minimum;
	}
	
	public boolean getIsHere() {
		return isHere;
	}
	
	public boolean getScavengable() {
		return scavengable;
	}
	
	public void addDays() {
		if (!scavengable) {
			days++;
		}
	}
	
	public int getDays() {
		return days;
	}
	
	public void check() {
		if (encounters >= maxEncounters || supplies >= maxSupplies) {
			scavengable = false;
			encounters = 0;
			supplies = 0;
			days = 0;
		}
		if (days >= 5) {
			scavengable = true;
			encounters = 0;
			supplies = 0;
			days = 0;
		}
	}
	
	public void reset() {
		scavengable = true;
		reset = false;
		encounters = 0;
		supplies = 0;
	}
	
	public void setReset(boolean input) {
		reset = input;
	}
	
	public boolean getReset() {
		return reset;
	}
	
	public void setIsHere(boolean input) {
		isHere = input;
	}
}
