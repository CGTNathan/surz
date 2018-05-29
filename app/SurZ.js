var Survivor = require("./Survivor.js");
var Zombie = require("./Zombie.js");
var Location = require("./Location.js");
var Store = require("./Store.js");
var School = require("./School.js");
var Library = require("./Lobrary.js");
var Mall = require("./Mall.js");
var Armory = require("./Armory.js");
var Shelter = require("./Shelter.js");
var Item = require("./Item.js");
var Session = require("./Session.js");

var Survivor = new Survivor();
var Zombie = new Zombie();
var Session = new Session();

var places = [];
var store = new Store();
var school = new School();
var library = new Library();
var mall = new Mall();
var armory = new Armory();
var shelter = new Shelter();

var inventory = [];
var items = [];
var zombieWpns = [];

var output = "";


function checkFor(required) {
  var input = input.toLowerCase().trim();
  //Check user input
  if (input.contains(required)) {
    return true;
  }
  return false;
}

function isInt(value) {
  return !isNaN(value) &&
         parseInt(Number(value)) == value &&
         !isNaN(parseInt(value, 10));
}

function println(input) {
  output += input + "<br>";
}

function print(input) {
  output += input;
}

function sleep(input) {
  output += "<SLEEP" + input + ">"
}

function surz(id, input) {

  Session.load(id, db);
  Survivor.load(id, db);
  Zombie.load(id, db);
  store.load(id, db, "store");
  school.load(id, db, "school");
  library.load(id, db, "library");
  mall.load(id, db, "mall");
  armory.load(id, db, "armory");
  shelter.load(id, db, "shelter");

  places.push(store);
  places.push(school);
  places.push(library);
  places.push(mall);
  places.push(armory);
  places.push(shelter);

  if (Session.getAtMenu()) {

  } else if (Session.getAtChoose()) {
    atChoose(input);
  } else if (Session.getAtInventory()) {
    atInventory(input);
  } else if (Session.getAtSureSwitch()) {
    atSureSwitch(input);
  } else if (Session.getAtSwitch()) {
    atSwitch(input);
  } else if (Session.getAtstartAtSureEat()) {
    atSureEat(input);
  } else if (Session.getAtTravel()) {
    atTravel(input);
  } else if (Session.getAtBattleChoose()) {
    atBattleChoose(input);
  } else if (Session.getAtEat()) {
    atEat(input);
  } else if (Session.getAtUse()) {
    atUse(input);
  } else if (Session.getAtSureDiscard()) {
    atSureDiscard(input);
  } else if (Session.getAtDiscard()) {
    atDiscard(input);
  } else if (Session.getAtWield()) {
    atWield(input);
  } else if (Session.getAtSureInitial()) {
    atSureInitial(input);
  } else if (Session.getAtInitial()) {
    atInitial(input)
  }

  Session.store(id, db);
  return output;

}

function startAtChoose() {
  //Beginning of Choose
  places.get(getLocationIndex()).check();


  println(Survivor.getName() + "   Weapon: " + Survivor.getWeapon().getName() + " (" + Survivor.getWeapon().getDurability() + "/" + Survivor.getWeapon().getMaxDurability() + ")   Health: (" + Survivor.getHealth() + "/" + Survivor.getMaxHP() + ")");
  println("");
  //Actions if Player is in Shelter
  if (places.get(5).getIsHere()) {
    println("What do you want to do?<br>"
				+ "[Travel] [Inventory] [Sleep]");
  //Otherwise
  } else {
    println("What do you want to do?<br>"
				+ "[Scavenge] [Inventory] [Return]");
  }

  Session.setAtChoose(true);
}

function atChoose(input) {
  //atChoose
  Session.setAtChoose(false);
	switch (input.toLowerCase().trim()) {
		case "inventory":
			//Inventory
      startAtInventory();
			break;
		case "travel":
		  //Travel
      Session.setBack(false);
      if (places.get(5).getIsHere()) {
        println("You have chosen to travel.");
  			startAtTravel();
      } else {
        println("Invalid input");
        startAtChoose();
      }
			break;
		case "sleep":
			//Sleep
			sleepAction();
			break;
		case "return":
			//Return
			returnAction();
			break;
		case "scavenge":
			//Scavenge
			scavengeAction();
			break;
		case "end":
			//Eject (debug option)
			Survivor.subHealth(Survivor.getHealth());
			break;
		default:
			//Invalid
			println("Invalid input");

      startAtChoose();
			break;
	}
}

function startAtInventory() {
  //Beginning of Inventory

  //Display outside of battle
  if (Session.getTurn() == 0) {
    println(Survivor.getName() + "   Weapon: " + Survivor.getWeapon().getName() + " (" + Survivor.getWeapon().getDurability() + "/" + Survivor.getWeapon().getMaxDurability() + ")   Health: (" + Survivor.getHealth() + "/" + Survivor.getMaxHP() + ")");
  }
  println("You are holding (" + countWeapons() + ") weapon(s) and (" + countFood() + ") food.");
  println("Would you like to switch your weapon, eat food, or go back?");
  println("[Switch]   [Eat]   [Back]");

  Session.setAtInventory(true);
}

function atInventory(input) {
		//atInventory
    Session.setAtInventory(false);
		println("");

		switch (input.toLowerCase().trim()) {
			case ("switch"):
				//If Player doesn't have a weapon
				if (inventory[0].getIsHolding()) {
					if (countWeapons() == 1) {
						inventory[0].setIsHolding(false);
						for (var i = 0; i < inventory.length; i++) {
							if (inventory[i].getType().equals("weapon") && !inventory[i].getIsHolding()) {
								inventory[i].setIsHolding(true);
								Survivor.setWeapon(inventory[i]);
								break;
							}
						}
					} else {
						println("You aren't carrying any more weapons!");
						Session.setBack(true);
					}
				//If Player only has one weapon
				} else if (countWeapons() == 1) {
					println("You're only carrying one weapon!");
					startAtInventory();
				//Otherwise
				} else {
          startAtSureSwitch();
				}
				break;
			case ("eat"):
				//If Player has food
				if (countFood() > 0) {
          startAtSureEat();
				} else {
					println("You don't have any more food!");
					startAtInventory();
				}
				break;
			case ("back"):
				//Back
        Session.setBack(true);
				break;
			default:
				println("Invalid input");

  			startAtInventory();
        break;
		}
	//Back
	if (Session.getBack(true)) {
    if (Session.getAtBattle()) {
      startAtBattleChoose();
    } else {
      startAtChoose();
    }
	}
	//If outside of battle
	if (Session.getTurn() == 0 && !Session.getBack()) {
		Session.setActions(Session.getActions()-1)
	}
}

function startAtSureSwitch() {
  println("You are currently holding: ");
  println(Survivor.getWeapon().info());
  println();
  println("In your inventory: ");
  var counter = 1;
  for (var i = 0; i < inventory.length; i++) {
    if (inventory[i].getType().equals("weapon") && !inventory[i].getIsHolding()) {
      println("[" + counter + "] " + inventory[i].info());
      counter++;
    }
  }

  //Verify
  println("Are you sure you want to switch?<br>[Yes] [No]");
  Session.setAtSureSwitch(true);
}

function atSureSwitch(input) {
  //atSureSwitch
  Session.setAtSureSwitch(false);
  println("");

  if (input.toLowerCase().trim().equals("yes")) {
    startAtSwitch();
  } else if (input.toLowerCase().trim().equals("no")) {
    startAtSwitch();
  } else if (input.toLowerCase().trim().equals("no")) {
    startAtInventory();
  } else {
    println("Invalid input");

    startAtSureSwitch();
  }
}

function startAtSwitch() {

  var temp = [];
  var amt = 0;
  var counter = 1;
  println("Which item(#) would you like? Please input number.");
  for (var i = 0; i < inventory.length; i++) {
    if (inventory[i].getType().equals("weapon") && !inventory[i].getIsHolding()) {
      println("[" + counter + "] " + inventory[i].info());
      temp[counter-1] = i;
      counter++;
    }
  }

  Session.setAtSwitch(true);
}

function atSwitch(input) {
  //atSwitch
  Session.setAtSwitch(false);

  var temp = [];
  var amt = 0;
  var counter = 1;

  if (isInt(input)) {
    amt = parseInt(input, 10);
    if (amt > countWeapons()-1 || amt <= 0 || amt > 3) {
      println("Invalid input");

      startAtSwitch();
    } else {
      //Unhold current weapon
      for (var i = 0; i < inventory.length; i++) {
        if (inventory[i].getIsHolding()) {
          inventory[i].setIsHolding(false);
          break;
        }
      }

      //Hold new weapon
      inventory[(temp[amt-1])].setIsHolding(true);
      Survivor.setWeapon(inventory[(temp[amt-1])]);

      if (Session.getAtBattle()) {
        continueBattle();
      } else {
        startAtChoose();
      }
    }
  } else {
    println("Invalid input");

    startAtSwitch();
  }
}

function startAtSureEat() {

  println("You are carrying (" + countFood() + ") food.");
  println("Are you sure you want to eat food?<br>[Yes] [No]");

  Session.setSureEat(true);
}

function atSureEat(input) {
  //atSureEat
  Session.setSureEat(false);
  println("");

  if (input.toLowerCase().trim().equals("yes")) {
    startAtEat();
  } else if (input.toLowerCase().trim().equals("no")) {
    startAtInventory();
  } else {
    println("Invalid input");

    startAtSureEat();
  }

}

function startAtEat() {

  println("You have (" + Survivor.getHealth() + "/" + Survivor.getMaxHP() + ") health. You are carrying (" + countFood() + ") food.");
  println("How much food would you like to eat?");

  Session.setAtEat(true);
}

function atEat(input) {
  var amt = 0;
  var heal = 0;
  //atEat
  Session.setAtEat(false);

  if (isInt(input)) {
    amt = parseInt(input, 10);
    if (amt > countFood() || amt <= 0) {
      println("Invalid input");

      startAtEat();
    } else {
      for (var x = 0; x < amt; x++) {
        for (var i = 0; i < inventory.length; i++) {
          //Heal and remove eaten food
          if (inventory[i].getType().equals("food")) {
            heal += inventory[i].getHeal();
            inventory.remove(i);
            break;
          }
        }
      }
      //Heal 2x if Jeremy
      heal = heal*special3();
      sleep(500);
      Survivor.addHealth(heal);
      println("");

      if (Session.getAtBattle()) {
        continueBattle();
      } else {
        startAtChoose();
      }
    }
  } else {
    println("Invalid input");

    startAtEat();
  }
}

function startAtTravel() {
  //Choose a location
  println("Choose a location to go to.");
  for (var i = 0; i < places.size()-1; i ++) {
    if (!places.get(i).getIsHere()) {
      print("[" + places.get(i).toString() + "]   ");
    }
  }
  print("[Back]");
  println("");

  Session.setAtTravel(true);
}

function atTravel(input) {
  //atTravel
  Session.setAtTravel(false);
  println("");

  switch (input.toLowerCase().trim()) {
    case ("store"):
      //If scavengable
      if (places.get(0).getScavengable()) {
        sleep(500);
        println("You leave the safety of your shelter and go to the Supermarket.");
        places.get(5).setIsHere(false);
        places.get(0).setIsHere(true);
        encounter();
      } else {
        println("The store isn't scavengable at the moment!");

        startAtTravel();
      }
      break;
    case ("school"):
      if (places.get(1).getScavengable()) {
        sleep(500);
        println("You leave the safety of your shelter and go to Blue Mill High School.");
        places.get(5).setIsHere(false);
        places.get(1).setIsHere(true);
        encounter();
      } else {
        println("The school isn't scavengable at the moment!");

        startAtTravel();
      }
      break;
    case ("library"):
      if (places.get(2).getScavengable()) {
        sleep(500);
        println("You leave the safety of your shelter and go to the Central Library.");
        places.get(5).setIsHere(false);
        places.get(2).setIsHere(true);
        encounter();
      } else {
        println("The library isn't scavengable at the moment!");

        startAtTravel();
      }
      break;
    case ("mall"):
      if (places.get(3).getScavengable()) {
        sleep(500);
        println("You leave the safety of your shelter and go to the Shopping Mall.");
        places.get(5).setIsHere(false);
        places.get(3).setIsHere(true);
        encounter();
      } else {
        println("The mall isn't scavengable at the moment!");

        startAtTravel();
      }
      break;
    case ("armory"):
      if (places.get(4).getScavengable()) {
        sleep(500);
        println("You leave the safety of your shelter and go to the City Police Department.");
        places.get(5).setIsHere(false);
        places.get(4).setIsHere(true);
        encounter();
      } else {
        println("The armory isn't scavengable at the moment!");

        startAtTravel();
      }
      break;
    case ("back"):
        Session.setBack(true);
        break;
    default:
      println("Invalid input");
      startAtTravel();
      break;
  }
  //Back
  if (Session.getBack(true)) {
    startAtChoose();
  }
  sleep(1000);
}

function sleepAction() {
  //If player is in Shelter
  if (places.get(5).getIsHere()) {
    sleep(1000);
    println("You spend the night in your shelter recovering some injuries.");
    sleep(1000);
    //Player recovers 10% health
    Survivor.addHealth(((Survivor.getMaxHP()*.1)+.5));
    Session.setSlept(Session.getSlept()++);
    //Ensures Player doesn't sleep too much
    if (Session.getSlept() == 5) {
      //BozonSpecial (BS) Zombie battle
      bozonSpecial();
    }
  } else {
    println("Invalid input");
    startAtChoose();
  }
  sleep(1000);
  println("");
}

function returnAction() {
  //If Player isn't in Shelter
  if (!places.get(5).getIsHere()) {
    //If location is scavengable
    if (places.get(getLocationIndex()).getScavengable()) {
      encounter();
    }
    sleep(500);
    println("You return to your shelter for safety.");
    places.get(getLocationIndex()).setIsHere(false);
    places.get(5).setIsHere(true);
  } else {
    println("Invalid input");
    startAtChoose();
  }
  sleep(1000);
}

function scavengeAction() {
  //If Player isn't in Shelter
  if (!places.get(5).getIsHere()) {
    //If location is scavengable
    if (places.get(getLocationIndex()).getScavengable()) {
      sleep(500);
      println("You search the " + places[(getLocationIndex())].getName().toLowerCase() + " for supplies.");
      //If Player rolls high enough
      //Extra scavenging if Linda
      if (rollD(20,(Survivor.getScavenging())+special2()) > places[(getLocationIndex())].getSupply()) {
        //If Player roll is high enough for weapons
        if (rollD(20) > places[(getLocationIndex())].getWeapon()-(special2()/2)) {
          places[(getLocationIndex())].addSupplies();
          var a = rollD(items.length-2);
          sleep(1000);
          var name = items[a].getName().toLowerCase();
          print("You found ");
          if (name.endsWith("s")) {
            print("some " + name + "!<br>");
          } else if (name.startsWith("a") || name.startsWith("e") || name.startsWith("i") || name.startsWith("o") || name.startsWith("u")) {
            print("an " + name + "!<br>");
          } else {
            print("a " + name + "!<br>");
          }
          findWeapon(items[a]);
        //If Player roll is high enough for food
      } else if (rollD(20) > places[(getLocationIndex())].getFood()-special2()){
          var newItem = items[(items.length-1)];
          places[getLocationIndex()].addSupplies();
          sleep(1000);
          println("You found some food!");
          inventory.push(newItem);

          finishScavenge();
        } else {
          sleep(1000);
          println("You couldn't manage to find anything!");

          finishScavenge();
        }
      } else {
        sleep(1000);
        println("You couldn't manage to find anything!");

        finishScavenge();
      }
    } else {
      println("The " + places.get(getLocationIndex()).getName().toLowerCase() + " isn't scavengable at the moment!");

      startAtChoose();
    }
  } else {
    println("Invalid input");

    startAtChoose();
  }
}

function finishScavenge() {
  //If scavenging or encounter is at limit
  places[getLocationIndex()].check();
  if (!places[getLocationIndex()].getScavengable()) {
    pillage();
  } else {
    encounter();
  }
}

function encounter() {
  //Randomly generate a zombie
  randomZombie();
  //If Player roll + exposure + Zombie searching is higher than required roll
  if (rollD(20,(Survivor.getExposure()+Zombie.getSearching())) > (places.get(getLocationIndex()).getMinimum()-dayMod)) {
    places.get(getLocationIndex()).addEncounters();
    sleep(1000);
    println("You've encountered a zombie!");
    battle();
  } else {
    sleep(1000);
    println("You avoid encountering Zombies.");
    println("");
    startAtChoose();
  }
}

function battle() {
  sleep(1000);
  //Initiate battle
  println("<br>---------- Battle Start -----------<br>");
  Session.setTurn(Session.getTurn()++);
  Session.setRunMod(0);
  startTurn();
}

function startTurn() {
  Session.setAtBattle(true);
  println("<br>----------<br>  Turn " + Session.getTurn() + "<br>----------<br>");
  startAtBattleChoose();
}

function startAtBattleChoose() {
  println(Survivor.getName() + " (Lv. " + Survivor.getLevel() + ")   " + Survivor.getWeapon().toString() + "   Health: " + healthBar() + " (" + Survivor.getHealth() + "/" + Survivor.getMaxHP() + ")");
  println();
  println("Zombie   Weapon: " + Zombie.getWeapon().getName());
  println();
  println("What will you do?");
  println("[Fight]   [Inventory]   [Run]");

  Session.setAtBattleChoose(true);
}

function atBattleChoose(input) {
  //atBattleChoose
  Session.setAtBattleChoose(false);
  println("");
  switch (input.toLowerCase().trim()) {
    case ("fight"):
      fight();
      //Uses a durability
      use();
      break;
    case ("inventory"):
      startAtInventory();
      break;
    case ("run"):
      //If Player (minus Zombie) rolls higher than Zombie
      if (rollD(20,((Survivor.getAgility()-Zombie.getAgility())+runMod+special1())) >= rollD(20,(Zombie.getAgility()))) {
        sleep(500);
        println("You get away safely!");
        Session.setAtBattle(false);
      } else {
        sleep(500);
        println("You can't escape!");
        //Chances of running away increase
        Session.setRunMod(Session.getRunMod()++);
      }
      println("");
      break;
    default:
      println("Invalid input");

      startAtBattleChoose();
      break;
  }
}

function checkBattle() {
  special5();

  if (Session.getAtkRepeat()) {
    fight();
    use();
  } else {
    println("");
    Session.setAtkCount(0);
    continueBattle();
  }
}

function continueBattle() {
  //Eject if Player or Zombie is dead
  if (!Survivor.getCont() || !Zombie.getCont()) {
    Session.setAtBattle(false);
  } else if (Session.getAtBattle()) {
    var damage = rollD(Zombie.getWeapon().getMaxDamage(),Zombie.getStrength());
    //If Player rolls less than Zombie
    if (rollD(20,Survivor.getAgility()) < rollD(20,(Zombie.getAgility()))) {
      sleep(500);
      println("The Zombie attacks you with its " + Zombie.getWeapon().getName().toLowerCase() + "!");
      sleep(1000);
      Survivor.subHealth(damage);
    } else {
      sleep(500);
      println("The Zombie attempts to attack you with its " + Zombie.getWeapon().getName().toLowerCase() + "!");
      //2/3 chance of hitting
      if (rollD(3,1,0) > 1) {
        sleep(1000);
        Survivor.subHealth(damage);
      //1/3 chance of missing
      } else {
        sleep(1000);
        println("You avoided the attack!");
      }
    }
    //Eject if Player is dead
    Session.setAtBattle(Survivor.getCont());
    Session.setTurn(Session.getTurn()++);
  }
  sleep(1000);
  if (Session.getAtBattle()) {
    startTurn();
  } else {
    //Exp if Zombie is dead
    if (!Zombie.getCont()) {
      sleep(500);
      gainExp();
    }
  }
}

function finishBattle() {
  places[(getLocationIndex())].check();
  //If scavenging or encounters is at limit
  if (Survivor.getCont() && !places[(getLocationIndex())].getScavengable()) {
    println("You've cleared out all of the zombies in the area!");
    pillage();
  } else {
    if (Session.getAtBS()) {
      finishBozonSpecial();
    } else {
      startAtChoose();
    }
  }
}

function fight() {
  var damage = 0;
  //Extra damage if Sarah
  damage = rollD(Survivor.getWeapon().getMaxDamage(),(Survivor.getStrength()+special4()));

  //If Player rolls higher than Zombie
  if (rollD(20,Survivor.getAgility()) >= rollD(20,(Zombie.getAgility()))) {
    sleep(500);
    println("You strike the Zombie with your " + Survivor.getWeapon().getName().toLowerCase() + "!");
    //If Player can say phrase
    if (damage >= Zombie.getHealth() && !Survivor.getWeapon().getPhrase().equals("") && rollD(20) == 20) {
      sleep(1000);
      println("\"" + Survivor.getWeapon().getPhrase() + "\"");
    }
    sleep(1000);
    Zombie.subHealth(damage);
  } else {
    sleep(500);
    println("You attempt to strike the Zombie with your " + Survivor.getWeapon().getName().toLowerCase() + "!");
    //If Player can say phrase
    if (damage >= Zombie.getHealth() && !Survivor.getWeapon().getPhrase().equals("") && rollD(20) == 20) {
      sleep(1000);
      println("\"" + Survivor.getWeapon().getPhrase() + "\"");
    }
    //2/3 chance of hitting
    if (rollD(3,1,0) > 1) {
      sleep(1000);
      Zombie.subHealth(damage);
    //1/3 chance of missing
    } else {
      sleep(1000);
      println("The Zombie avoided your attack!");
    }
  }
}

function healthBar() {
  //Only looks correct if proper Windows version (otherwise it shows |????|)
  //One block per 20 HP
  var out = "|";
  var spaces = ((Survivor.getMaxHP()/20)+.5);
  var blocks = ((Survivor.getHealth()/20)+1);
  for (var i = 0; i <= spaces; i++) {
    if (blocks > 0) {
      out += "█";
      blocks--;
    } else {
      out += " ";
    }
  }
  out += "|";
  return out;
}

function use() {
  Survivor.getWeapon().use();
  if (Survivor.getWeapon().getDurability() == 0) {
    sleep(1000);
    println("Your " + Survivor.getWeapon().getName() + " broke!");
    //Remove broken weapon
    for (var i = 0; i < inventory.length; i++) {
      if (inventory[i].getIsHolding()) {
        inventory[i].setIsHolding(false);
        inventory.splice(i,1);
        break;
      }
    }
    if (countWeapons() == 1) {
      inventory[0].setIsHolding(false);
      //Hold only available weapon
      for (var i = 0; i < inventory.length; i++) {
        if (inventory[i].getType().equals("weapon") && !inventory[i].getIsHolding()) {
          inventory[i].setIsHolding(true);
          Survivor.setWeapon(inventory[i]);
          break;
        }
      }

      checkBattle();
    } else if (countWeapons() > 0) {
      println("");
      startAtUse();
    } else {
      //Default to Fist
      println("You don't have any weapons left!");
      inventory[0].setIsHolding(true);
      Survivor.setWeapon(inventory[0]);
      println("");

      checkBattle();
    }
  } else {
    checkBattle();
  }
}

function startAtUse() {
  var counter = 1;
  var temp = [];
  var amt = 0;

  for (var i = 0; i < inventory.length; i++) {
    if (inventory[i].getType().equals("weapon")) {
      println("[" + counter + "] " + inventory[i].info());
      temp[counter-1] = i;
      counter++;
    }
  }
  println("Which item(#) would you like to wield? Please input number.");

  Session.setAtUse(true);
}

function atUse(input) {
  //atUse
  Session.setAtUse(false);

  var counter = 1;
  var temp = [];
  var amt = 0;

  if (isInt(input)) {
    amt = parseInt(input);
    if (amt > countWeapons() || amt <= 0 || amt > 3) {
      println("Invalid input");

      startAtUse();
    } else {
      inventory[(temp[amt-1])].setIsHolding(true);
      Survivor.setWeapon(inventory[(temp[amt-1])]);

      checkBattle();
    }
  } else {
    println("Invalid input");

    startAtUse();
  }
}

function gainExp() {
  println("You gained " + Survivor.getExpStep() + " experience!");
  Survivor.gainExp(Survivor.getExpStep());
  //If Survivor has more than 100 exp
  if (Survivor.getExp() >= Survivor.getMaxExp()) {
    Survivor.levelUp();
    sleep(500);
    println("You leveled up! You are now level " + Survivor.getLevel() +"!");
    println();
    sleep(1000);
    println("You can increase one of your stats!");
    startAtLevel();
  } else {
    finishGainExp();
  }
}

function finishGainExp() {
  println("");
  //10% chance of dropping item that isn't Fist
  if (!Zombie.getWeapon().getName().toLowerCase().equals("fist") && rollD(10) == 10) {
    sleep(1000);
    println("The zombie dropped its " + Zombie.getWeapon().getName().toLowerCase() + "!");
    findWeapon(Zombie.getWeapon());
  } else {
    finishBattle();
  }
}

function startAtLevel() {
  //Beginning of atLevel
  println("Max Health: " + Survivor.getMaxHP() + " --> " + (Survivor.getMaxHP() + 25));
  println("Scavenging: " + Survivor.getScavenging() + " --> " + (Survivor.getScavenging() + 1));
  println("Strength:   " + Survivor.getStrength() + " --> " + (Survivor.getStrength() + 1));
  println("Agility:    " + Survivor.getAgility() + " --> " + (Survivor.getAgility() + 1));
  println("Which stat would you like to increase?");
  println("[Health]   [Scavenging]   [Strength]   [Agility]");

  Session.setAtLevel(true);
}

function atLevel(input) {
  //atLevel
  Session.setAtLevel(false);
  println("");

  switch (input.toLowerCase().trim()){
    case ("health"):
      //Increase maximum health by 25
      Survivor.raiseStat(1);
      sleep(500);
      println("Your maximum health increased to " + Survivor.getMaxHP() + "!");
      finishGainExp();
      break;
    case ("scavenging"):
      //Increase scavenging by 1
      Survivor.raiseStat(2);
      sleep(500);
      println("Your scavenging increased to " + Survivor.getScavenging() + "!");
      finishGainExp();
      break;
    case ("strength"):
      //Increase strength by 1
      Survivor.raiseStat(3);
      sleep(500);
      println("Your strength increased to " + Survivor.getStrength() + "!");
      finishGainExp();
      break;
    case ("agility"):
      //Increase agility by 1
      Survivor.raiseStat(4);
      sleep(500);
      println("Your agility increased to " + Survivor.getAgility() + "!");
      finishGainExp();
      break;
    default:
      println("Invalid input");

      startAtLevel();
      break;
  }
}

function pillage() {
  sleep(500);
  println("You clear out the " + places.get(getLocationIndex()).getName().toLowerCase() + " for supplies.");
  println();
  //Scavenge 3 times without fail
  for (var i = 0; i < 3; i++) {
    if (rollD(20) > places.get(getLocationIndex()).getWeapon()-(special2()/2)) {
      var a = rollD(items.size()-2);
      sleep(1000);
      var name = items.get(a).getName().toLowerCase();
      print("You found ");
      if (name.endsWith("s")) {
        print("some " + name + "!<br>");
      } else if (name.startsWith("a") || name.startsWith("e") || name.startsWith("i") || name.startsWith("o") || name.startsWith("u")) {
        print("an " + name + "!<br>");
      } else {
        print("a " + name + "!<br>");
      }
      println();
      findWeapon(items.get(a));
    } else if (rollD(20) > places.get(getLocationIndex()).getFood()-special2()){
      var newItem = items.get(items.size()-1);
      sleep(1000);
      println("You found some food!");
      inventory.add(newItem);
    //Roll again if failed
    } else {
      i--;
    }
  }
}

function bozonSpecial() {
  //Ensures that the Player doesn't sleep too much just to gain days
  println();
  sleep(2000);
  println("As you wake up from a deep sleep, you hear banging on the outside of your shelter.");
  sleep(3000);
  println("You assume that it will go away. The sound begins to die down.");
  sleep(3000);
  println("Suddenly, you see your bunker door fly across the room with a loud \"Bang!\" against the wall.");
  sleep(3000);
  println("Standing in the doorway, you see a towering undead figure.");
  sleep(3000);
  println("The Zombie charges at you as you hastily ready your " + Survivor.getWeapon().getName().toLowerCase() + "!");
  sleep(3000);
  Session.setTemp(Session.getDayMod());
  var temp = Session.getTemp();
  var dayMod = Session.getDayMod();
  //Alters current dayMod to BS proportions
  Session.setDayMod(Survivor.getStrength()+5);
  //Creates BozonSpecial Zombie
  Zombie.set(rollD(1),rollD((((Survivor.getMaxHP()*.75)+.5)+temp),60,0),zombieWpns[(rollD((zombieWpns.length-1))],rollD(dayMod,temp,0),rollD((Survivor.getAgility()+dayMod),temp,0));
  Session.setAtBS(true);
  battle();
}

function finishBozonSpecial() {
  Session.setDayMod(Session.getTemp());
  //If Player somehow survived that
  if (Survivor.getCont()) {
    Session.setSlept(0);
    sleep(2000);
    println("You breathe heavily, staring at the large corpse. You ask yourself:");
    sleep(3000);
    println("\"Was it really worth it?\"");
    sleep(1000);

    startChoose();
  }
}

function findWeapon(item) {
  Session.setDiscarded(false);
  var newItem = new Item(item.getName(),item.getMaxDamage(),item.getHeal(),item.getType(),item.getProbability(),item.getMaxDurability(),item.getPhrase());
  newItem.setDurability(rollD(newItem.getMaxDurability(),(int)((newItem.getMaxDurability()/2)+.5),0));
  Session.setNewItem(newItem);
  //If Player is already carrying 3 weapons
  if (countWeapons() >= 3) {
    startAtSureDiscard();
  } else {
    sleep(1000);
    println("You take the " + newItem.getName().toLowerCase() + ".");
    println("");
    inventory.push(newItem);

    finishFindWeapon();
  }
}

function finishFindWeapon() {
  var newItem = Session.getNewItem();

  if (!Session.getDiscarded()) {
    sleep(500);
    println(newItem.info());
    println();

    //If Player has no weapon
    if (inventory[0].getIsHolding()) {
      inventory[0].setIsHolding(false);
      //Hold new weapon
      for (var i = 0; i < inventory.length; i++) {
        if (inventory[i].getType().equals("weapon") && !inventory[i].getIsHolding()) {
          inventory[i].setIsHolding(true);
          Survivor.setWeapon(inventory[i]);
          break;
        }
      }

      finishScavenge();
    //If Player isn't holding the new weapon
    } else if (!inventory[(inventory.length-1)].getIsHolding()) {
      startAtWield();
    }
  } else {

  }
}

function startAtSureDiscard() {
  var newItem = Session.getNewItem();

  println(newItem.info());
  println("");
  sleep(1000);
  println("You can't carry any more weapons!");
  sleep(500);
  println("You are currently have: ");
  sleep(1000);
  var counter = 1;
  for (var i = 0; i < inventory.length; i++) {
    if (inventory[i].getType().equals("weapon")) {
      println("[" + counter + "] " + inventory[i].info());
      counter++;
    }
  }

  sleep(500);
  println("Would you like to discard a weapon?<br>[Yes] [No]");

  Session.setAtSureDiscard(true);
}

function atSureDiscard(input) {
  //atSureDiscard
  Session.setAtSureDiscard(false);
  println("");

  var newItem = Session.getNewItem();

  if (input.toLowerCase().trim().equals("yes")) {
    startAtDiscard();
  } else if (input.toLowerCase().trim().equals("no")) {
    //Discard new weapon
    Session.setDiscarded(true);
    sleep(500);
    println("You discard the " + newItem.getName().toLowerCase() + ".");
    println("");

    finishFindWeapon();
  } else {
    println("Invalid input");

    startAtSureDiscard();
  }
}

function startAtDiscard() {
  var amt = 0;
  var temp = [];
  var counter = 1;
  for (var i = 0; i < inventory.length; i++) {
    if (inventory[i].getType().equals("weapon")) {
      println("[" + counter + "] " + inventory[i].info());
      temp[counter-1] = i;
      counter++;
    }
  }
  println("Which item(#) would you like to discard? Please input number.");

  Session.setAtDiscard(true);
}

function atDiscard(input) {
  //atDiscard
  Session.setAtDiscard(false);
  println("");

  var amt = 0;
  var temp = [];
  var counter = 1;
  for (var i = 0; i < inventory.length; i++) {
    if (inventory[i].getType().equals("weapon")) {
      temp[counter-1] = i;
      counter++;
    }
  }
  var newItem = Session.getNewItem();

  if (isInt(input)) {
    amt = parseInt(input, 10);

    if (amt > countWeapons() || amt <= 0 || amt > 3) {
      println("Invalid input");

      startAtDiscard();
    } else {
      inventory.push(newItem);;
      //Hold new weapon if held weapon is discarded
      if (inventory[temp[amt-1]].getIsHolding()) {
        inventory[temp[amt-1]].setIsHolding(false);
        inventory[(inventory.length-1)].setIsHolding(true);
        Survivor.setWeapon(inventory[(inventory.length-1)]);
      }
      sleep(500);
      println("You discard your " + inventory.[temp[amt-1]].getName().toLowerCase() + " and take the " + newItem.getName().toLowerCase() + ".");
      inventory.remove(temp[amt-1]);

      finishFindWeapon();
    }
  } else {
    println("Invalid input");

    startAtDiscard();
  }
}

function startAtWield() {
  sleep(500);
  println("You currently have: ");
  sleep(1000);
  println(Survivor.getWeapon().info());
  println("");
  sleep(1000);
  println("Would you like to wield your new " + inventory[(inventory.length-1)].getName() + "?<br>[Yes] [No]");

  Session.setAtWield(true);
}

function atWield(input) {
  //atWield
  Session.setAtWield(false);
  println("");

  if (input.toLowerCase().trim().equals("yes")) {
    for (var i = 0; i < inventory.length; i++) {
      if (inventory[i].getIsHolding()) {
        inventory[i].setIsHolding(false);
        break;
      }
    }
    inventory[(inventory.length-1)].setIsHolding(true);
    Survivor.setWeapon(inventory.get(inventory.length-1));

    finishScavenge();
  } else if (input.toLowerCase().trim().equals("no")) {
    finishScavenge();
  } else {
    println("Invalid input");

    startAtWield();
  }
}

function countFood() {
  var count = 0;
  //Count food
  for (var i = 0; i < inventory.length; i++) {
    if (inventory[i].getType().equals("food")) {
      count++;
    }
  }
  return count;
}

function countWeapons() {
  var count = 0;
  //Count weapons
  for (var i = 0; i < inventory.length; i++) {
    if (inventory[i].getType().equals("weapon")) {
      count++;
    }
  }
  return count;
}

function getLocationIndex() {
  var index = -1;
  //Get current location
  for (var i = 0; i < places.length; i++) {
    if (places[i].getIsHere()) {
      index = i;
    }
  }
  return index;
}

public static void leaderboard() throws FileNotFoundException {
  ArrayList<Ranking> leaderboard = new ArrayList<Ranking>();

  //Reads leaderboard from file
  Scanner read = new Scanner(new File("Leaderboard.txt"));
  read.useDelimiter(",");
  for (int i = 0; i < 5; i++) {
    if (read.hasNextLine()) {
      int rank = read.nextInt();
      String character = read.next();
      int level = read.nextInt();
      int days = read.nextInt();
      String initials = read.next();
      leaderboard.add(new Ranking(rank,character,level,days,initials));
      read.nextLine();
    } else {
      leaderboard.add(new Ranking(0,"VACANT",0,0,"N/A"));
    }
  }
  read.close();

  //Checks if Player can go on the board
  boolean onBoard = false;
  for (int i = 0; i < leaderboard.size(); i++) {
     if (day >= leaderboard.get(i).getDays()) {
      if (Survivor.getLevel() >= leaderboard.get(i).getLevel()) {
        onBoard = true;
      }
    }
  }

  //If Player can go on the board
  String initials = "";
  if (onBoard) {
    println("Congratulations! You made it onto the leaderboard!");
    do {
      cont = true;
      println("Would you like to add your initials?<br>[Yes] [No]");
      input = in.nextLine();
      println();
      if (input.toLowerCase().trim().equals("yes")) {
        do {
          cont = true;
          println("Input your initials.");
          input = in.nextLine();
          if (input.length() != 3) {
            println("Invalid input");
            cont = false;
          } else {
            initials = input.toUpperCase();
          }
        } while (!cont);
      } else if (input.toLowerCase().trim().equals("no")) {
        initials = "N/A";
      } else {
        println("Invalid input");
        cont = false;
      }
    } while (!cont);

    //Assigns Player a spot on the leaderboard
    int rank = 1;
    int newRank = 0;
    int newIndex = 0;
    for (int i = 0; i < leaderboard.size(); i++) {
      if (day > leaderboard.get(i).getDays() && newRank == 0) {
        newRank = rank;
        newIndex = i;
        rank++;
      } else if (day == leaderboard.get(i).getDays() && newRank == 0) {
        if (Survivor.getLevel() >= leaderboard.get(i).getLevel()) {
          newRank = rank;
          newIndex = i;
          rank++;
        }
      }
      leaderboard.get(i).setRank(rank);
      rank++;
    }
    if (newIndex != 0) {
      leaderboard.add(newIndex,new Ranking(newRank,Survivor.getName(),Survivor.getLevel(),day,initials));
    } else {
      leaderboard.add(new Ranking(leaderboard.size()+1,Survivor.getName(),Survivor.getLevel(),day,initials));
    }
    if (leaderboard.size() > 5) {
      leaderboard.remove(leaderboard.size()-1);
    }

    //Writes new leaderboard to txt file
      try {
          FileWriter writer = new FileWriter(new File("Leaderboard.txt"));
      String output = "";
      for (int i = 0; i < leaderboard.size(); i++) {
        output += leaderboard.get(i).getRank() + ",";
        output += leaderboard.get(i).getCharacter() + ",";
        output += leaderboard.get(i).getLevel() + ",";
        output += leaderboard.get(i).getDays() + ",";
        output += leaderboard.get(i).getInitials() + ",";
        output += "<br>";
      }
      writer.write(output);
      writer.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  //Outputs leaderboard
  println("<br>---------------Leaderboard---------------<br>");
  println();
  println("Rank\tCharacter\tDays\tInitials");
  for (int i = 0; i < leaderboard.size(); i++) {
    println(leaderboard.get(i).toString());
  }

}

function checkFor(required) {
  var = input.toLowerCase().trim();
  //Check user input
  if (input.contains(required)) {
    return true;
  }
  return false;
}

function special1() {
  var out = 0;
  //If Player is John
  if (Survivor.getSpecial() == 1) {
    if (rollD(6) > 4) {
      out = 5;
    }
  }
  return out;
}

function special2() {
  var out = 0;
  //If Player is Linda
  if (Survivor.getSpecial() == 2) {
    out = 4;
  }
  return out;
}

function special3() {
  var out = 1;
  //If Player is Jeremy
  if (Survivor.getSpecial() == 3) {
    out = 2;
  }
  return out;
}

function special4() {
  var out = 0;
  //If Player is Sarah
  if (Survivor.getSpecial() == 4) {
    if (rollD(6) > 4) {
      out = 10;
    }
  }
  return out;
}

function special5() {
  //If Player is Tommy
  if (Survivor.getSpecial() == 5 && Session.getAtkCount() == 0 && Zombie.getCont()) {
    Session.setAtkRepeat(true);
    Session.setAtkCount(Session.getAtkCount()++);
  } else {
    Session.setAtkRepeat(false);
  }
}

function randomZombie() {
  //Creates Zombie(searching,health,weapon,strength,agility)
  var dayMod = Session.getDayMod();
  Zombie.set(rollD(4+dayMod,(dayMod/2),0),rollD((40+(5*dayMod)),20,0),zombieWpns.get((rollD(zombieWpns.size()))-1),rollD(dayMod,(dayMod/2),0),rollD((dayMod),(dayMod/2),0));
}

function rollD(max) {
  //Roll from 1-max inclusive
  return (Math.random()*((max - 1) + 1)) + 1;
}

function rollD(max, mod) {
  //Roll from 1-max inclusive + modifier
  return ((Math.random()*((max - 1) + 1)) + 1) + mod;
}

function rollD(max, min, mod) {
  //Roll from min-max inclusive + modifier
  return ((Math.random()*((max - min) + 1)) + min) + mod;
}

/*
  _id: cookie#,
  cont: false,
  day: 1,
  turn: 0,
  actions: 0,
  dayMod: 0,
  slept: 0,
  survivor: {
    name: "John",
    maxHP: 100,
    health: maxHP,
    weapon: {
      name: "Fist",
      maxDamage: 4,
      heals: 0,
      type: "default",
      probability: -1,
      maxDurability: -1,
      durability: -1,
      phrase: ""
    },
    exposure: 10,
    scavenging: 2,
    strength: 0,
    agility: 4,
    special: 0,
    level: 1,
    maxExp: 100,
    exp: 0,
    expStep: 50,
    cont: true
  },
  inventory: {
    item: {
      name: "Fist",
      maxDamage: 4,
      heals: 0,
      type: "default",
      probability: -1,
      maxDurability: -1,
      durability: -1,
      phrase: ""
    }
  },
  places: {
    location: {
      name: "",
      maxEncounters: 0,
      encounters: 0,
      maxSupplies: 0,
      supplies: 0,
      supply: 0,
      food: 0,
      weapon: 0,
      scavengable: true,
      days: 0,
      minimum: 0,
      reset: false,
      isHere: false
    }
  },
  zombie: {
    searching: 0,
    maxHP: 30,
    health: maxHP,
    weapon: {
      name: "Fist",
      maxDamage: 4,
      heals: 0,
      type: "weapon",
      probability: -1,
      maxDurability: -1,
      durability: -1,
      phrase: ""
    },
    strength: 0,
    agility: 0,
    cont: true
  }

}





*/
