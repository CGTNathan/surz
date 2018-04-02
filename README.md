# surz
Welcome to Zombie Survival!

This program was written by Nathan Schwartz for the AP Computer Science Principles Create Project
(Isn't that a mouth full)

It is important that Leaderboard.txt, ZombieSurvival.bat, and ZombieSurvival.jar stay in the same folder in order to start the program.
It is also important that you use the ZombieSurvival.bat to run the program, as this text-based adventure takes place in the command prompt.
It is recommended that if you have trouble seeing the words, increase your Command Prompt font size by:
	1. Right-click the title bar of the Command Prompt window.
	2. Tap Properties in the menu.
	3. Click the Font button on the top part.
	4. Select a size from the Size menu. (I recommend 20)

This file gives you a brief overview on how this game works.

----Gameplay----

Goal and Mechanics

- The goal of Zombie Survival is to survive as long as possible.
- Every week (7 days), the Zombies get increasingly stronger. This is called the dayMod (Day Modifier).
- A Survivor can perform 3 actions per day.
- An action is Travel, Scavenge, Return, and Sleep.
- A Survivor has a chance of encountering a Zombie when he or she performs Travel, Scavenge, or Return.
- A Zombie can only be found in the Shelter during very specific circumstances. Otherwise, it is completely safe.
- In order to survive, it is wise for the Survivor to scavenge for various weapons and food.
- Each Food heals 20 Health Points (HP), except if the Survivor is Jeremy. (More on this in Characters)
- When a Zombie is defeated, the Survivor gains experience (exp).
- When the Survivor accumulates 100 exp, they level up.
- Leveling up allows for the Survivor to modify one of four stats. (Max HP, Scavenging, Strength, and Agility).
- When a Survivor levels up, the amount of exp gained deteriorates.
- This game utilizes a dice-based RNG (Random Number Generator).
- For example, 1d20 means one twenty-sided die. It has an equal chance of rolling 1-20 (inclusive).
- Modifiers can be added to these dice rolls based on stats, weapon, or situation.
- When the Survivor's Health Points (HP) reaches 0, the game ends.

---Actions---

Travel:

- The Survivor leaves the Shelter in order to go to one of 5 Locations. (Store, School, Library, Mall, and Armory).
- Traveling costs 1 Action. (3 actions per day)
- Traveling can only be done from the Shelter.
- Each Location has special attributes for encountering, scavenging, and finding items.
- The minimum dice value for these Locations vary, making some areas better for food or weapons.
- The Survivor has a chance of encountering a Zombie on their way to the new Location.

Inventory:

- Opens up Inventory menu.
- Checking your Inventory costs 0 Actions. (You can do this as many times as you like)
- Switching a Survivor's weapon costs 1 Turn during Battle.
- Eating Food costs 1 Turn during Battle.
- A Survivor has an Inventory consisting of Food and Weapons
- A Survivor can only hold 3 weapons at a time. (1 held, 2 in inventory)
- A Survivor can hold any amount of Food.
- Each Food heals 20 Health Points (HP), except if the Survivor is Jeremy. (More on this in Characters)

Scavenge:

- The Survivor will scavenge the area for supplies. (Food or Weapons)
- Scavenging costs 1 Action. (3 actions per day)
- Scavenging cannot be done from the Shelter.
- Different Locations have different minimum values to scavenge.
- If the Survivor successfully rolls a Scavenge Roll (1d20+Scavenging), an Item Roll is made (1d20).
- This value determines what the Survivor finds in the area. (Nothing, Food, or Weapons)
- If the Survivor is Linda, she gets another bonus to her Scavenge Roll and Item Roll. (More on this in Characters)
- If the Location has been scavenged enough or cleared of Zombies, the Survivor will pillage the area.
- Pillaging is when the Survivor scavenges the Location 3 times without fail and without chance of encountering a Zombie.
- After a Location has been pillaged, it will become unscavengable for 5 days.
- When a location is unscavengable, the following is in effect:
	- The Survivor may not Scavenge in this Location.
	- The Survivor may not Travel to this Location.
	- The Survivor will not encounter Zombies when Returning from this location.
	
Return:

- The Survivor will Return to their Shelter from any other Location.
- Returning costs 1 Action. (3 actions per day)
- Returning cannot be done from the Shelter.
- The Survivor may encounter a Zombie on their way back to the Shelter.

Sleep:

- The Survivor will have a restful sleep.
- Sleeping costs 1 Action. (3 actions per day)
- Sleeping can only be done from the Shelter.
- A Survivor will heal 10% of their Maximum HP (Max HP) after sleeping.
- It is advised to sleep cautiously and to not spend too much time sleeping in the Shelter.

---Stats---

- Stats are modifiers to the base dice roll (1d20).
- In most cases, a 1d20 is rolled and a stat is added to the roll.

Exposure: 

- Exposure is a Survivor exclusive stat.
- Exposure is the modifier to an Encounter Roll.
- For example, 1d20 + Exposure + Searching = Encounter Roll.
- Exposure cannot be increased by leveling up.
- Exposure is increased by the dayMod.

Searching:

- Searching is a Zombie exclusive stat.
- Searching is the modifier to an Encounter Roll.
- For example, 1d20 + Exposure + Searching = Encounter Roll.
- Searching is increased by the dayMod.

HP (Health Points): 

- Health (HP) is the amount of damage a Survivor can take before dying.
- Zombie health is randomly generated, with assistance from the dayMod.
- Maximum Health (Max HP) can be increased by 25 after leveling up.
- HP is tracked as the Survivor and Zombie take damage.
- When the Zombie's HP reaches 0, the battle is over.
- When the Survivor's HP reaches 0, the game ends.

Scavenging: 

- Scavenging is a Survivor exclusive stat.
- Scavenging is the modifier to a Scavenge Roll.
- For example, 1d20 + Scavenging = Scavenge Roll.
- Scavenging can be increased by 1 after leveling up.

Strength: 

- Strength is the modifier to a Damage Roll.
- For example, 1d8 + Strength = Damage Roll.
- Strength can be increased by 1 after leveling up.
- All Survivors start the game with 0 Strength.

Agility: 

- Agility is the modifier to a Dodge Roll or Escape Roll.
- For example, 1d20 + Agility = Dodge Roll.
- Agility is used for dodging and running.
- Agility can be increased by 1 after leveling up.

---Battles---

- A Zombie is randomly generated upon Encounter. An Encounter Roll takes place immediately after.
- A Battle begins when the Survivor rolls above the minimum Encounter Roll (determined by Location).
- The Turn is displayed at the top of the Battle menu.
- The Survivor's name, level, weapon, weapon durability, and health is displayed at the top of the Battle menu.
- The Zombie's weapon is displayed below.
- A Battle is a Turn-based system in which the Survivor and Zombie are in combat.
- Each Turn begins with the Survivor's action and is followed by the Zombie's attack.

Fight:

- Fighting costs 1 Turn, unless the Survivor is Tommy. (More on this in Characters)
- The Survivor begins with a Damage Roll.
- The Survivor and Zombie enter a Dodge Challenge.
	- The Attacker and Defender roll 1d20 and add their Agility.
	- If the Attacker's Dodge Roll is higher than the Defender, the attack hits.
	- If the Defender's Dodge Roll is higher than the Attacker, the attack has a 1/3 chance of missing.
	- The Dodge Challenge happens when the Zombie attacks the Survivor as well.
- If the Survivor is Tommy, they will attack twice per turn.

Inventory:

- (See Actions)

Run:

- The Survivor attempts to flee from the Zombie.
- The Survivor and Zombie make an Escape Roll.
	- The Survivor and Zombie roll 1d20 and add their agility.
	- The Survivor's roll is subtracted by the Zombie's Agility and added by a runMod.
	- If the Survivor's Escape Roll is higher than the Zombie's, he or she will get away safely.
	- If the Zombie's Escape Roll is higher than the Survivor's, the Battle continues.
	- When this happens, the runMod is increased by 1.
	- This means that the more a Survivor runs, the better the chances of escape.
	
---Characters---

John:

A former fire-fighter. Carries fireman's axe.
- Max HP: 200
- Weapon: Fireman's Axe
- Damage: 1d16
- Exposure: +4
- Scavenging: +6
- Strength: +0
- Agility: +4
- Special: John has a 1/3 chance of adding 5 to an Escape Roll.

Linda:

A former school principle. Carries wooden ruler.
- Max HP: 150
- Weapon: Wooden Ruler
- Damage: 1d8
- Exposure: +3
- Scavenging: +4
- Strength: +0
- Agility: +3
- Special: Linda adds a special modifier to her Scavenge Rolls and item finding rolls.

Jeremy:

A former chef. Carries frying pan.
- Max HP: 250
- Weapon: Frying Pan
- Damage: 1d12
- Exposure: +5
- Scavenging: +3
- Strength: +0
- Agility: +2
- Special: Jeremy heals 2x more from Food. (40 HP healed per Food).

Sarah:

A veteran of the army. Carries army knife.
- Max HP: 100
- Weapon: Army Knife
- Damage: 1d10
- Exposure: +1
- Scavenging: +6
- Strength: +0
- Agility: +5
- Special: Sarah has a 1/3 chance of dealing 10 extra damage to a Damage Roll.

Tommy:

High school student. Carries brother's skateboard.
- Max HP: 75
- Weapon: Brother's Skateboard
- Damage: 1d8
- Exposure: +2
- Scavenging: +3
- Strength: +0
- Agility: +8
- Special: Tommy attacks twice during a Battle.

---Items---

- Food heals 20 HP per food, except if the Survivor is Jeremy.
- Weapons are unique in the amount of damage they deal.
- Weapons may range from rolling a 1d4 to a 1d30.
- A weapon's durability is listed in it's description.
- When a weapon's durability reaches 0, it breaks.
- Zombies can find weapons as well.
