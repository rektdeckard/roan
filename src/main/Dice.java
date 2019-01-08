package main;

import java.util.Random;

public class Dice {

    private static final Random random = new Random();

    public static int roll(int max) {
        return 1 + random.nextInt(max);
    }

}
