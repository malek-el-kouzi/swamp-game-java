package org.swamp.game.gui;



import org.swamp.game.Game;

import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame implements GameObserver {
    private final Game game;
    private final GamePanel gamePanel;
    private JButton startButton;
    private JButton moveButton;
    private JButton orgeMoodButton;
    private JButton helpButton;

    public GameGUI() {
        setTitle("Get Out of My Swamp");
        setSize(420, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        game = new Game();
        game.addObserver(this);

        gamePanel = new GamePanel(game);
        add(gamePanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        startButton = new JButton("Start");
        moveButton = new JButton("Move Hek");
        orgeMoodButton = new JButton("Change Mood");
        helpButton = new JButton("Help");
        controlPanel.add(startButton);
        controlPanel.add(moveButton);
        controlPanel.add(orgeMoodButton);
        controlPanel.add(helpButton);

        startButton.addActionListener(e -> game.initializeGame());
        moveButton.addActionListener(e -> game.moveHek());
        orgeMoodButton.addActionListener(e -> game.setOrgeMood());
        helpButton.addActionListener(e -> showHelpMessage());

        add(controlPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update() {
        gamePanel.repaint();
        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showHelpMessage() {
        String helpMessage = "Game Help:\n\n" +
                "1. Move Hek using the 'Move' button.\n" +
                "2. Start/Restart the game using the 'Start' button.\n" +
                "3. Change Hek's mood between 'Passive' and 'Grumpy' using the 'Mood' button.\n" +
                "4. Enemy Colors:\n" +
                "   - Snake: Red\n" +
                "   - Parrot: Blue\n" +
                "   - Donkey: Gray\n" +
                "5. Hek's Colors:\n" +
                "   - Passive Mood: Green\n" +
                "   - Grumpy Mood: Orange\n" +
                "Enemies enter from the top-left corner and move randomly.\n" +
                "Hek kills one enemy upon encounter; two enemies kill Hek unless he is grumpy, where it takes three.";

        JOptionPane.showMessageDialog(null, helpMessage, "Game Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            gui.setVisible(true);
        });
    }
}
