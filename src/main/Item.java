package main;

class Item {
    String name;
    int meleeDamage;
    int rangedDamage;
    int armor;
    int luck;
    boolean isWeapon = false;
    boolean isArmor = false;

    // Item Text
    String pickup;

    //Constructors

    Item() {
        name = "itemName";
        meleeDamage = 0;
        rangedDamage = 0;
        armor = 0;
        luck = 0;
    }

    Item(String name) {
        this();
        this.name = name;
    }

    Item(String name, String pickup) {
        this(name);
        this.pickup = pickup;
    }

    Item(String name, int armor, int luck) {
        this(name);
        this.armor = armor;
        this.luck = luck;
        this.isArmor = true;
    }

    Item(String name, int armor, int luck, String pickup) {
        this(name, armor, luck);
        this.pickup = pickup;
    }

    Item(String name, int meleeDamage, int rangedDamage, int luck) {
        this(name);
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
        this.luck = luck;
        this.isWeapon = true;
    }

    Item(String name, int meleeDamage, int rangedDamage, int luck, String pickup) {
        this(name, meleeDamage, rangedDamage, luck);
        this.pickup = pickup;
    }
}
