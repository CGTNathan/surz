
public class Survivor {

	private String desc;		//Character description
	private String name;		//Character name
	private int maxHP;			//Damage Survivor can take
	private int health;			//Amount of HP remaining
	private Item weapon;		//Weapon
	private int exposure;		//Chance of encountering a zombie
	private int scavenging;		//Probability of finding supplies
	private int strength;		//Amount of damage dealt
	private int agility;		//Probability of dodge
	private int special;		//Special ability
	private int level;			//Level
	private int maxExp;			//Max experience
	private int exp;			//Experience
	private int expStep;		//Exp per zombie
	private boolean cont;		//Continue
	
	public Survivor() {			//Default
		desc = "Survivor";
		name = "Survivor";
		maxHP = 100;
		health = maxHP;
		weapon = new Item("Fist",4,0,"default",-1,-1,"");
		exposure = 10;
		scavenging = 2;
		strength = 0;
		agility = 4;
		special = 0;
		level = 1;
		maxExp = 100;
		exp = 0;
		expStep = 50;
	}
	
	public void setCharacter(String character) {
		cont = true;
		character = character.toLowerCase();
		switch (character) {
			case ("john"):
				desc = "John is a fire-fighter, a kind man who sacrifices himself for others. "
						+ "But after the zombie outbreak, he decided to use his skills for suvival. "
						+ "His training has allowed him the endurance to survive many attacks. "
						+ "He carries a Fireman's Axe in order to protect himself and others. "
						+ "While he may not be the fastest or most vigilant, he is an ideal survivor with a knack for quick escapes.";
				name = "John";
				maxHP = 200;
				health = maxHP;
				weapon = new Item("Fireman's Axe",16,0,"weapon",-1,30,true);
				exposure = 4;
				scavenging = 6;
				strength = 0;
				agility = 4;
				special = 1;
				break;
			case ("linda"):
				desc = "Linda is a powerful woman who isn't afraid of zombies after she's dealt with high schoolers. "
						+ "She certainly is observant, as it comes with her job. "
						+ "This seemingly average woman carries a Wooden Ruler, her prefered choice of discipline. "
						+ "Her education has increased her knowledge on properly finding supplies and other necesities for survival.";
				name = "Linda";
				maxHP = 150;
				health = maxHP;
				weapon = new Item("Wooden Ruler",8,0,"weapon",-1,120,true);
				exposure = 3;
				scavenging = 4;
				strength = 0;
				agility = 3;
				special = 2;
				break;
			case ("jeremy"):
				desc = "Jeremy is widely known in this small town for his amazing cooking abilities. "
						+ "A former chef, he knows just how to prepare a nice Sunday brunch. "
						+ "While he is a bulky man, he makes up for it in raw strength gained from lifting heavy boxes of ingredients. "
						+ "He carries his trusty Frying Pan, excellent at cooking omlets. "
						+ "He may not be the fastest, however he knows how to properly cook a meal.";
				name = "Jeremy";
				maxHP = 250;
				health = maxHP;
				weapon = new Item("Frying Pan",12,0,"weapon",-1,60,true);
				exposure = 5;
				scavenging = 3;
				strength = 0;
				agility = 2;
				special = 3;
				break;
			case ("sarah"):
				desc = "Sarah is a respected officer in the military and is well prepared for the zombie outbreak. "
						+ "Her military training has prepared her for anything. "
						+ "She is both viligant and quick. "
						+ "Her Army Knife is the most useful tool she possesses, allowing her to deal extra damage to a zombie.";
				name = "Sarah";
				maxHP = 100;
				health = maxHP;
				weapon = new Item("Army Knife",8,0,"weapon",-1,120,true);
				exposure = 1;
				scavenging = 6;
				strength = 0;
				agility = 5;
				special = 4;
				break;
			case ("tommy"):
				desc = "Tommy is an average student at Blue Mill High, but his education was no preparation for the zombie outbreak. "
						+ "Despite this, he uses his quick wit, agile build, and fast reflexes to survive in this new society. "
						+ "He may not be the strongest, however his agility has gotten him out of bad run-ins with the police now and again. "
						+ "He moves quickly and can strike twice before anyone notices.";
				name = "Tommy";
				maxHP = 75;
				health = maxHP;
				weapon = new Item("Brother's Skateboard",8,0,"weapon",-1,120,true);
				exposure = 2;
				scavenging = 3;
				strength = 0;
				agility = 8;
				special = 5;
				break;
			default:
				System.out.println("Invalid input");
				cont = false;
		}
	}
	
	public String stats(String character) {
		character = character.toLowerCase();
		String output = "";
		switch (character) {
			case ("john"):
				output += "A former fire-fighter. Carries fireman's axe.\n";
				output += "Health: 200\nWeapon: Fireman's Axe\n" + "Damage: 1d16\n"
						+ "Exposure: +4\nScavenging: +6\nStrength: +0\nAgility: +4";
				System.out.println(output);
				break;
			case ("linda"):
				output += "A former school principle. Carries wooden ruler.\n";
				output += "Health: 150\nWeapon: Wooden Ruler\n"	+ "Damage: 1d8\n"
						+ "Exposure: +3\nScavenging: +4\nStrength: +0\nAgility: +3";
				System.out.println(output);
				break;
			case ("jeremy"):
				output += "A former chef. Carries frying pan.\n";
				output += "Health: 250\nWeapon: Frying Pan\n" + "Damage: 1d12\n"
						+ "Exposure: +5\nScavenging: +3\nStrength: +0\nAgility: +2";
				System.out.println(output);
				break;
			case ("sarah"):
				output += "A veteran of the army. Carries army knife.\n";
				output += "Health: 100\nWeapon: Army Knife\n" + "Damage: 1d10\n"
						+ "Exposure: +1\nScavenging: +6\nStrength: +0\nAgility: +5";
				System.out.println(output);
				break;
			case ("tommy"):
				output += "High school student. Carries brother's skateboard.\n";
				output += "Health: 75\nWeapon: Brother's Skateboard\n" + "Damage: 1d8\n"
						+ "Exposure: +2\nScavenging: +3\nStrength: +0\nAgility: +8";
				System.out.println(output);
				break;
			default:
				output = "Invalid option.";
				System.out.println(output);
		}
		
		return output;
	}
	
	public void raiseStat(int stat) {
		switch (stat) {
			case (1):
				maxHP += 25;
				health += 20;
				break;
			case (2):
				scavenging++;
				break;
			case (3):
				strength++;
				break;
			case (4):
				agility++;
				break;
			default:
				break;
		}
	}
	
	public void setWeapon(Item input) {
		weapon = input;
	}
	
	public void addHealth(int add) throws InterruptedException {
		if (health <= 0) {
			cont = false;
		} else if (health + add > maxHP) {
			System.out.println("You regained " + (maxHP - health) + " health!");
			Thread.sleep(500);
			System.out.println("You are now fully healed!");
			health = maxHP;
		} else {
			System.out.println("You regained " + add + " health!");
			health += add;
		}
	}
	
	public void subHealth(int sub) throws InterruptedException {
		if (health - sub <= 0) {
			System.out.println("You lost " + health + " health!");
			health = 0;
			cont = false;
			Thread.sleep(500);
			System.out.println("You died!");
		} else {
			System.out.println("You lost " + sub + " health!");
			health -= sub;
		}
	}
	
	public void gainExp(int xp) {
		exp += xp;
	}
	
	public int getExp() {
		return exp;
	}
	
	public void levelUp() {
		expStep = (int)(50*Math.pow(.8, level)+.5);
		level++;
		exp = exp%100;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getExpStep() {
		return expStep;
	}
	
	public int getMaxExp() {
		return maxExp;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getName() {
		return name;
	}
	
	public int getExposure() {
		return exposure;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Item getWeapon() {
		return weapon;
	}
	
	public int getScavenging() {
		return scavenging;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getAgility() {
		return agility;
	}
	
	public int getSpecial() {
		return special;
	}
	
	public boolean getCont() {
		return cont;
	}
	
}
