package roan;

// NEW FORMAT FOR RANDOMS
enum WeaponPrefix {
    ARROGANT ("Arrogant", (Dice.roll(5) + 20),(Dice.roll(2)), 100),
    BLOODY ("Bloody", (Dice.roll(10) + 25), 0, 75),
    CURIOUS ("Curious", 0, (Dice.roll(3)), 50),
    DULL ("Dull", -Dice.roll(5), 0,-10),
    FERVENT ("Fervent", (Dice.roll(5) + 30), -1, 60),
    FLIMSY ("Flimsy", 0, -Dice.roll(3), -5),
    FORTUITOUS ("Fortuitous", 5, (Dice.roll(3) + 2), 55),
    RUSTY ("Rusty", -3, 0, -5),
    VIOLENT ("Violent", Dice.roll(5) + 30, Dice.roll(5), 150),
    ZEALOUS ("Zealous", (Dice.roll(20) + 20), -3, 100);

    public String name;
    public int damage;
    public int luck;
    public int value;

    private static final int size = WeaponPrefix.values().length;

    WeaponPrefix(String name, int damage, int luck, int value) {
        this.name = name;
        this.damage = damage;
        this.luck = luck;
        this.value = value;
    }

    public static WeaponPrefix randomPrefix() {
        return WeaponPrefix.values()[Dice.roll(WeaponPrefix.size) - 1];
    }
}

enum WeaponSuffix {
    BASTARDSWORD ("Bastard Sword", 20, -1, 75),
    CLAWS ("Claws", 8, 3, 50),
    DAGGER ("Dagger", 7, 1, 10),
    DIRK ("Dirk", 5, 2, 15),
    GLAIVE ("Glaive", 18, -1, 80),
    HORN ("Horn", 5, 0, 5),
    LONGSWORD ("Longsword", 10, 0, 15),
    MACE("Mace", 15, -2, 50),
    MORNINGSTAR("Morningstar", 20, -2, 90),
    SHORTSWORD ("Shortsword", 8, 0, 10);

    public String name;
    public int damage;
    public int luck;
    public int value;

    private static final int size = WeaponSuffix.values().length;

    WeaponSuffix(String name, int damage, int luck, int value) {
        this.name = name;
        this.damage = damage;
        this.luck = luck;
        this.value = value;
    }

    public static WeaponSuffix randomSuffix() {
        return WeaponSuffix.values()[Dice.roll(size) - 1];
    }
}