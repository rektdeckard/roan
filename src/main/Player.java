package main;

public class Player extends Creature {

    // PLAYER LOCATION
    private int xPos;
    private int yPos;

    // METHODS

    public void equip(String input) {
        boolean hasItem = false;
        boolean equippedItem = false;
        for (Item item : super.getInventory().values()) {
            if (input.contains(item.getName().toLowerCase())) {
                hasItem = true;
                equippedItem = equip(item);
            }
        }
        if (!hasItem) {
            System.out.println("You don't have that in your inventory.");
        } else if (equippedItem) {
            System.out.println("Item equipped.");
        } else {
            System.out.println("You can't equip that.");
        }
    }

    public void unequip(String input) {
        boolean hasItem = false;
        boolean unequippedItem = false;
        if (input.equals("unequip all") && ((super.getEquippedWeapon() != null) || (super.getEquippedArmor() != null))) {
            super.unequip();
            hasItem = true;
            unequippedItem = true;
        } else {
            for (Item item : super.getInventory().values()) {
                if (input.contains(item.getName().toLowerCase())) {
                    hasItem = true;
                    if (item instanceof Weapon && super.getEquippedWeapon() == item) {
                        unequippedItem = super.unequip(item);
                    } else if (item instanceof Armor && super.getEquippedArmor() == item) {
                        unequippedItem = super.unequip(item);
                    } else {
                        System.out.println("Item is not equipped");
                    }
                }
            }
        }
        if (!hasItem) {
            System.out.println("You don't have that in your inventory.");
        } else if (unequippedItem) {
            System.out.println("Item(s) unequipped.");
        }
    }

    @Override
    public void setHealth(int n) {
        n += super.getHealth();
        if (n > super.getMaxHealth()) {
            super.setHealth(super.getMaxHealth());
            System.out.println("You feel refreshed.");
        } else if (n <= 0) {
            System.out.println("Your vision grows faint as the world slips away. You are dead.");
            System.exit(0);
        } else if (n > super.getHealth()) {
            System.out.println("You feel a bit better...");
        } else {
            super.setHealth(n);
            System.out.println("That hurt.");
        }
    }

    public int getXPos() {
        return this.xPos;
    }

    public int getYPos() {
        return this.yPos;
    }

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // BUILD
    public static abstract class Builder<T extends Builder<T>> extends Creature.Builder<T> {

        // PLAYER STATISTICS
        private int xPos = 5;
        private int yPos = 5;


        public Player build() {
            return new Player(this);
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

    private Player(Builder<?> builder) {
        super(builder);
        this.xPos = builder.xPos;
        this.yPos = builder.yPos;
    }

}
