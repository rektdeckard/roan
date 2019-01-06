package main;

import java.util.Map;
import java.util.Scanner;

public class Roan {

    // Declarations, inflate Player, Scene, Dice, etc.
    private static Player player = Player.builder().name("Player").maxHealth(40).build();
    private static WorldMap map = new WorldMap();
    private static Scene currentScene = map.getLocation(player.getXPos(), player.getYPos());
    private static Scanner scanner = new Scanner(System.in);
    private static Dice dice = new Dice();
    private static boolean playing = true;

    public static void main(String[] args) {
        // GAMEPLAY LOOP
        System.out.print("You open your eyes and rise slowly to your feet. ");
        System.out.print(currentScene.getDescription() + " ");
        System.out.println("You can't seem to remember who you are, or how you arrived here.");
        while(playing) {
            // Check for hostiles, enter combat or dialogue
            if (!currentScene.getCreatures().isEmpty()) {
                for (Creature creature : currentScene.getCreatures().values()) {
                    if (creature.isHostile()) {
                        combat();
                    }
                }
                // Dialogue
            }
            // Enter exploration mode
            explore();
        }
    }

    private static void explore() {
        boolean done = false;
        while(!done) {
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();
            if (input.contains("north") || input.equals("n")) {
                done = goNorth();
            } else if (input.contains("east") || input.equals("e")) {
                done = goEast();
            } else if (input.contains("south") || input.equals("s")) {
                done = goSouth();
            } else if (input.contains("west") || input.equals("w")) {
                done = goWest();
            } else if (input.contains("examine") || input.contains("look") || input.contains("search") || input.contains("investigate") || input.equals("x")) {
                done = examine();
            } else if (input.contains("take") || input.contains("pick") || input.contains("grab") || input.contains("get") || input.equals("t")) {
                // item name parsing done in take() function
                done = take(input);
            } else if (input.contains("climb") || input.equals("c")) {
                done = climb();
            } else if (input.contains("use")) {
                done = use();
            } else if (input.contains("attack") || input.contains("ambush")) {
                // Creature name parsing done in ambush() function
                ambush(input);
            } else if (input.contains("inventory") || input.contains("items") || input.equals("i") || input.equals("inv")) {
                inventory();
            } else if (input.contains("unequip")) {
                // Item name parsing done in equip()/unequip() functions
                player.unequip(input);
            } else if (input.contains("equip")) {
                player.equip(input);
            } else if (input.contains("location") || input.contains("where am i") || input.equals("l")) {
                location();
            } else if (input.equals("quit") || input.equals("exit")) {
                playing = false;
                done = true;
                quit();
            } else {
                System.out.println("That cannot be done.");
            }
        }

    }

    // Map movement

    private static boolean goNorth() {
        if (currentScene.north && player.yPos < map.mapHeight) {
            player.yPos += 1;
            currentScene = map.location[player.xPos][player.yPos];
            System.out.println(currentScene.description);
            return true;
        } else {
            System.out.println("That can't be done.");
            return false;
        }
    }

    private static boolean goEast() {
        if (currentScene.east && player.xPos < map.mapWidth) {
            player.xPos += 1;
            currentScene = map.location[player.xPos][player.yPos];
            System.out.println(currentScene.description);
            return true;
        } else {
            System.out.println("That can't be done.");
            return false;
        }
    }

    private static boolean goSouth() {
        if (currentScene.south && player.yPos > 0) {
            player.yPos -= 1;
            currentScene = map.location[player.xPos][player.yPos];
            System.out.println(currentScene.description);
            return true;
        } else {
            System.out.println("That can't be done.");
            return false;
        }
    }

    private static boolean goWest() {
        if (currentScene.west && player.xPos > 0) {
            player.xPos -= 1;
            currentScene = map.location[player.xPos][player.yPos];
            System.out.println(currentScene.description);
            return true;
        } else {
            System.out.println("That can't be done.");
            return false;
        }
    }

    // Combat

    private static void combat() {
        for (Map.Entry<String, Creature> combatant : currentScene.creatures.entrySet()) {
            System.out.println(combatant.getValue().description);
        }
        boolean fighting = !currentScene.creatures.isEmpty();
        // Loop while creatures are alive and has not fled
        while (fighting) {
            // Enemy attack phase (how does luck factor in??)
            for (Map.Entry<String, Creature> combatant : currentScene.creatures.entrySet()) {
                if (combatant.getValue().hostile) {
                    if (combatant.getValue().equippedWeapon != null) {
                        System.out.println("The " + combatant.getValue().name + " attacks.");
                        if (combatant.getValue().equippedWeapon.meleeDamage > 0) {
                            if (player.equippedArmor !=null) {
                                if ((dice.roll(20) + combatant.getValue().meleeAttack) + combatant.getValue().luck > player.equippedArmor.armor) {
                                    player.setHealth(-(dice.roll(combatant.getValue().equippedWeapon.meleeDamage)));
                                } else {
                                    System.out.println("The " + combatant.getValue().name + " missed.");
                                }
                            } else {
                                player.setHealth(-(dice.roll(combatant.getValue().equippedWeapon.meleeDamage)));
                            }
                        } else if (combatant.getValue().equippedWeapon.rangedDamage > 0) {
                            if (player.equippedArmor !=null) {
                                if ((dice.roll(20) + combatant.getValue().rangedAttack) + combatant.getValue().luck > player.equippedArmor.armor) {
                                    player.setHealth(-(dice.roll(combatant.getValue().equippedWeapon.rangedDamage)));
                                } else {
                                    System.out.println("The " + combatant.getValue().name + " missed.");
                                }
                            } else {
                                player.setHealth(-(dice.roll(combatant.getValue().equippedWeapon.rangedDamage)));
                            }
                        }
                    } else {
                        System.out.println("The " + combatant.getValue().name + " can't attack.");
                    }
                }
            }

            // Player attack phase
            System.out.print("# ");
            String input = scanner.nextLine().toLowerCase();
            // INSTAKILL for debugging
            if (input.contains("kill")) {
                for (Map.Entry<String, Creature> combatant : currentScene.creatures.entrySet()) {
                    if (input.contains(combatant.getValue().name.toLowerCase()) || (currentScene.creatures.size() == 1)) {
                        currentScene.inventory.putAll(combatant.getValue().inventory);
                        currentScene.creatures.remove(combatant.getKey());
                    } else {
                        System.out.println("There is no enemy by that name.");
                    }
                }
            } else if (input.contains("attack") || input.contains("hit") || input.equals("a")) {
                if (player.equippedWeapon != null) {
                    for (Map.Entry<String, Creature> combatant : currentScene.creatures.entrySet()) {
                        if (input.contains(combatant.getValue().name.toLowerCase()) || (currentScene.creatures.size() == 1)) {
                            Creature creature = combatant.getValue();
                            boolean alive = true;
                            if (player.equippedWeapon.meleeDamage > 0) {
                                if (creature.equippedArmor != null) {
                                    if (dice.roll(20) + player.meleeAttack + player.luck > creature.equippedArmor.armor) {
                                        alive = creature.setHealth(-(dice.roll(player.equippedWeapon.meleeDamage)));
                                    } else {
                                        System.out.println("Your attack misses.");
                                    }
                                } else {
                                    alive = creature.setHealth(-(dice.roll(player.equippedWeapon.meleeDamage)));
                                }
                            } else if (player.equippedWeapon.rangedDamage > 0) {
                                if (creature.equippedArmor != null) {
                                    if (dice.roll(20) + player.rangedAttack + player.luck > creature.equippedArmor.armor) {
                                        alive = creature.setHealth(-(dice.roll(player.equippedWeapon.rangedDamage)));
                                    } else {
                                        System.out.println("Your attack misses.");
                                    }
                                } else {
                                    alive = creature.setHealth(-(dice.roll(player.equippedWeapon.rangedDamage)));
                                }
                            }
                            if (!alive) {
                                currentScene.inventory.putAll(combatant.getValue().inventory);
                                currentScene.creatures.remove(combatant.getKey());
                            }
                        } else {
                            System.out.println("There is no enemy by that name.");
                        }
                    }
                } else {
                    System.out.println("You have no weapon equipped!");
                }
            } else if (input.contains("wait") || input.contains("nothing")) {
                System.out.println("You bide your time.");
            } else if (input.contains("run") || input.contains("flee")) {
                int combatantLuck = 0;
                for (Map.Entry<String, Creature> combatant : currentScene.creatures.entrySet()) {
                    combatantLuck += combatant.getValue().luck;
                }
                if ((dice.roll(20) + player.luck) > (dice.roll(20) + combatantLuck)) {
                    fighting = false;
                } else {
                    System.out.println("You failed to run from combat.");
                }
            } else if (input.equals("quit") || input.equals("exit")) {
                fighting = false;
                playing = false;
                quit();
            } else {
                System.out.println("That cannot be done in combat.");
            }

            // Check if all are dead
            if (currentScene.creatures.isEmpty()) {
                fighting = false;
            }
        }
        if (currentScene.creatures.isEmpty()) {
            System.out.println("You have defeated all enemies in the area.");
        } else {
            System.out.println("You successfully flee from combat.");
        }
    }

    private static void ambush(String input) {
        for (Map.Entry<String, Creature> creature : currentScene.creatures.entrySet()) {
            if (input.contains(creature.getValue().name.toLowerCase())) {
                if (!creature.getValue().hostile) {
                    Creature combatant = creature.getValue();
                    combatant.hostile = true;
                    currentScene.creatures.replace(creature.getKey(), combatant);
                }
                combat();
            } else {
                System.out.println("There is nobody here by that name.");
            }
        }
    }

    // Actions

    private static boolean examine() {
        if (currentScene.examine) {
            System.out.println(currentScene.detail);
            if (!currentScene.creatures.isEmpty()) {
                System.out.println("Creatures: " + currentScene.creatures.keySet());
            }
            if (!currentScene.inventory.isEmpty()) {
                System.out.println("Items: " + currentScene.inventory.keySet());
            }
        } else {
            System.out.println("You can't do that here.");
        }
        return false;
    }

    private static boolean use() {
        if (currentScene.use) {
            // Add "use" functionality
            return true;
        } else {
            System.out.println("You can't do that here.");
            return false;
        }
    }

    private static boolean take(String input) {
        if (currentScene.inventory.isEmpty()) {
            System.out.println("There is nothing here.");
        } else if (input.contains("all") || input.equals("t")) {
            player.inventory.putAll(currentScene.inventory);
            System.out.println("Received: " + currentScene.inventory.keySet());
            currentScene.inventory.clear();
        } else {
            boolean hasItem = false;
            for (Map.Entry<String, Item> sceneItem : currentScene.inventory.entrySet()) {
                if (input.contains(sceneItem.getValue().name.toLowerCase())) {
                    hasItem = true;
                    player.inventory.put(sceneItem.getKey(), sceneItem.getValue());
                    System.out.println(sceneItem.getValue().pickup);
                    System.out.println("Received: [" + sceneItem.getValue().name + "]");
                    //TODO use an iterator to remove the item, else will throw ConcurrentModificationException
                    currentScene.inventory.remove(sceneItem.getKey());
                }
            }
            if (!hasItem) {
                System.out.println("It's not here.");
            }
        }

// THIS IS UGLY AND WILL NOT EXTEND TO OTHER ITEMS
//
//        } else if (input.contains("bow") && currentScene.inventory.containsKey("bow")) {
//            System.out.println("You pick up the bow. It is smooth and light, and it's worn surface warms at your touch.");
//            player.inventory.put("bow", currentScene.inventory.get("bow"));
//            System.out.println("Received: [bow]");
//            currentScene.inventory.remove("bow");
//        } else if (input.contains("dagger") && currentScene.inventory.containsKey("dagger")) {
//            System.out.println("The dagger is heavier than it appears. Its cold metal stirs beneath the surface, reflecting the light like ripples on a pond.");
//            player.inventory.put("dagger", currentScene.inventory.get("dagger"));
//            System.out.println("Received: [dagger]");
//            currentScene.inventory.remove("dagger");
//        } else if (input.contains("bridle") && currentScene.inventory.containsKey("bridle")) {
//            System.out.println("You retrieve the trappings from the foul carcass. While the animal hide material is weathered and faded, it is surprisingly supple in your hands. Something stirs within you.");
//            player.inventory.put("bridle", currentScene.inventory.get("bridle"));
//            System.out.println("Received: [bridle]");
//            currentScene.inventory.remove("bridle");
//        } else if (input.contains("rope") && currentScene.inventory.containsKey("rope")) {
//            System.out.println("You coil the coarse rope into loops and sling it over your shoulder. This may come in handy later.");
//            player.inventory.put("rope", currentScene.inventory.get("rope"));
//            System.out.println("Received: [rope]");
//            currentScene.inventory.remove("rope");
//        }
        return false;
    }

    private static boolean climb() {
        if (currentScene.climb) {
            if (player.inventory.containsKey("rope")) {
                if (dice.roll(20) + player.luck >= 10) {
                    System.out.println(currentScene.succeed);
                    currentScene = map.location[player.xPos][player.yPos];
                    player.inventory.remove("rope");
                    System.out.println("Removed: [rope]");
                    player.xPos -= 1;
                    currentScene = map.location[player.xPos][player.yPos];
                    System.out.println(currentScene.description);
                    return true;
                } else {
                    System.out.println(currentScene.fail);
                    player.setHealth(-20);
                    return false;
                }
            } else {
                System.out.println("You don't have any rope.");
                return false;
            }
        } else {
            System.out.println("You can't do that here.");
            return false;
        }
    }

    private static void inventory() {
        System.out.println("Inventory: " + player.inventory.keySet());
        if (player.equippedWeapon != null) {
            System.out.println("Equipped Weapon: [" + player.equippedWeapon.name + "]");
        }
        if (player.equippedArmor != null) {
            System.out.println("Equipped Armor: [" + player.equippedArmor.name + "]");
        }
    }

    private static void location() {
        System.out.println("[" + player.xPos + "," + player.yPos + "] " + currentScene.name);
    }

    private static void quit() {
        // SIGN OFF
        System.out.println("Thank you for playing.");
        System.exit(0);
    }

}
