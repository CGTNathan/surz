class Library extends Location {

	Library() {
		maxEncounters = 10;
		encounters = 0;
		maxSupplies = 13;
		supplies = 0;
		supply = 13;
		food = 6;
		weapon = 13;
		name = "Library";
		minimum = 14;
		scavengable = true;
		days = 0;
	}

	function toString() {
		return name;
	}

}
