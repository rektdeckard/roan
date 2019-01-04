package main;

class Player extends Creature {

    // Player Statistics (inherited from Creature)

    // Player Location
    int xPos = 5;
    int yPos = 5;

    // Constructor, used only for initialization
    Player() {
        maxHealth = 40;
        health = maxHealth;
        meleeAttack = 0;
        rangedAttack = 0;
        luck = 0;
        Item rope = new Item("rope");
        inventory.put(rope.name, rope);
    }

    // Taking Damage and Healing (overrides Creature.setHealth)
    @Override
    boolean setHealth(int n) {
        health += n;
        if (health > maxHealth) {
            health = maxHealth;
            System.out.println("You feel refreshed.");
            return true;
        } else if (health <= 0) {
            System.out.println("Your vision grows faint as the world slips away. You are dead.");
            System.exit(0);
            return false;
        } else if (n > 0) {
            System.out.println("You feel a bit better...");
            return true;
        } else {
            System.out.println("That hurt.");
            return true;
        }
    }

}
