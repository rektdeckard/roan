package main;

import java.util.Random;

class Dice {

    private final Random random = new Random();

    int roll(int max) {
        return 1 + random.nextInt(max);
    }

}
