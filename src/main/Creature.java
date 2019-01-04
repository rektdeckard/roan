package main;

import java.util.HashMap;
import java.util.Map;

class Creature {

    // Creature Statistics
    String name;
    int maxHealth;
    int health;
    int meleeAttack;
    int rangedAttack;
    int luck = 0;
    boolean hostile = true;

    // Inventory
    Map<String, Item> inventory = new HashMap<>();
    Item equippedWeapon;
    Item equippedArmor;

    //Creature Text
    String description;

    // Methods
    boolean equip(Item item) {
        if (item.isWeapon) {
            this.equippedWeapon = item;
            return true;
        } else if (item.isArmor) {
            this.equippedArmor = item;
            return true;
        } else {
            return false;
        }
    }

    // Constuctors
    Creature() {
        name = "Nullbeast";
        maxHealth = 10;
        health = maxHealth;
        this.description = "A " + this.name + " stands ready to fight.";
    }

    Creature(String name) {
        this();
        this.name = name;
    }

    Creature(String name, int maxHealth, int meleeAttack, int rangedAttack, int luck) {
        this(name);
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.meleeAttack = meleeAttack;
        this.rangedAttack = rangedAttack;
        this.luck = luck;
    }

    Creature(String name, int maxHealth, int meleeAttack, int rangedAttack, int luck, Item item) {
        this(name, maxHealth, meleeAttack, rangedAttack, luck);
        inventory.put(item.name, item);
        equip(item);
    }

    Creature(String name, int maxHealth, int meleeAttack, int rangedAttack, int luck, Item item1, Item item2) {
        this(name, maxHealth, meleeAttack, rangedAttack, luck, item1);
        inventory.put(item2.name, item2);
        equip(item2);
    }

    // Taking Damage and Healing
    boolean setHealth(int n) {
        health += n;
        if (health > maxHealth) {
            health = maxHealth;
            System.out.println(this.name + " looks refreshed.");
            return true;
        } else if (n > 0) {
            System.out.println(this.name + " looks stronger.");
            return true;
        } else if (health <= 0) {
            System.out.println(this.name + " has fallen.");
            return false;
        } else {
            System.out.println(this.name + " was injured.");
            return true;
        }
    }

}
