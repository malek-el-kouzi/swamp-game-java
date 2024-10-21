package org.swamp.game.gui;



import org.swamp.game.Constant;
import org.swamp.game.Game;
import org.swamp.game.domain.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int CELL_SIZE = 100; // Size of each cell in the grid
    private static final int DIMENSION = Constant.DIMENSION_SIZE;
    private final Game game;


    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(DIMENSION * CELL_SIZE, DIMENSION * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawCreatures(g);
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                g.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawCreatures(Graphics g) {
        // Draw Hek
        Hek hek = game.getHek();
        Point hekPosition = hek.getPosition();
        Hek.Mood mood = hek.getMood();
        if (mood.equals(Hek.Mood.PASSIVE)) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.ORANGE);
        }
        g.fillRect(hekPosition.x * CELL_SIZE, hekPosition.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g.setColor(Color.BLACK);
        drawCenteredText(g, "Hek", hekPosition.x * CELL_SIZE, hekPosition.y * CELL_SIZE, CELL_SIZE);

        // Draw Enemies
        int y_increment = 20;
        for (Enemy enemy : game.getEnemies()) {
            Point position = enemy.getPosition();
            g.setColor(getColorForEnemy(enemy));
            g.fillRect(position.x * CELL_SIZE + 10, position.y * CELL_SIZE + 10, CELL_SIZE - 20, CELL_SIZE - 20);

            // Draw enemy name
            g.setColor(Color.BLACK);
            drawCenteredText(g, enemy.getClass().getSimpleName(), position.x * CELL_SIZE, position.y * CELL_SIZE + y_increment, CELL_SIZE);
        }
    }


    private Color getColorForEnemy(Enemy enemy) {
        if (enemy instanceof Snake) {
            return Color.RED;
        } else if (enemy instanceof Parrot) {
            return Color.BLUE;
        } else if (enemy instanceof Donkey) {
            return Color.GRAY;
        }
        return Color.BLACK; // Default color for an unknown type of enemy
    }
    private void drawCenteredText(Graphics g, String text, int x, int y, int width) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.drawString(text, x + (width - textWidth) / 2, y + (textHeight + (CELL_SIZE - textHeight) / 2) / 2);
    }
}
