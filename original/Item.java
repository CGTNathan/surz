
public class Item {

	private String name;
	private int maxDamage;
	private int heals;
	private String type;
	private int probability;
	private int maxDurability;
	private int durability;
	private String phrase;
	private boolean isHolding = false;
	
	public Item(String n, int dmg, int heal, String t, int prob, int durable, String p) {

		name = n;
		maxDamage = dmg;
		heals = heal;
		type = t;
		probability = prob;
		maxDurability = durable;
		durability = maxDurability;
		phrase = p;
		
	}

	public Item(String n, int dmg, int heal, String t, int prob, int durable, boolean hold) {

		name = n;
		maxDamage = dmg;
		heals = heal;
		type = t;
		probability = prob;
		maxDurability = durable;
		durability = maxDurability;
		phrase = "";
		isHolding = hold;
	}
	
	public void use() {
		if (durability > 0) {
			durability--;
		}
	}
	
	public String info() {
		return "Weapon: " + name + "   Damage: 1d" + maxDamage + "   Durability: (" + durability + "/" + maxDurability + ")";
	}
	
	public String getName() {
		return name;
	}
	
	public int getMaxDamage() {
		return maxDamage;
	}
	
	public int getHeal() {
		return heals;
	}
	
	public int getProbability() {
		return probability;
	}
	
	public int getMaxDurability() {
		return maxDurability;
	}
	
	public void setDurability(int durable) {
		durability = durable;
	}
	
	public int getDurability() {
		return durability;
	}
	
	public String getPhrase() {
		return phrase;
	}
	
	public boolean getIsHolding() {
		return isHolding;
	}
	
	public void setIsHolding(boolean b) {
		isHolding = b;
	}
	
	public String getType() {
		return type;
	}
	
	public String toString() {
		return "Weapon: " + name + "   Durability: (" + durability + "/" + maxDurability + ")";
	}
}
