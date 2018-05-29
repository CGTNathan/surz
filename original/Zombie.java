
public class Zombie {

	private int searching;	//Chance of encountering a Survivor
	private int maxHP;		//Damage Zombie can take
	private int health;		//Amount of HP remaining
	private Item weapon;	//Weapon
	private int strength;
	private int agility;	//Probability of dodge
	private boolean cont;	//Continue
	
	public Zombie() {
		searching = 0;
		maxHP = 30;
		health = maxHP;
		weapon = new Item("Fist",4,0,"weapon",-1,-1,"");
		strength = 0;
		agility = 0;
		cont = true;
	}
	
	public void set(int search, int HP, Item wpn, int str, int agil) {
		searching = search; 
		maxHP = HP;
		health = maxHP;
		weapon = wpn;
		strength = str;
		agility = agil;
		cont = true;
	}
	
	public int getSearching() {
		return searching;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void subHealth(int sub) throws InterruptedException {
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

	public boolean getCont() {
		return cont;
	}
	
	public Item getWeapon() {
		return weapon;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getAgility() {
		return agility;
	}
	
	public String toString() {
		String output = "Zombie: " + searching + " " + health + "/" + maxHP + " " + weapon.toString() + " " + agility;
		return output;
	}
}
