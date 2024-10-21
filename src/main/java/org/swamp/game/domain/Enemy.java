package org.swamp.game.domain;

import java.awt.*;

public abstract class Enemy implements Creature {
    protected Point position;

    public Enemy(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void move(Point newPosition) {
        position = newPosition;
    }
}

