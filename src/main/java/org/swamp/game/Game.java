package org.swamp.game;

import org.swamp.game.domain.Enemy;
import org.swamp.game.domain.EnemyFactory;
import org.swamp.game.domain.Hek;
import org.swamp.game.gui.GameObserver;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Hek hek;
    private List<Enemy> enemies;
    private List<GameObserver> observers;
    private Random rand = new Random();
    private boolean gameOver;

    public Game() {
        observers = new ArrayList<>();
        initializeGame();
    }

    public void initializeGame() {
        Point newPosition = getRandomPosition();
        hek = new Hek();
        hek.setPosition(newPosition);
        hek.setMood(Hek.Mood.PASSIVE);
        enemies = new ArrayList<>();
        gameOver = false;
        notifyObservers();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void moveHek() {
        if (gameOver) return;
        Point newPosition = getRandomPosition();
        hek.move(newPosition);
        // move enemies
        for(Enemy enemy: enemies) {
            Point enemyNewPosition = getRandomPosition();
            enemy.move(enemyNewPosition);
        }
        checkConflicts();
        if (rand.nextInt(3) == 0) {
            spawnEnemy();
        }
        notifyObservers();
    }
    public void setOrgeMood() {
        Hek.Mood orgeMood = hek.getMood();
        if (orgeMood == null)
            return;
        if (orgeMood.equals(Hek.Mood.PASSIVE)) {
            hek.setMood(Hek.Mood.GRUMPY);
        } else {
            hek.setMood(Hek.Mood.PASSIVE);
        }
        notifyObservers();
    }

    void checkConflicts() {
        Point hekPosition = hek.getPosition();
        List<Enemy> enemiesAtPosition = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.getPosition().equals(hekPosition)) {
                enemiesAtPosition.add(enemy);
            }
        }

        int enemyCount = enemiesAtPosition.size();
        if (enemyCount == 1) {
            enemies.removeAll(enemiesAtPosition);
        } else if (enemyCount >= 2) {
            if (hek.getMood() == Hek.Mood.GRUMPY && enemyCount < 3) {
                enemies.removeAll(enemiesAtPosition);
            } else {
                gameOver = true;
            }
        }
    }

    private Point getRandomPosition() {
        // Place Hek in a random position except the top-left corner
        int x, y;
        int bound = Constant.DIMENSION_SIZE;
        do {
            x = rand.nextInt(bound);
            y = rand.nextInt(bound);
        } while (x == 0 && y == 0);
        return new Point(x,y);
    }

    private void spawnEnemy() {
        Enemy enemy = EnemyFactory.createRandomEnemy(new Point(0, 0));
        enemies.add(enemy);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }


    public Hek getHek() {
        return hek;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
