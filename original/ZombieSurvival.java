/* AP Computer Science Principles Create Project
 *
 * Zombie Survival
 * Text-based survival adventure game
 *
 * Written by: Nathan Schwartz
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ZombieSurvival {

	//Initializing important variables
	private static Scanner in = new Scanner(System.in);				//Get user input
	private static String input = "";								//User input

	private static boolean cont = false;							//Continue for do loops and ending game
	private static int day = 1;										//Number of days passed
	private static int turn = 0;									//Turn counter, checks in/out of battle
	private static int actions = 0;									//Number of turns in current day
	private static int dayMod = day/7;								//Zombies get stronger every week
	private static int slept = 0;									//Amount of times Player slept

	private static Survivor Survivor = new Survivor();				//Player
	private static Zombie Zombie = new Zombie();					//Zombie
	private static boolean battleCont = false;						//Continue for battle
	private static boolean atkRepeat = false;						//Tommy attacks twice
	private static int atkCount = 0;								//Number of times Tommy attacked
	private static boolean back = false;							//Previous menu
	private static int runMod = 0;									//Chance of escaping

	private static ArrayList<Location> places = new ArrayList<>();	//Available locations
	private static Location store = new Store();					//Supermarket
	private static Location school = new School();					//Blue Mill High School
	private static Location library = new Library();				//Central Library
	private static Location mall = new Mall();						//Shopping Mall
	private static Location armory = new Armory();					//City Police Department
	private static Location shelter = new Shelter();				//Shelter

	private static ArrayList<Item> inventory = new ArrayList<>();	//Items Player is carrying
	private static ArrayList<Item> items = new ArrayList<>();		//Items in the game
	private static ArrayList<Item> zombieWpns = new ArrayList<>();	//Items Zombies can hold

	public static void main(String[] args) throws IOException, InterruptedException {

        //Initializing places
		places.add(store);
		places.add(school);
		places.add(library);
		places.add(mall);
		places.add(armory);
		places.add(shelter);

		//Importing items from txt files
		Scanner scanner = new Scanner(ZombieSurvival.class.getResourceAsStream("/Item.txt"));
		scanner.useDelimiter(",");
		while (scanner.hasNextLine()) {
			String n = scanner.next();
			int dmg = scanner.nextInt();
			int heal = scanner.nextInt();
			String t = scanner.next();
			int prob = scanner.nextInt();
			int durable = scanner.nextInt();
			String phrase = scanner.next();
			for (int i = 0; i < prob; i++) {
				items.add(new Item(n,dmg,heal,t,prob,durable,phrase));
				if (prob <= 0) {
					prob = 1;
				}
			}
			scanner.nextLine();
		}

		scanner.close();

		//Importing ZombieWeapons from txt files
		scanner = new Scanner(ZombieSurvival.class.getResourceAsStream("/ZombieWeapons.txt"));
		scanner.useDelimiter(",");

		while (scanner.hasNextLine()) {
			String n = scanner.next();
			int dmg = scanner.nextInt();
			int heal = scanner.nextInt();
			String t = scanner.next();
			int prob = scanner.nextInt();
			int durable = scanner.nextInt();
			String phrase = scanner.next();
			for (int i = 0; i < prob; i++) {
				zombieWpns.add(new Item(n,dmg,heal,t,prob,durable,phrase));
			}
			scanner.nextLine();
		}

		scanner.close();

		//Creating initial inventory
		inventory.add(new Item("Fist",4,0,"default",-1,-1,""));
		inventory.add(new Item("Food",0,20,"food",1,-1,""));
		inventory.add(new Item("Food",0,20,"food",1,-1,""));
		inventory.add(new Item("Food",0,20,"food",1,-1,""));
		inventory.add(new Item("Food",0,20,"food",1,-1,""));
		inventory.add(new Item("Food",0,20,"food",1,-1,""));

		//Start
		System.out.println("Welcome to Zombie Survival!\n");

		//Main Menu
		do {
			System.out.println();

			//Initial instructions
			System.out.println("Type \"characters\" for a character list.\n"
					+ "Type \"stats [name]\" for character info.\n"
					+ "Type \"select [name]\" to select character.");
			//atMenu
			input = in.nextLine();
			System.out.println();

			//User response
			if (checkFor("characters")) {
				//List of characters
				System.out.println("Characters:\nJohn   Linda   Jeremy   "
						+ "Sarah   Tommy");
			} else if (input.indexOf(" ") != -1) {
				if (checkFor("stats")) {
					//Give stats for specified character
					Survivor.stats(input.substring(input.lastIndexOf("stats")+6,
						input.length()));
				} else if (checkFor("select")) {
					//Selects specified character
					Survivor.setCharacter(input.substring(input.lastIndexOf("select")+7,
						input.length()));
				}
			} else {
				System.out.println("Invalid input");
			}

			//Ensures that a character was chosen
			cont = Survivor.getCont();
			if (!cont) {
				System.out.println();
			}
		} while (!cont);

		//Adds default weapon to inventory
		inventory.add(Survivor.getWeapon());

		//Character description
		System.out.println(Survivor.getDesc());

		//Day cycle
		do {
			//Beginning of Day Cycle
			cont = true;
			System.out.println("\n----------\n  Day " + day + "\n----------\n");
			actions = 0;

			//Action cycle
			do {
				//Beginning of Action Cycle
				//3 actions per day

				//Choose cycle
				do {
					//Beginning of Choose Cycle
					//Choose an action
					cont = true;
					places.get(getLocationIndex()).check();


					System.out.println(Survivor.getName() + "   Weapon: " + Survivor.getWeapon().getName() + " (" + Survivor.getWeapon().getDurability() + "/" + Survivor.getWeapon().getMaxDurability() + ")   Health: (" + Survivor.getHealth() + "/" + Survivor.getMaxHP() + ")");
					System.out.println();
					//Actions if Player is in Shelter
					if (places.get(5).getIsHere()) {
						System.out.println("What do you want to do?\n"
								+ "[Travel] [Inventory] [Sleep]");
					//Otherwise
					} else {
						System.out.println("What do you want to do?\n"
								+ "[Scavenge] [Inventory] [Return]");
					}
					//atChoose
					input = in.nextLine();
					System.out.println();

					switch (input.toLowerCase().trim()) {
						case ("inventory"):
							//Inventory
							inventoryAction();
							break;
						case ("travel"):
							//Travel
							travelAction();
							break;
						case ("sleep"):
							//Sleep
							sleepAction();
							break;
						case ("return"):
							//Return
							returnAction();
							break;
						case ("scavenge"):
							//Scavenge
							scavengeAction();
							break;
						case ("end"):
							//Eject (debug option)
							Survivor.subHealth(Survivor.getHealth());
							break;
						default:
							//Invalid
							System.out.println("Invalid input");
							cont = false;
							break;
					}

					//Eject if Player is dead
					if (!Survivor.getCont()) {
						cont = true;
					}
					//End of Choose Cycle
				} while (!cont);
				actions++;
				System.out.println();

				//Eject if Player is dead
				if (!Survivor.getCont()) {
					actions = 3;
				}
				//End of Action Cycle
			} while (actions < 3);
			cont = true;

			//Eject if Player is dead
			if (!Survivor.getCont()) {
				cont = false;
			} else if (Survivor.getCont()) {
				//Increase day
				day++;
				//Zombies become stronger every week
				dayMod = day / 7;

				for (int i = 0; i < places.size()-1; i ++) {
					//Reset supply/encounter variables
					if (places.get(i).getReset()) {
						places.get(i).reset();
					}
					//Add day if no longer scavengable
					if (!places.get(i).getScavengable()) {
						places.get(i).addDays();
					} else {
						//If the Player is away from a location for a day, it will reset
						if (!places.get(i).getIsHere() && (places.get(i).getEncounters() > 0 || places.get(i).getSupplies() > 0) ) {
							places.get(i).setReset(true);
						} else {
							places.get(i).setReset(false);
						}
					}
					//Check if scavengable
					places.get(i).check();
				}
				//Reduce slept counter by 1
				if (slept != 0) {
					slept--;
				}
			}
			//End of Day Cycle
		} while (cont);

		//Player dies on the first day
		if (day == 1) {
			System.out.println("You died on the first day as " + Survivor.getName() + "! Maybe you should try again?");
		//Player survived more than 1 day
		} else {
			System.out.println("Congratulations! You survived " + (day) + " day(s) with " + Survivor.getName() + "!");
		}
		leaderboard();
		System.out.println();
		System.out.println("Written by: Nathan Schwartz");
		in.close();
	}

	public static void inventoryAction() throws InterruptedException {
		do {
			cont = true;
			back = false;

			//Display outside of battle
			if (turn == 0) {
				System.out.println(Survivor.getName() + "   Weapon: " + Survivor.getWeapon().getName() + " (" + Survivor.getWeapon().getDurability() + "/" + Survivor.getWeapon().getMaxDurability() + ")   Health: (" + Survivor.getHealth() + "/" + Survivor.getMaxHP() + ")");
			}
			System.out.println("You are holding (" + countWeapons() + ") weapon(s) and (" + countFood() + ") food.");
			System.out.println("Would you like to switch your weapon, eat food, or go back?");
			System.out.println("[Switch]   [Eat]   [Back]");
			//atInventory
			input = in.nextLine();
			System.out.println();

			switch (input.toLowerCase().trim()) {
				case ("switch"):
					//If Player doesn't have a weapon
					if (inventory.get(0).getIsHolding()) {
						if (countWeapons() == 1) {
							inventory.get(0).setIsHolding(false);
							for (int i = 0; i < inventory.size(); i++) {
								if (inventory.get(i).getType().equals("weapon") && !inventory.get(i).getIsHolding()) {
									inventory.get(i).setIsHolding(true);
									Survivor.setWeapon(inventory.get(i));
									break;
								}
							}
						} else {
							System.out.println("You aren't carrying any more weapons!");
							back = true;
						}
					//If Player only has one weapon
					} else if (countWeapons() == 1) {
						System.out.println("You're only carrying one weapon!");
						cont = false;
					//Otherwise
					} else {
						System.out.println("You are currently holding: ");
						System.out.println(Survivor.getWeapon().info());
						System.out.println();
						System.out.println("In your inventory: ");
						int counter = 1;
						for (int i = 0; i < inventory.size(); i++) {
							if (inventory.get(i).getType().equals("weapon") && !inventory.get(i).getIsHolding()) {
								System.out.println("[" + counter + "] " + inventory.get(i).info());
								counter++;
							}
						}

						//Verify
						do {
							System.out.println("Are you sure you want to switch?\n[Yes] [No]");
							//atSureSwitch
							input = in.nextLine();
							System.out.println();

							if (input.toLowerCase().trim().equals("yes")) {
								do {
									cont = true;
									int[] temp;
									int amt = 0;
									do {
										cont = true;
										temp = new int[countWeapons()-1];
										counter = 1;
										System.out.println("Which item(#) would you like? Please input number.");
										for (int i = 0; i < inventory.size(); i++) {
											if (inventory.get(i).getType().equals("weapon") && !inventory.get(i).getIsHolding()) {
												System.out.println("[" + counter + "] " + inventory.get(i).info());
												temp[counter-1] = i;
												counter++;
											}
										}

										try {
											//atSwitch
											amt = Integer.parseInt(in.nextLine());
											System.out.println();
											if (amt > countWeapons()-1 || amt <= 0 || amt > 3) {
												System.out.println("Invalid input");
												cont = false;
											}
										} catch (NumberFormatException e) {
											System.out.println("Invalid input");
											cont = false;
										} catch (ArrayIndexOutOfBoundsException e) {
											System.out.println("Invalid input");
											cont = false;
										}
									} while (!cont);

									//Unhold current weapon
									for (int i = 0; i < inventory.size(); i++) {
										if (inventory.get(i).getIsHolding()) {
											inventory.get(i).setIsHolding(false);
											break;
										}
									}

									//Hold new weapon
									inventory.get(temp[amt-1]).setIsHolding(true);
									Survivor.setWeapon(inventory.get(temp[amt-1]));
								} while (!cont);
							} else if (input.toLowerCase().trim().equals("no")) {
								back = true;
							} else {
								System.out.println("Invalid input");
								cont = false;
							}
						} while (!cont);
					}
					break;
				case ("eat"):
					//If Player has food
					if (countFood() > 0) {
						do {
							cont = true;
							System.out.println("You are carrying (" + countFood() + ") food.");
							System.out.println("Are you sure you want to eat food?\n[Yes] [No]");
							//atSureEat
							input = in.nextLine();
							System.out.println();

							if (input.toLowerCase().trim().equals("yes")) {
								eat();
							} else if (input.toLowerCase().trim().equals("no")) {
								back = true;
							} else {
								System.out.println("Invalid input");
								cont = false;
							}
						} while (!cont);
					} else {
						System.out.println("You don't have any more food!");
						cont = false;
					}
					break;
				case ("back"):
					//Back
					back = true;
					break;
				default:
					System.out.println("Invalid input");
					cont = false;
			}
		} while (!cont);
		//Back
		if (back) {
			cont = false;
		}
		//If outside of battle
		if (turn == 0 && !back) {
			actions--;
		}
	}

	public static void travelAction() throws InterruptedException {
		back = false;
		if (places.get(5).getIsHere()) {
			System.out.println("You have chosen to travel.");

			do {
				//Choose a location
				cont = true;
				System.out.println("Choose a location to go to.");
				for (int i = 0; i < places.size()-1; i ++) {
					if (!places.get(i).getIsHere()) {
						System.out.print("[" + places.get(i).toString() + "]   ");
					}
				}
				System.out.print("[Back]");
				System.out.println();
				//atTravel
				input = in.nextLine();
				System.out.println();

				switch (input.toLowerCase().trim()) {
					case ("store"):
						//If scavengable
						if (places.get(0).getScavengable()) {
							Thread.sleep(500);
							System.out.println("You leave the safety of your shelter and go to the Supermarket.");
							places.get(5).setIsHere(false);
							places.get(0).setIsHere(true);
							encounter();
						} else {
							System.out.println("The store isn't scavengable at the moment!");
							cont = false;
						}
						break;
					case ("school"):
						if (places.get(1).getScavengable()) {
							Thread.sleep(500);
							System.out.println("You leave the safety of your shelter and go to Blue Mill High School.");
							places.get(5).setIsHere(false);
							places.get(1).setIsHere(true);
							encounter();
						} else {
							System.out.println("The school isn't scavengable at the moment!");
							cont = false;
						}
						break;
					case ("library"):
						if (places.get(2).getScavengable()) {
							Thread.sleep(500);
							System.out.println("You leave the safety of your shelter and go to the Central Library.");
							places.get(5).setIsHere(false);
							places.get(2).setIsHere(true);
							encounter();
						} else {
							System.out.println("The library isn't scavengable at the moment!");
							cont = false;
						}
						break;
					case ("mall"):
						if (places.get(3).getScavengable()) {
							Thread.sleep(500);
							System.out.println("You leave the safety of your shelter and go to the Shopping Mall.");
							places.get(5).setIsHere(false);
							places.get(3).setIsHere(true);
							encounter();
						} else {
							System.out.println("The mall isn't scavengable at the moment!");
							cont = false;
						}
						break;
					case ("armory"):
						if (places.get(4).getScavengable()) {
							Thread.sleep(500);
							System.out.println("You leave the safety of your shelter and go to the City Police Department.");
							places.get(5).setIsHere(false);
							places.get(4).setIsHere(true);
							encounter();
						} else {
							System.out.println("The armory isn't scavengable at the moment!");
							cont = false;
						}
						break;
					case ("back"):
						back = true;
						break;
					default:
						System.out.println("Invalid input");
						cont = false;
						break;
				}
			} while (!cont);
		} else {
			System.out.println("Invalid input");
			cont = false;
		}
		//Back
		if (back) {
			cont = false;
		}
		Thread.sleep(1000);
	}

	public static void sleepAction() throws InterruptedException {
		//If player is in Shelter
		if (places.get(5).getIsHere()) {
			Thread.sleep(1000);
			System.out.println("You spend the night in your shelter recovering some injuries.");
			Thread.sleep(1000);
			//Player recovers 10% health
			Survivor.addHealth((int)((Survivor.getMaxHP()*.1)+.5));
			slept++;
			//Ensures Player doesn't sleep too much
			if (slept == 5) {
				//BozonSpecial (BS) Zombie battle
				bozonSpecial();
			}
		} else {
			System.out.println("Invalid input");
			cont = false;
		}
		Thread.sleep(1000);
		System.out.println();
	}

	public static void returnAction() throws InterruptedException {
		//If Player isn't in Shelter
		if (!places.get(5).getIsHere()) {
			//If location is scavengable
			if (places.get(getLocationIndex()).getScavengable()) {
				encounter();
			}
			Thread.sleep(500);
			System.out.println("You return to your shelter for safety.");
			places.get(getLocationIndex()).setIsHere(false);
			places.get(5).setIsHere(true);
		} else {
			System.out.println("Invalid input");
			cont = false;
		}
		Thread.sleep(1000);
	}

	public static void scavengeAction() throws InterruptedException {
		//If Player isn't in Shelter
		if (!places.get(5).getIsHere()) {
			//If location is scavengable
			if (places.get(getLocationIndex()).getScavengable()) {
				Thread.sleep(500);
				System.out.println("You search the " + places.get(getLocationIndex()).getName().toLowerCase() + " for supplies.");
				//If Player rolls high enough
				//Extra scavenging if Linda
				if (rollD(20,(Survivor.getScavenging())+special2()) > places.get(getLocationIndex()).getSupply()) {
					//If Player roll is high enough for weapons
					if (rollD(20) > places.get(getLocationIndex()).getWeapon()-(special2()/2)) {
						places.get(getLocationIndex()).addSupplies();
						int a = rollD(items.size()-2);
						Thread.sleep(1000);
						String name = items.get(a).getName().toLowerCase();
						System.out.print("You found ");
						if (name.endsWith("s")) {
							System.out.print("some " + name + "!\n");
						} else if (name.startsWith("a") || name.startsWith("e") || name.startsWith("i") || name.startsWith("o") || name.startsWith("u")) {
							System.out.print("an " + name + "!\n");
						} else {
							System.out.print("a " + name + "!\n");
						}
						findWeapon(items.get(a));
					//If Player roll is high enough for food
					} else if (rollD(20) > places.get(getLocationIndex()).getFood()-special2()){
						Item newItem = items.get(items.size()-1);
						places.get(getLocationIndex()).addSupplies();
						Thread.sleep(1000);
						System.out.println("You found some food!");
						inventory.add(newItem);
					} else {
						Thread.sleep(1000);
						System.out.println("You couldn't manage to find anything!");
					}
				} else {
					Thread.sleep(1000);
					System.out.println("You couldn't manage to find anything!");
				}
				//If scavenging or encounter is at limit
				places.get(getLocationIndex()).check();
				if (!places.get(getLocationIndex()).getScavengable()) {
					pillage();
				} else {
					encounter();
				}
			} else {
				System.out.println("The " + places.get(getLocationIndex()).getName().toLowerCase() + " isn't scavengable at the moment!");
				cont = false;
			}
		} else {
			System.out.println("Invalid input");
			cont = false;
		}
		Thread.sleep(1000);
	}

	public static void encounter() throws InterruptedException {
		//Randomly generate a zombie
		randomZombie();
		//If Player roll + exposure + Zombie searching is higher than required roll
		if (rollD(20,(Survivor.getExposure()+Zombie.getSearching())) > (places.get(getLocationIndex()).getMinimum()-dayMod)) {
			places.get(getLocationIndex()).addEncounters();
			Thread.sleep(1000);
			System.out.println("You've encountered a zombie!");
			battle();
			places.get(getLocationIndex()).check();
			//If scavenging or encounters is at limit
			if (Survivor.getCont() && !places.get(getLocationIndex()).getScavengable()) {
				System.out.println("You've cleared out all of the zombies in the area!");
				pillage();
			}
		} else {
			Thread.sleep(1000);
			System.out.println("You avoid encountering Zombies.");
			Thread.sleep(500);
			gainExp();
			System.out.println();
		}
	}

	public static void battle() throws InterruptedException {
		//atBattle
		Thread.sleep(1000);
		//Initiate battle
		System.out.println("\n---------- Battle Start -----------\n");
		turn = 1;
		runMod = 0;
		do {
			battleCont = true;
			System.out.println("\n----------\n  Turn " + turn + "\n----------\n");
			do {
				cont = true;
				System.out.println(Survivor.getName() + " (Lv. " + Survivor.getLevel() + ")   " + Survivor.getWeapon().toString() + "   Health: " + healthBar() + " (" + Survivor.getHealth() + "/" + Survivor.getMaxHP() + ")");
				System.out.println();
				System.out.println("Zombie   Weapon: " + Zombie.getWeapon().getName());
				System.out.println();
				System.out.println("What will you do?");
				System.out.println("[Fight]   [Inventory]   [Run]");
				//atBattleChoose
				input = in.nextLine();
				System.out.println();
				switch (input.toLowerCase().trim()) {
					case ("fight"):
						int damage = 0;
						do {
							//Extra damage if Sarah
							damage = rollD(Survivor.getWeapon().getMaxDamage(),(Survivor.getStrength()+special4()));

							//If Player rolls higher than Zombie
							if (rollD(20,Survivor.getAgility()) >= rollD(20,(Zombie.getAgility()))) {
								Thread.sleep(500);
								System.out.println("You strike the Zombie with your " + Survivor.getWeapon().getName().toLowerCase() + "!");
								//If Player can say phrase
								if (damage >= Zombie.getHealth() && !Survivor.getWeapon().getPhrase().equals("") && rollD(20) == 20) {
									Thread.sleep(1000);
									System.out.println("\"" + Survivor.getWeapon().getPhrase() + "\"");
								}
								Thread.sleep(1000);
								Zombie.subHealth(damage);
							} else {
								Thread.sleep(500);
								System.out.println("You attempt to strike the Zombie with your " + Survivor.getWeapon().getName().toLowerCase() + "!");
								//If Player can say phrase
								if (damage >= Zombie.getHealth() && !Survivor.getWeapon().getPhrase().equals("") && rollD(20) == 20) {
									Thread.sleep(1000);
									System.out.println("\"" + Survivor.getWeapon().getPhrase() + "\"");
								}
								//2/3 chance of hitting
								if (rollD(3,1,0) > 1) {
									Thread.sleep(1000);
									Zombie.subHealth(damage);
								//1/3 chance of missing
								} else {
									Thread.sleep(1000);
									System.out.println("The Zombie avoided your attack!");
								}
							}
							//Uses a durability
							use();
							// Attack twice if Tommy
							special5();
						} while (atkRepeat);
						System.out.println();
						atkCount = 0;
						break;
					case ("inventory"):
						inventoryAction();
						break;
					case ("run"):
						//If Player (minus Zombie) rolls higher than Zombie
						if (rollD(20,((Survivor.getAgility()-Zombie.getAgility())+runMod+special1())) >= rollD(20,(Zombie.getAgility()))) {
							Thread.sleep(500);
							System.out.println("You get away safely!");
							battleCont = false;
						} else {
							Thread.sleep(500);
							System.out.println("You can't escape!");
							//Chances of running away increase
							runMod++;
						}
						System.out.println();
						break;
					default:
						System.out.println("Invalid input");
						cont = false;
						break;
				}
			} while (!cont);
			//Eject if Player or Zombie is dead
			if (!Survivor.getCont() || !Zombie.getCont()) {
				battleCont = false;
			} else if (battleCont) {
				int damage = rollD(Zombie.getWeapon().getMaxDamage(),Zombie.getStrength());
				//If Player rolls less than Zombie
				if (rollD(20,Survivor.getAgility()) < rollD(20,(Zombie.getAgility()))) {
					Thread.sleep(500);
					System.out.println("The Zombie attacks you with its " + Zombie.getWeapon().getName().toLowerCase() + "!");
					Thread.sleep(1000);
					Survivor.subHealth(damage);
				} else {
					Thread.sleep(500);
					System.out.println("The Zombie attempts to attack you with its " + Zombie.getWeapon().getName().toLowerCase() + "!");
					//2/3 chance of hitting
					if (rollD(3,1,0) > 1) {
						Thread.sleep(1000);
						Survivor.subHealth(damage);
					//1/3 chance of missing
					} else {
						Thread.sleep(1000);
						System.out.println("You avoided the attack!");
					}
				}
				//Eject if Player is dead
				battleCont = Survivor.getCont();
				turn++;
			}
			Thread.sleep(1000);
		} while (battleCont);
		//Exp if Zombie is dead
		if (!Zombie.getCont()) {
			Thread.sleep(500);
			gainExp();
			//10% chance of dropping item that isn't Fist
			if (!Zombie.getWeapon().getName().toLowerCase().equals("fist") && rollD(10) == 10) {
				Thread.sleep(1000);
				System.out.println("The zombie dropped its " + Zombie.getWeapon().getName().toLowerCase() + "!");
				findWeapon(Zombie.getWeapon());
			}
		}
		//Outside of battle
		turn = 0;
	}

	public static String healthBar() {
		//Only looks correct if proper Windows version (otherwise it shows |????|)
		//One block per 20 HP
		String output = "|";
		int spaces = (int)((Survivor.getMaxHP()/20)+.5);
		int blocks = (int)((Survivor.getHealth()/20)+1);
		for (int i = 0; i <= spaces; i++) {
			if (blocks > 0) {
				output += "█";
				blocks--;
			} else {
				output += " ";
			}
		}
		output += "|";
		return output;
	}

	public static void eat() throws InterruptedException {
		int amt = 0;
		int heal = 0;
		do {
			cont = true;
			System.out.println("You have (" + Survivor.getHealth() + "/" + Survivor.getMaxHP() + ") health. You are carrying (" + countFood() + ") food.");
			System.out.println("How much food would you like to eat?");
			try {
				//atEat
				amt = Integer.parseInt(in.nextLine());
				System.out.println();
				if (amt > countFood() || amt <= 0) {
					System.out.println("Invalid input");
					cont = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input");
				cont = false;
			}
		} while (!cont);
		for (int x = 0; x < amt; x++) {
			for (int i = 0; i < inventory.size(); i++) {
				//Heal and remove eaten food
				if (inventory.get(i).getType().equals("food")) {
					heal += inventory.get(i).getHeal();
					inventory.remove(i);
					break;
				}
			}
		}
		//Heal 2x if Jeremy
		heal = heal*special3();
		Thread.sleep(500);
		Survivor.addHealth(heal);
		System.out.println();
	}

	public static void use() throws InterruptedException {
		Survivor.getWeapon().use();
		if (Survivor.getWeapon().getDurability() == 0) {
			Thread.sleep(1000);
			System.out.println("Your " + Survivor.getWeapon().getName() + " broke!");
			//Remove broken weapon
			for (int i = 0; i < inventory.size(); i++) {
				if (inventory.get(i).getIsHolding()) {
					inventory.get(i).setIsHolding(false);
					inventory.remove(i);
					break;
				}
			}
			if (countWeapons() == 1) {
				inventory.get(0).setIsHolding(false);
				//Hold only available weapon
				for (int i = 0; i < inventory.size(); i++) {
					if (inventory.get(i).getType().equals("weapon") && !inventory.get(i).getIsHolding()) {
						inventory.get(i).setIsHolding(true);
						Survivor.setWeapon(inventory.get(i));
						break;
					}
				}
			} else if (countWeapons() > 0) {
				System.out.println();
				int counter;
				int[] temp;
				int amt = 0;
				do {
					counter = 1;
					temp = new int[countWeapons()];
					cont = true;
					for (int i = 0; i < inventory.size(); i++) {
						if (inventory.get(i).getType().equals("weapon")) {
							System.out.println("[" + counter + "] " + inventory.get(i).info());
							temp[counter-1] = i;
							counter++;
						}
					}
					System.out.println("Which item(#) would you like to wield? Please input number.");

					try {
						//atUse
						amt = Integer.parseInt(in.nextLine());
						System.out.println();
						if (amt > countWeapons() || amt <= 0 || amt > 3) {
							System.out.println("Invalid input");
							cont = false;
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid input");
						cont = false;
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Invalid input");
						cont = false;
					}
				} while (!cont);
				inventory.get(temp[amt-1]).setIsHolding(true);
				Survivor.setWeapon(inventory.get(temp[amt-1]));
			} else {
				//Default to Fist
				System.out.println("You don't have any weapons left!");
				inventory.get(0).setIsHolding(true);
				Survivor.setWeapon(inventory.get(0));
			}
			System.out.println();
		}
	}

	public static void gainExp() throws InterruptedException {
		System.out.println("You gained " + Survivor.getExpStep() + " experience!");
		Survivor.gainExp(Survivor.getExpStep());
		//If Survivor has more than 100 exp
		if (Survivor.getExp() >= Survivor.getMaxExp()) {
			Survivor.levelUp();
			Thread.sleep(500);
			System.out.println("You leveled up! You are now level " + Survivor.getLevel() +"!");
			System.out.println();
			Thread.sleep(1000);
			System.out.println("You can increase one of your stats!");
			do {
				cont = true;
				System.out.println("Max Health: " + Survivor.getMaxHP() + " --> " + (Survivor.getMaxHP() + 25));
				System.out.println("Scavenging: " + Survivor.getScavenging() + " --> " + (Survivor.getScavenging() + 1));
				System.out.println("Strength:   " + Survivor.getStrength() + " --> " + (Survivor.getStrength() + 1));
				System.out.println("Agility:    " + Survivor.getAgility() + " --> " + (Survivor.getAgility() + 1));
				System.out.println("Which stat would you like to increase?");
				System.out.println("[Health]   [Scavenging]   [Strength]   [Agility]");
				//atLevel
				input = in.nextLine();
				System.out.println();
				switch (input.toLowerCase().trim()){
					case ("health"):
						//Increase maximum health by 25
						Survivor.raiseStat(1);
						Thread.sleep(500);
						System.out.println("Your maximum health increased to " + Survivor.getMaxHP() + "!");
						break;
					case ("scavenging"):
						//Increase scavenging by 1
						Survivor.raiseStat(2);
						Thread.sleep(500);
						System.out.println("Your scavenging increased to " + Survivor.getScavenging() + "!");
						break;
					case ("strength"):
						//Increase strength by 1
						Survivor.raiseStat(3);
						Thread.sleep(500);
						System.out.println("Your strength increased to " + Survivor.getStrength() + "!");
						break;
					case ("agility"):
						//Increase agility by 1
						Survivor.raiseStat(4);
						Thread.sleep(500);
						System.out.println("Your agility increased to " + Survivor.getAgility() + "!");
						break;
					default:
						System.out.println("Invalid input");
						cont = false;
						break;
				}
			} while (!cont);

		}
		System.out.println();
	}

	public static void pillage() throws InterruptedException {
		Thread.sleep(500);
		System.out.println("You clear out the " + places.get(getLocationIndex()).getName().toLowerCase() + " for supplies.");
		System.out.println();
		//Scavenge 3 times without fail
		for (int i = 0; i < 3; i++) {
			if (rollD(20) > places.get(getLocationIndex()).getWeapon()-(special2()/2)) {
				int a = rollD(items.size()-2);
				Thread.sleep(1000);
				String name = items.get(a).getName().toLowerCase();
				System.out.print("You found ");
				if (name.endsWith("s")) {
					System.out.print("some " + name + "!\n");
				} else if (name.startsWith("a") || name.startsWith("e") || name.startsWith("i") || name.startsWith("o") || name.startsWith("u")) {
					System.out.print("an " + name + "!\n");
				} else {
					System.out.print("a " + name + "!\n");
				}
				System.out.println();
				findWeapon(items.get(a));
			} else if (rollD(20) > places.get(getLocationIndex()).getFood()-special2()){
				Item newItem = items.get(items.size()-1);
				Thread.sleep(1000);
				System.out.println("You found some food!");
				inventory.add(newItem);
			//Roll again if failed
			} else {
				i--;
			}
		}
	}

	public static void bozonSpecial() throws InterruptedException {
		//Ensures that the Player doesn't sleep too much just to gain days
		System.out.println();
		Thread.sleep(2000);
		System.out.println("As you wake up from a deep sleep, you hear banging on the outside of your shelter.");
		Thread.sleep(3000);
		System.out.println("You assume that it will go away. The sound begins to die down.");
		Thread.sleep(3000);
		System.out.println("Suddenly, you see your bunker door fly across the room with a loud \"Bang!\" against the wall.");
		Thread.sleep(3000);
		System.out.println("Standing in the doorway, you see a towering undead figure.");
		Thread.sleep(3000);
		System.out.println("The Zombie charges at you as you hastily ready your " + Survivor.getWeapon().getName().toLowerCase() + "!");
		Thread.sleep(3000);
		int temp = dayMod;
		//Alters current dayMod to BS proportions
		dayMod = Survivor.getStrength()+5;
		//Creates BozonSpecial Zombie
		Zombie.set(rollD(1),rollD(((int)((Survivor.getMaxHP()*.75)+.5)+temp),60,0),zombieWpns.get(rollD((zombieWpns.size())-1)),rollD(dayMod,temp,0),rollD((Survivor.getAgility()+dayMod),temp,0));
		battle();
		dayMod = temp;
		//If Player somehow survived that
		if (Survivor.getCont()) {
			slept = 0;
			Thread.sleep(2000);
			System.out.println("You breathe heavily, staring at the large corpse. You ask yourself:");
			Thread.sleep(3000);
			System.out.println("\"Was it really worth it?\"");
			Thread.sleep(1000);
		}
	}

	public static void findWeapon(Item item) throws InterruptedException {
		boolean discarded = false;
		Item newItem = new Item(item.getName(),item.getMaxDamage(),item.getHeal(),item.getType(),item.getProbability(),item.getMaxDurability(),item.getPhrase());
		newItem.setDurability(rollD(newItem.getMaxDurability(),(int)((newItem.getMaxDurability()/2)+.5),0));
		//If Player is already carrying 3 weapons
		if (countWeapons() >= 3) {
			System.out.println(newItem.info());
			System.out.println();
			Thread.sleep(1000);
			System.out.println("You can't carry any more weapons!");
			Thread.sleep(500);
			System.out.println("You are currently have: ");
			Thread.sleep(1000);
			int counter = 1;
			for (int i = 0; i < inventory.size(); i++) {
				if (inventory.get(i).getType().equals("weapon")) {
					System.out.println("[" + counter + "] " + inventory.get(i).info());
					counter++;
				}
			}

			Thread.sleep(500);
			do {
				System.out.println("Would you like to discard a weapon?\n[Yes] [No]");
				//atSureDiscard
				input = in.nextLine();
				System.out.println();

				if (input.toLowerCase().trim().equals("yes")) {
					do {
						cont = true;
						int amt = 0;
						int[] temp;
						do {
							counter = 1;
							temp = new int[countWeapons()];
							cont = true;
							for (int i = 0; i < inventory.size(); i++) {
								if (inventory.get(i).getType().equals("weapon")) {
									System.out.println("[" + counter + "] " + inventory.get(i).info());
									temp[counter-1] = i;
									counter++;
								}
							}
							System.out.println("Which item(#) would you like to discard? Please input number.");
							try {
								//atDiscard
								amt = Integer.parseInt(in.nextLine());
								System.out.println();
								if (amt > countWeapons() || amt <= 0 || amt > 3) {
									System.out.println("Invalid input");
									cont = false;
								}
							} catch (NumberFormatException e) {
								System.out.println("Invalid input");
								cont = false;
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid input");
								cont = false;
							}
						} while (!cont);
						inventory.add(newItem);;
						//Hold new weapon if held weapon is discarded
						if (inventory.get(temp[amt-1]).getIsHolding()) {
							inventory.get(temp[amt-1]).setIsHolding(false);
							inventory.get(inventory.size()-1).setIsHolding(true);
							Survivor.setWeapon(inventory.get(inventory.size()-1));
						}
						Thread.sleep(500);
						System.out.println("You discard your " + inventory.get(temp[amt-1]).getName().toLowerCase() + " and take the " + newItem.getName().toLowerCase() + ".");
						inventory.remove(temp[amt-1]);
					} while (!cont);
				} else if (input.toLowerCase().trim().equals("no")) {
					//Discard new weapon
					discarded = true;
					Thread.sleep(500);
					System.out.println("You discard the " + newItem.getName().toLowerCase() + ".");
					System.out.println();
				} else {
					System.out.println("Invalid input");
					cont = false;
				}
			} while (!cont);
		} else {
			Thread.sleep(1000);
			System.out.println("You take the " + newItem.getName().toLowerCase() + ".");
			System.out.println();
			inventory.add(newItem);
		}
		if (!discarded) {
			Thread.sleep(500);
			System.out.println(newItem.info());
			System.out.println();

			//If Player has no weapon
			if (inventory.get(0).getIsHolding()) {
				inventory.get(0).setIsHolding(false);
				//Hold new weapon
				for (int i = 0; i < inventory.size(); i++) {
					if (inventory.get(i).getType().equals("weapon") && !inventory.get(i).getIsHolding()) {
						inventory.get(i).setIsHolding(true);
						Survivor.setWeapon(inventory.get(i));
						break;
					}
				}
			//If Player isn't holding the new weapon
			} else if (!inventory.get(inventory.size()-1).getIsHolding()) {
				Thread.sleep(500);
				System.out.println("You currently have: ");
				Thread.sleep(1000);
				System.out.println(Survivor.getWeapon().info());
				System.out.println();
				do {
					Thread.sleep(1000);
					System.out.println("Would you like to wield your new " + inventory.get(inventory.size()-1).getName() + "?\n[Yes] [No]");
					//atWield
					input = in.nextLine();
					System.out.println();
					cont = true;
					if (input.toLowerCase().trim().equals("yes")) {
						for (int i = 0; i < inventory.size(); i++) {
							if (inventory.get(i).getIsHolding()) {
								inventory.get(i).setIsHolding(false);
								break;
							}
						}
						inventory.get(inventory.size()-1).setIsHolding(true);
						Survivor.setWeapon(inventory.get(inventory.size()-1));
					} else if (!input.toLowerCase().trim().equals("no")) {
						System.out.println("Invalid input");
						cont = false;
					}
				} while (!cont);
			}
		}
	}

	public static int countFood() {
		int count = 0;
		//Count food
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getType().equals("food")) {
				count++;
			}
		}
		return count;
	}

	public static int countWeapons() {
		int count = 0;
		//Count weapons
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getType().equals("weapon")) {
				count++;
			}
		}
		return count;
	}

	public static int getLocationIndex() {
		int index = -1;
		//Get current location
		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).getIsHere()) {
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
			System.out.println("Congratulations! You made it onto the leaderboard!");
			do {
				cont = true;
				System.out.println("Would you like to add your initials?\n[Yes] [No]");
				//atSureInitial
				input = in.nextLine();
				System.out.println();
				if (input.toLowerCase().trim().equals("yes")) {
					do {
						cont = true;
						System.out.println("Input your initials.");
						//atInitial
						input = in.nextLine();
						if (input.length() != 3) {
							System.out.println("Invalid input");
							cont = false;
						} else {
							initials = input.toUpperCase();
						}
					} while (!cont);
				} else if (input.toLowerCase().trim().equals("no")) {
					initials = "N/A";
				} else {
					System.out.println("Invalid input");
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
					output += "\n";
				}
				writer.write(output);
				writer.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

		//Outputs leaderboard
		System.out.println("\n---------------Leaderboard---------------\n");
		System.out.println();
		System.out.println("Rank\tCharacter\tDays\tInitials");
		for (int i = 0; i < leaderboard.size(); i++) {
			System.out.println(leaderboard.get(i).toString());
		}

	}

	public static boolean checkFor(String required) {
		input = input.toLowerCase().trim();
		//Check user input
		if (input.contains(required)) {
			return true;
		}
		return false;
	}

	public static int special1() {
		int output = 0;
		//If Player is John
		if (Survivor.getSpecial() == 1) {
			if (rollD(6) > 4) {
				output = 5;
			}
		}
		return output;
	}

	public static int special2() {
		int output = 0;
		//If Player is Linda
		if (Survivor.getSpecial() == 2) {
			output = 4;
		}
		return output;
	}

	public static int special3() {
		int output = 1;
		//If Player is Jeremy
		if (Survivor.getSpecial() == 3) {
			output = 2;
		}
		return output;
	}

	public static int special4() {
		int output = 0;
		//If Player is Sarah
		if (Survivor.getSpecial() == 4) {
			if (rollD(6) > 4) {
				output = 10;
			}
		}
		return output;
	}

	public static void special5() {
		//If Player is Tommy
		if (Survivor.getSpecial() == 5 && atkCount == 0 && Zombie.getCont()) {
			atkRepeat = true;
			atkCount++;
		} else {
			atkRepeat = false;
		}
	}

	public static void randomZombie() {
		//Creates Zombie(searching,health,weapon,strength,agility)
		Zombie.set(rollD(4+dayMod,(dayMod/2),0),rollD((40+(5*dayMod)),20,0),zombieWpns.get((rollD(zombieWpns.size()))-1),rollD(dayMod,(dayMod/2),0),rollD((dayMod),(dayMod/2),0));
	}

	public static int rollD(int max) {
		//Roll from 1-max inclusive
		return (int)(Math.random()*((max - 1) + 1)) + 1;
	}

	public static int rollD(int max, int mod) {
		//Roll from 1-max inclusive + modifier
		return (int)((Math.random()*((max - 1) + 1)) + 1) + mod;
	}

	public static int rollD(int max, int min, int mod) {
		//Roll from min-max inclusive + modifier
		return (int)((Math.random()*((max - min) + 1)) + min) + mod;
	}
}
