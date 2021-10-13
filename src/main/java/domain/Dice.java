package domain;

import java.util.Random;

public class Dice {
    private int value;

    public static int rollDice() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
