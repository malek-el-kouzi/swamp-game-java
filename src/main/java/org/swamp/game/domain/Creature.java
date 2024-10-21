package org.swamp.game.domain;

import java.awt.*;

public interface Creature {
    void move(Point newPosition);
    Point getPosition();
}
