package main;

class NPC extends Creature {

    // NPC Statistics (inherited from Creature)

    // NPC Location
    int xPos;
    int yPos;

    // Constructors
    NPC() {
        super();
        hostile = false;
    }

    NPC(String name, int maxHealth, int meleeAttack, int rangedAttack, int luck, Item item) { //add dialogue stuff?
        super(name, maxHealth, meleeAttack, rangedAttack, luck, item);
        hostile = false;
        // Dialogue stuff??
    }

    NPC(String name, int maxHealth, int meleeAttack, int rangedAttack, int luck, Item item1, Item item2) {
        super(name, maxHealth, meleeAttack, rangedAttack, luck, item1, item2);
        hostile = false;
    }
}
