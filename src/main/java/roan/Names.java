package roan;

import java.util.ArrayList;

public class Names {

    // WEAPON ADJECTIVES
    private static String[] weaponAdjectives = {
            "Bloody",
            "Curious",
            "Deadly",
            "Fervent",
            "Fortuitous",
            "Rusty",
            "Violent",
    };

    private static String[] weaponNoun = {
            "Bow",
            "Dagger",
            "Dirk",
            "Longbow",
            "Longsword",
            "Recurve Bow",
            "Short Bow",
            "Shortsword",
    };

    public static String randomWeaponAdjective() {
        return weaponAdjectives[Dice.roll(weaponAdjectives.length)];
    }

    public static String randomWeaponNoun() {
        return weaponNoun[Dice.roll(weaponNoun.length)];
    }

}
