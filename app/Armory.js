class Armory extends Location {

	function Armory() {
		maxEncounters = 6;
		encounters = 0;
		maxSupplies = 16;
		supplies = 0;
		supply = 16;
		food = 8;
		weapon = 11;
		name = "Armory";
		minimum = 12;
		scavengable = true;
		days = 0;
	}

	function toString() {
		return name;
	}
}
