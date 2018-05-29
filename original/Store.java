class Store extends Location {

	Store() {
		maxEncounters = 14;
		encounters = 0;
		maxSupplies = 10;
		supplies = 0;
		supply = 10;
		food = 4;
		weapon = 16;
		name = "Store";
		minimum = 12;
		scavengable = true;
		days = 0;
	}

	function toString() {
		return name;
	}
}
