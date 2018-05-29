var Item = require('./Item.js');

class Zombie {

	var searching;	//Chance of encountering a Survivor
	var maxHP;		//Damage Zombie can take
	var health;		//Amount of HP remaining
	var weapon;	//Weapon
	var strength;
	var agility;	//Probability of dodge
	var cont;	//Continue

	Zombie() {
		searching = 0;
		maxHP = 30;
		health = maxHP;
		weapon = new Item("Fist",4,0,"weapon",-1,-1,"");
		strength = 0;
		agility = 0;
		cont = true;
	}

	function set(search, HP, wpn, str, agil) {
		searching = search;
		maxHP = HP;
		health = maxHP;
		weapon = wpn;
		strength = str;
		agility = agil;
		cont = true;
	}

	function getSearching() {
		return searching;
	}

	function getMaxHP() {
		return maxHP;
	}

	function getHealth() {
		return health;
	}

	function subHealth(sub) {
		if (health - sub <= 0) {
			System.out.println("The Zombie lost " + health + " health!");
			health = 0;
			cont = false;
			Thread.sleep(500);
			System.out.println("The Zombie died!");
		} else {
			System.out.println("The Zombie lost " + sub + " health!");
			health -= sub;
		}
	}

	function getCont() {
		return cont;
	}

	function getWeapon() {
		return weapon;
	}

	function getStrength() {
		return strength;
	}

	function getAgility() {
		return agility;
	}

	function toString() {
		String output = "Zombie: " + searching + " " + health + "/" + maxHP + " " + weapon.toString() + " " + agility;
		return output;
	}
}
