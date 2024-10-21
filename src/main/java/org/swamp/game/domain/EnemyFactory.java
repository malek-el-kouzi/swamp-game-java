package org.swamp.game.domain;

import java.awt.Point;
import java.util.Random;

public class EnemyFactory {
    private static final Random random = new Random();

    public static Enemy createRandomEnemy(Point entryPoint) {
        int type = random.nextInt(3); // for the 3 types of enemies, snake, parrot and donkey
        switch (type) {
            case 0:
                return new Snake(entryPoint);
            case 1:
                return new Parrot(entryPoint);
            case 2:
                return new Donkey(entryPoint);
            default:
                throw new IllegalArgumentException("Unknown enemy type");
        }
    }
}
