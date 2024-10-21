package org.swamp.game;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.swamp.game.domain.Enemy;
import org.swamp.game.domain.EnemyFactory;
import org.swamp.game.domain.Hek;
import org.swamp.game.gui.GameObserver;

import java.awt.Point;
import java.util.List;


class GameTest {

    private Game game;

    @Mock
    private GameObserver observer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        game = new Game();
        game.addObserver(observer);
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game.getHek());
        assertTrue(game.getEnemies().isEmpty());
        assertFalse(game.isGameOver());
    }

    @Test
    void testMoveHek() {
        Hek hek = game.getHek();
        hek.setPosition(new Point(0,0));
        Point initialPosition = hek.getPosition();
        game.moveHek();

        assertNotEquals(initialPosition.x, game.getHek().getPosition().x);
        assertNotEquals(initialPosition.y, game.getHek().getPosition().y);
    }

    @Test
    void testConflictResolution() {
        Point conflictPosition = new Point(3, 3);
        game.getHek().setPosition(conflictPosition);
        Enemy enemy = EnemyFactory.createRandomEnemy(conflictPosition);
        game.getEnemies().add(enemy);
        game.checkConflicts();
        assertTrue(game.getEnemies().isEmpty());
    }

    @Test
    void testGameOver() {
        Point position = new Point(3, 3);
        game.getHek().setPosition(position);
        game.getHek().setMood(Hek.Mood.PASSIVE);
        List<Enemy> enemies = game.getEnemies();

        Enemy e1= EnemyFactory.createRandomEnemy(position);
        Enemy e2 = EnemyFactory.createRandomEnemy(position);

        enemies.add(e1);
        enemies.add(e2);

        game.checkConflicts();
        assertTrue(game.isGameOver());
    }

    @Test
    void testMoodChange() {
        game.getHek().setMood(Hek.Mood.PASSIVE);
        game.setOrgeMood();
        assertEquals(Hek.Mood.GRUMPY, game.getHek().getMood());
    }
}
