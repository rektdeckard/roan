package main;

import java.util.HashMap;
import java.util.Map;

public class Creature {

    // CREATURE STATISTICS
    private String name;
    private int maxHealth;
    private int health;
    private int meleeAttack;
    private int rangedAttack;
    private int luck;
    private boolean hostile;

    // INVENTORY
    private Map<String, Item> inventory;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    // CREATURE TEXT
    private String description;

    // METHODS
    public boolean equip(Item item) {
        boolean equipped = false;
        for (Item inventoryItem : this.inventory.values()) {
            if (item == inventoryItem) {
                if (item instanceof Weapon) {
                    this.equippedWeapon = (Weapon) item;
                    this.luck += this.equippedWeapon.getLuck();
                    equipped = true;
                } else if (item instanceof Armor) {
                    this.equippedArmor = (Armor) item;
                    this.luck += this.equippedArmor.getLuck();
                    equipped = true;
                }
            }
        }
        return equipped;
    }

    public void unequip() {
        this.equippedWeapon = null;
        this.equippedArmor = null;
    }

    public boolean unequip(Item item) {
        boolean unequipped = false;
        if (item == this.equippedWeapon) {
            this.equippedWeapon = null;
            unequipped = true;
        } else if (item == equippedArmor){
            this.equippedArmor = null;
            unequipped = true;
        }
        return unequipped;
    }

    // GETTERS & SETTERS
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        if (maxHealth <= 0) {
            maxHealth = 10;
        }
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean damage(int n) {
        this.health -= n;
        if (health <= 0) {
            System.out.println(this.name + " has fallen.");
            return false;
        } else {
            System.out.println(this.name + " was injured.");
            return true;
        }
    }

    public void heal(int n) {
        this.health += n;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
            System.out.println(this.name + " looks refreshed.");
        } else if (n > 0) {
            System.out.println(this.name + " looks stronger.");
        }
        // TODO: 1/7/2019 potions??
    }

    public int getMeleeAttack() {
        return this.meleeAttack;
    }

    public void setMeleeAttack(int meleeAttack) {
        if (meleeAttack < 0) {
            meleeAttack = 0;
        }
        this.meleeAttack = meleeAttack;
    }

    public int getRangedAttack() {
        return this.rangedAttack;
    }

    public void setRangedAttack(int rangedAttack) {
        if (rangedAttack < 0) {
            rangedAttack = 0;
        }
        this.rangedAttack = rangedAttack;
    }

    public int getLuck() {
        return this.luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public boolean isHostile() {
        return this.hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Item> getInventory() {
        return this.inventory;
    }

    public void putInventory(Item item) {
        this.inventory.put(item.getName(), item);
    }

    public void putInventory(Map<String, Item> inventory) {
        this.inventory.putAll(inventory);
    }

    public void removeInventory(Item item) {
        this.unequip(item);
        this.inventory.remove(item.getName());
    }

    public void clearInventory() {
        this.inventory.clear();
    }

    public Map<String, Item> takeInventory() {
        Map<String, Item> inventory = this.inventory;
        this.inventory.clear();
        return inventory;
    }

    public Weapon getEquippedWeapon() {
        return this.equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return this.equippedArmor;
    }

    // BUILDER
    public static abstract class Builder<T extends Builder<T>> {

        // CREATURE STATISTICS
        private String name = "NullBeast";
        private int maxHealth = 10;
        private int health = 10;
        private int meleeAttack = 0;
        private int rangedAttack = 0;
        private int luck = 0;
        private boolean hostile = true;

        // INVENTORY
        private Map<String, Item> inventory = new HashMap<>();
        private Weapon equippedWeapon;
        private Armor equippedArmor;

        // CREATURE TEXT
        private String description;

        protected abstract T self();

        public T name(String name) {
            this.name = name;
            this.description = "A " + this.name + " looks ready to kill you.";
            return self();
        }

        public T maxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            this.health = maxHealth;
            return self();
        }

        public T meleeAttack(int meleeAttack) {
            if (meleeAttack < 0) {
                meleeAttack = 0;
            }
            this.meleeAttack = meleeAttack;
            return self();
        }

        public T rangedAttack(int rangedAttack) {
            if (rangedAttack < 0) {
                rangedAttack = 0;
            }
            this.rangedAttack = rangedAttack;
            return self();
        }

        public T luck(int luck) {
            this.luck = luck;
            return self();
        }

        public T hostile(boolean hostile) {
            this.hostile = hostile;
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        public T inventory(Item item) {
            this.inventory.put(item.getName(), item);
            return self();
        }

        public T equip(Item item) {
            this.inventory.put(item.getName(), item);
            if (item instanceof Weapon) {
                this.equippedWeapon = (Weapon) item;
                this.luck += this.equippedWeapon.getLuck();
            } else if (item instanceof Armor) {
                this.equippedArmor = (Armor) item;
            }
            return self();
        }

        public Creature build() {
            return new Creature(this);
        }

    }

    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    // SOLE CONSTRUCTOR
    Creature(Builder<?> builder) {
        this.name = builder.name;
        this.maxHealth = builder.maxHealth;
        this.health = builder.health;
        this.meleeAttack = builder.meleeAttack;
        this.rangedAttack = builder.rangedAttack;
        this.luck = builder.luck;
        this.hostile = builder.hostile;
        this.description = builder.description;
        this.inventory = builder.inventory;
        this.equippedWeapon = builder.equippedWeapon;
        this.equippedArmor = builder.equippedArmor;
    }

}