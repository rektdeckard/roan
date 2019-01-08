package roan;

import java.util.Scanner;

public class Roan {

    // GLOBAL DECLARATIONS & INSTANCES
    private static Player player = Player.builder().name("Player").maxHealth(40).build();
    private static WorldMap map = new WorldMap();
    private static Scene currentScene = map.getScene(player.getXPos(), player.getYPos());
    private static Scanner scanner = new Scanner(System.in);
    private static boolean playing = true;
    private static boolean alive = true;

    public static void main(String[] args) {

        // GAMEPLAY LOOP
        while(playing) {
            System.out.print("You open your eyes and rise slowly to your feet. You can't seem to remember who you are, or how you arrived here. ");
            while(alive) {
                // Check for hostiles, enter combat or dialogue
                if (!currentScene.getCreatures().isEmpty()) {
                    for (Creature creature : currentScene.getCreatures().values()) {
                        if (creature.isHostile()) {
                            alive = combat();
                        }
                        if (!alive) {
                            // TODO add dialog to restart or quit
                            quit();
                        }
                    }
                    if (!alive) {
                        quit();
                    }
                    // Dialogue
                }
                // Enter exploration mode
                System.out.println(currentScene.getDescription());
                alive = explore();
            }
        }
    }

    // EXPLORE
    private static boolean explore() {
        boolean done = false;
        while(!done) {
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();
            if (input.contains("northeast") || input.equals("ne")) {
                done = go(Direction.NE);
            } else if (input.contains("northwest") || input.equals("nw")) {
                done = go(Direction.NW);
            } else if (input.contains("southeast") || input.equals("se")) {
                done = go(Direction.SE);
            } else if (input.contains("north") || input.equals("n")) {
                done = go(Direction.N);
            } else if (input.contains("east") || input.equals("e")) {
                done = go(Direction.E);
            } else if (input.contains("south") || input.equals("s")) {
                done = go(Direction.S);
            } else if (input.contains("west") || input.equals("w")) {
                done = go(Direction.W);
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
        return true;
    }

    // MAP MOVEMENT
    // TODO lock player position to currentScene coordinates
    private static boolean go(Direction direction) {
        int x = player.getXPos();
        int y = player.getYPos();
        if (currentScene.getAllowed().contains(direction)) {
            if (direction == Direction.N) {
                currentScene = map.getScene(x, y + 1);
                player.setPos(x, y + 1);
            } else if (direction == Direction.NE) {
                currentScene = map.getScene(x + 1, y + 1);
                player.setPos(x + 1, y + 1);
            } else if (direction == Direction.E) {
                currentScene = map.getScene(x + 1, y);
                player.setPos(x + 1, y);
            } else if (direction == Direction.SE) {
                currentScene = map.getScene(x + 1, y - 1);
                player.setPos(x + 1, y - 1);
            } else if (direction == Direction.S) {
                currentScene = map.getScene(x, y - 1);
                player.setPos(x, y - 1);
            } else if (direction == Direction.SW) {
                currentScene = map.getScene(x - 1, y - 1);
                player.setPos(x - 1, y - 1);
            } else if (direction == Direction.W) {
                currentScene = map.getScene(x - 1, y);
                player.setPos(x - 1, y);
            } else if (direction == Direction.NW) {
                currentScene = map.getScene(x - 1, y + 1);
                player.setPos(x - 1, y + 1);
            }
            return true;
        } else {
            System.out.println("That can't be done.");
            return false;
        }
    }

    private static boolean go(Direction direction, Vertical vertical) {
        if (currentScene.getClimbDirection().equals(direction) && currentScene.getClimbVertical().equals(vertical)) {
            int x = player.getXPos();
            int y = player.getYPos();
            int z = player.getZPos();
            if (vertical == Vertical.UP) {
                z++;
            } else {
                z--;
            }
            if (direction == Direction.N) {
                currentScene = map.getScene(x, y + 1, z);
                player.setPos(x, y + 1, z);
            } else if (direction == Direction.NE) {
                currentScene = map.getScene(x + 1, y + 1, z);
                player.setPos(x + 1, y + 1, z);
            } else if (direction == Direction.E) {
                currentScene = map.getScene(x + 1, y, z);
                player.setPos(x + 1, y, z);
            } else if (direction == Direction.SE) {
                currentScene = map.getScene(x + 1, y - 1, z);
                player.setPos(x + 1, y - 1, z);
            } else if (direction == Direction.S) {
                currentScene = map.getScene(x, y - 1, z);
                player.setPos(x, y - 1, z);
            } else if (direction == Direction.SW) {
                currentScene = map.getScene(x - 1, y - 1, z);
                player.setPos(x - 1, y - 1, z);
            } else if (direction == Direction.W) {
                currentScene = map.getScene(x - 1, y, z);
                player.setPos(x - 1, y, z);
            } else if (direction == Direction.NW) {
                currentScene = map.getScene(x - 1, y + 1, z);
                player.setPos(x - 1, y + 1, z);
            }
            return true;
        } else {
            System.out.println("That can't be done.");
            return false;
        }
    }

    // COMBAT LOOP
    private static boolean combat() {
        for (Creature combatant : currentScene.getCreatures().values()) {
            System.out.println(combatant.getDescription());
        }
        // Loop while creatures are alive and has not fled. Return true if player still alive, else false.
        boolean fighting = !currentScene.getCreatures().isEmpty();
        while (fighting) {
            // Enemy attack phase
            for (Creature combatant : currentScene.getCreatures().values()) {
                if (combatant.isHostile()) {
                    alive = attackPlayer(combatant, player);
                    if (!alive) {
                        return false;
                    }
                }
            }
            // Player attack phase
            System.out.print("# ");
            String input = scanner.nextLine().toLowerCase();
            // INSTAKILL for debugging
            if (input.contains("kill")) {
                for (Creature combatant : currentScene.getCreatures().values()) {
                    if (input.contains(combatant.getName().toLowerCase()) || (currentScene.getCreatures().size() == 1)) {
                        currentScene.disposeOf(combatant);
                    } else {
                        System.out.println("There is no enemy by that name.");
                    }
                }
            } else if (input.contains("attack") || input.contains("hit") || input.equals("a")) {
                alive = attackCreature(input);
                if (!alive) {
                    return false;
                }
            } else if (input.contains("wait") || input.contains("nothing")) {
                System.out.println("You bide your time.");
            } else if (input.contains("run") || input.contains("flee")) {
                int combatantLuck = 0;
                for (Creature combatant : currentScene.getCreatures().values()) {
                    combatantLuck += combatant.getLuck();
                }
                if ((Dice.roll(20) + player.getLuck()) > (Dice.roll(20) + combatantLuck)) {
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
            if (currentScene.getCreatures().isEmpty()) {
                fighting = false;
            }
        }
        if (currentScene.getCreatures().isEmpty()) {
            System.out.println("You have defeated all enemies in the area.");
        } else {
            System.out.println("You successfully flee from combat.");
        }
        return true;
    }

    private static boolean attackPlayer(Creature combatant, Player player) {
        if (combatant.getEquippedWeapon() != null) {
            System.out.println(combatant.getName() + " attacks.");
            if (player.getEquippedArmor() != null) {
                if (Dice.roll(20) + combatant.getMeleeAttack() + combatant.getLuck() > player.getEquippedArmor().getArmor() + player.getLuck()) {
                    return player.damage(Dice.roll(combatant.getEquippedWeapon().getDamage()));
                } else {
                    System.out.println("The " + combatant.getName() + " missed.");
                }
            } else {
                return player.damage(Dice.roll(combatant.getEquippedWeapon().getDamage()));
            }
        } else {
            System.out.println(combatant.getName() + " can't attack.");
        }
        return true;
    }

    private static boolean attackCreature(String input) {
        if (player.getEquippedWeapon() != null) {
            for (Creature combatant : currentScene.getCreatures().values()) {
                if (input.contains(combatant.getName().toLowerCase()) || (currentScene.getCreatures().size() == 1)) {
                    if (combatant.getEquippedArmor() != null) {
                        if (Dice.roll(20) + player.getMeleeAttack() + player.getLuck() > combatant.getEquippedArmor().getArmor() + combatant.getLuck()) {
                            alive = combatant.damage(Dice.roll(player.getEquippedWeapon().getDamage()));
                        } else {
                            System.out.println("Your attack misses.");
                        }
                    } else {
                        alive = combatant.damage(Dice.roll(player.getEquippedWeapon().getDamage()));
                    }
                    if (!alive) {
                        currentScene.disposeOf(combatant);
                    }
                } else {
                    System.out.println("There is no enemy by that name.");
                }
            }
        } else {
            System.out.println("You have no weapon equipped!");
        }
        //TODO add parry/riposte/thorns? case where player is injured by attacking
        return true;
    }

    private static void ambush(String input) {
        for (Creature creature : currentScene.getCreatures().values()) {
            if (input.contains(creature.getName().toLowerCase())) {
                creature.setHostile(true);
                combat();
            } else {
                System.out.println("There is nobody here by that name.");
            }
        }
    }

    // ACTIONS

    private static boolean examine() {
        if (currentScene.canExamine()) {
            System.out.println(currentScene.getDetail());
            if (!currentScene.getCreatures().isEmpty()) {
                System.out.println("Creatures: " + currentScene.getCreatures().keySet());
            }
            if (!currentScene.getInventory().isEmpty()) {
                System.out.println("Items: " + currentScene.getInventory().keySet());
            }
        } else {
            System.out.println("You cannot see.");
        }
        return false;
    }

    private static boolean use() {
        if (currentScene.canUse()) {
            // TODO add use functionality
            return true;
        } else {
            System.out.println("You can't do that here.");
            return false;
        }
    }

    private static boolean take(String input) {
        if (currentScene.getInventory().isEmpty()) {
            System.out.println("There is nothing here.");
        } else if (input.contains("all") || input.equals("t")) {
            System.out.println("Received: " + currentScene.getInventory().keySet());
            player.putInventory(currentScene.getInventory());
            currentScene.clearInventory();
        } else {
            boolean hasItem = false;
            for (Item sceneItem : currentScene.getInventory().values()) {
                if (input.contains(sceneItem.getName().toLowerCase())) {
                    hasItem = true;
                    player.putInventory(sceneItem);
                    System.out.println("Received: [" + sceneItem.getName() + "]");
                    currentScene.removeInventory(sceneItem);
                }
            }
            if (!hasItem) {
                System.out.println("It's not here.");
            }
        }
        return false;
    }

    private static boolean climb() {
        if ((currentScene.getClimbVertical() == Vertical.UP) || (currentScene.getClimbVertical() == Vertical.DOWN)) {
            if (player.getInventory().containsKey("Rope")) {
                if (Dice.roll(20) + player.getLuck() >= 10) {
                    System.out.println(currentScene.getSucceed());
                    go(currentScene.getClimbDirection(), currentScene.getClimbVertical());
                    return true;
                } else {
                    System.out.println(currentScene.getFail());
                    player.damage(Dice.roll(20));
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
        if (!player.getInventory().isEmpty()) {
            System.out.println("Inventory: " + player.getInventory().keySet());
        } else {
            System.out.println("You have nothing to your name.");
        }
        if (player.getEquippedWeapon() != null) {
            System.out.println("Equipped Weapon: [" + player.getEquippedWeapon().getName() + "]");
        }
        if (player.getEquippedArmor() != null) {
            System.out.println("Equipped Armor: [" + player.getEquippedArmor().getName() + "]");
        }
    }

    private static void location() {
        System.out.println("[" + player.getXPos() + "," + player.getYPos() + "," + player.getZPos() + "] " + currentScene.getName());
    }

    private static void quit() {
        // SIGN OFF
        System.out.println("Thank you for playing.");
        System.exit(0);
    }

}
