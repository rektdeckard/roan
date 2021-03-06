package roan;

import java.io.InputStream;
import java.util.Iterator;
import java.util.ListIterator;
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
        while (playing) {
            System.out.print("You open your eyes and rise slowly to your feet. You can't seem to remember who you are, or how you arrived here. ");
            while (alive) {
                // Check for hostiles, enter combat or dialogue
                if (currentScene.getCreatures() != null && !currentScene.getCreatures().isEmpty()) {
                    for (Creature creature : currentScene.getCreatures()) {
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
        while (!done) {
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
            } else if (input.equals("rest") || input.equals("r")) {
                player.rest();
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
                System.out.println("That can't be done.");
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
                y++;
            } else if (direction == Direction.NE) {
                x++;
                y++;
            } else if (direction == Direction.E) {
                x++;
            } else if (direction == Direction.SE) {
                x++;
                y--;
            } else if (direction == Direction.S) {
                y--;
            } else if (direction == Direction.SW) {
                x--;
                y--;
            } else if (direction == Direction.W) {
                x--;
            } else if (direction == Direction.NW) {
                x--;
                y++;
            }
            currentScene = map.getScene(player, currentScene, x, y);
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
                y++;
            } else if (direction == Direction.NE) {
                x++;
                y++;
            } else if (direction == Direction.E) {
                x++;
            } else if (direction == Direction.SE) {
                x++;
                y--;
            } else if (direction == Direction.S) {
                y--;
            } else if (direction == Direction.SW) {
                x--;
                y--;
            } else if (direction == Direction.W) {
                x--;
            } else if (direction == Direction.NW) {
                x--;
                y++;
            }
            currentScene = map.getScene(player, currentScene, x, y, z);
            return true;
        } else {
            System.out.println("That can't be done.");
            return false;
        }
    }

    // COMBAT LOOP
    private static boolean combat() {
        for (Creature combatant : currentScene.getCreatures()) {
            System.out.println(combatant.getDescription());
        }
        // Loop while creatures are alive and has not fled. Return true if player still alive, else false.
        boolean fighting = !currentScene.getCreatures().isEmpty();
        while (fighting) {
            // TODO order combatants based on luck and dice roll
            // Enemy attack phase
            for (Creature combatant : currentScene.getCreatures()) {
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
            // TODO remove instakill when releasing
            // TODO fix iterator. how to dispose of and remove?
            if (input.contains("kill")) {
                for (final ListIterator<Creature> iterator = currentScene.getCreatures().listIterator(); iterator.hasNext(); ) {
                    Creature combatant = iterator.next();
                    if (input.contains(combatant.getName().toLowerCase()) || (currentScene.getCreatures().size() == 1)) {
                        currentScene.disposeOf(combatant);
                        iterator.remove();
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
                for (Creature combatant : currentScene.getCreatures()) {
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
        // TODO Fix iterator problem
        Creature combatant = null;
        if (player.getEquippedWeapon() != null) {
            if (currentScene.getCreatures().size() == 1) {
                combatant = currentScene.getCreatures().get(0);
            } else {
                for (Creature creature : currentScene.getCreatures()) {
                    if (input.contains(creature.getName().toLowerCase())) {
                        combatant = creature;
                    }
                }
            }
            if (combatant != null) {
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
            } else{
                System.out.println("There is no enemy by that name.");
            }
        } else {
            System.out.println("You have no weapon equipped!");
        }
        //TODO add parry/riposte/thorns? case where player is injured by attacking
        return true;
    }

    private static void ambush(String input) {
        for (Creature combatant : currentScene.getCreatures()) {
            if (input.contains(combatant.getName().toLowerCase())) {
                combatant.setHostile(true);
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
                System.out.print("Creatures: ");
                for (Creature creature : currentScene.getCreatures()) {
                    System.out.print("[" + creature.getName() + "] ");
                }
                System.out.println();
            }
            if (!currentScene.getInventory().isEmpty()) {
                System.out.print("Items: ");
                for (Item item : currentScene.getInventory()) {
                    System.out.print("[" + item.getName() + "] ");
                }
                System.out.println();
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
        } else if (input.contains("all") || input.equals("t") || input.equals("take")) {
            System.out.print("Received: ");
            for (Item item : currentScene.getInventory()) {
                printYellow("[" + item.getName() + "] ");
            }
            System.out.println();
            player.putInventory(currentScene.getInventory());
            currentScene.clearInventory();
        } else {
            boolean hasItem = false;
            for (Item item : currentScene.getInventory()) {
                if (input.contains(item.getName().toLowerCase())) {
                    hasItem = true;
                    player.putInventory(item);
                    if (item.getDescription() != null) {
                        System.out.println(item.getDescription());
                    }
                    System.out.print("Received: ");
                    printYellow("[" + item.getName() + "] ");
                    System.out.println();
                    currentScene.removeInventory(item);
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
            boolean hasRope = false;
            for (Item item : player.getInventory()) {
                if (item.getName().toLowerCase().equals("rope")) {
                    hasRope = true;
                    if (Dice.roll(20) + player.getLuck() >= 10) {
                        System.out.println(currentScene.getSucceed());
                        return go(currentScene.getClimbDirection(), currentScene.getClimbVertical());
                    } else {
                        System.out.println(currentScene.getFail());
                        player.damage(Dice.roll(20));
                        return false;
                    }
                }
            }
            System.out.println("You don't have any rope.");
            return false;
        } else {
            System.out.println("You can't do that here.");
            return false;
        }
    }

    private static void inventory() {
        if (!player.getInventory().isEmpty()) {
            System.out.print("Inventory: ");
            for (Item item : player.getInventory()) {
                System.out.print("[" + item.getName() + "] ");
            }
            System.out.println();
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
        String mapFile = "PlayerMap.txt";
        InputStream inputStream = Roan.class.getResourceAsStream(mapFile);
        if (inputStream == null) {
            System.out.println("Resource not found: " + mapFile);
        } else {
            System.out.println(inputStream.toString());
        }
    }

    private static void quit() {
        // SIGN OFF
        System.out.println("Thank you for playing.");
        System.exit(0);
    }

    // PRINT COLORS
    public static void printRed(String string) {
        System.out.print((char) 27 + "[31m" + string + (char) 27 + "[39;49m");
    }

    public static void printYellow(String string) {
        System.out.print((char) 27 + "[33m" + string + (char) 27 + "[39;49m");
    }

    public static void printGreen(String string) {
        System.out.print((char) 27 + "[32m" + string + (char) 27 + "[39;49m");
    }

    public static void printBlue(String string) {
        System.out.print((char) 27 + "[34m" + string + (char) 27 + "[39;49m");
    }

    public static void printMagenta(String string) {
        System.out.print((char) 27 + "[35m" + string + (char) 27 + "[39;49m");
    }

}