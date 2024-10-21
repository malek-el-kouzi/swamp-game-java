package org.swamp.game.domain;

import java.awt.*;

public class Hek implements Creature {
    private Point position;
    private Mood mood;

    @Override
    public void move(Point newPosition) {
        setPosition(newPosition);
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public enum Mood {
        PASSIVE, GRUMPY
    }

}


