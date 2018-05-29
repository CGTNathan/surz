class Item {

	var name;
	var maxDamage;
	var heals;
	var type;
	var probability;
	var maxDurability;
	var durability;
	var phrase;
	var isHolding = false;

	Item(n, dmg, heal, t, prob, durable, p) {

		name = n;
		maxDamage = dmg;
		heals = heal;
		type = t;
		probability = prob;
		maxDurability = durable;
		durability = maxDurability;
		phrase = p;

	}

	Item(n, dmg, heal, t, prob, durable, p, hold) {

		name = n;
		maxDamage = dmg;
		heals = heal;
		type = t;
		probability = prob;
		maxDurability = durable;
		durability = maxDurability;
		phrase = p;
		isHolding = hold;
	}

	use() {
		if (durability > 0) {
			durability--;
		}
	}

	info() {
		return "Weapon: " + name + "   Damage: 1d" + maxDamage + "   Durability: (" + durability + "/" + maxDurability + ")";
	}

	getName() {
		return name;
	}

	getMaxDamage() {
		return maxDamage;
	}

	getHeal() {
		return heals;
	}

	getProbability() {
		return probability;
	}

	getMaxDurability() {
		return maxDurability;
	}

	setDurability(durable) {
		durability = durable;
	}

	getDurability() {
		return durability;
	}

	getPhrase() {
		return phrase;
	}

	getIsHolding() {
		return isHolding;
	}

	setIsHolding(b) {
		isHolding = b;
	}

	getType() {
		return type;
	}

	toString() {
		return "Weapon: " + name + "   Durability: (" + durability + "/" + maxDurability + ")";
	}
}
