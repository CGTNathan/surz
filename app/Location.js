class Location {

	var name;				//Name
	var maxEncounters;			//Maximum amount of encounters
	var encounters;				//Encounters had
	var maxSupplies;			//Maximum amount of supplies
	var supplies;				//Supplies found
	var supply;					//Minimum value to find supplies
	var food;					//Minimum value to find food
	var weapon;					//Minimum value to find weapons
	var scavengable;		//Scavengable
	var days;					//Days since became unscavengable
	var minimum;				//Minimum value to encounter Zombies
	var reset = false;		//Reset supplies and encounters
	var isHere;				//Survivor location

	load(var id, var db, var q) {
  	var query = {_id: id, places: q};
  	db.collection("sessions").find(query).toArray(function(err, session) {
	}

	getMaxEncounters() {
		return maxEncounters;
	}

	addEncounters() {
		encounters++;
		if (encounters >= maxEncounters) {

		}
	}

	getEncounters() {
		return encounters;
	}

	getMaxSupplies() {
		return maxSupplies;
	}

	addSupplies() {
		supplies++;
	}

	getSupplies() {
		return supplies;
	}

	getName() {
		return name;
	}

	getSupply() {
		return supply;
	}

	getFood() {
		return food;
	}

	getWeapon() {
		return weapon;
	}

	getMinimum() {
		return minimum;
	}

	getIsHere() {
		return isHere;
	}

	getScavengable() {
		return scavengable;
	}

	addDays() {
		if (!scavengable) {
			days++;
		}
	}

	getDays() {
		return days;
	}

	check() {
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

	reset() {
		scavengable = true;
		reset = false;
		encounters = 0;
		supplies = 0;
	}

	setReset(input) {
		reset = input;
	}

	getReset() {
		return reset;
	}

	setIsHere(input) {
		isHere = input;
	}
}
