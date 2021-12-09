package org.cis120.twentyfourtyeight;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private TwentyFourtyEight tfe; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        tfe = new TwentyFourtyEight(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                tfe.playTurn(keyCode);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }

        });


    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        tfe.updateGameHistory();
        int gameNumber = tfe.getGameNumber();
//        TreeMap<Integer, ArrayList<Integer>> history = tfe.getGameHistoryTreeMap();
        tfe.reset();
        status.setText("Swipe");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * DONE -- IMPORTANT - WRITE JAVADOCS
     */
    public String done() {
        tfe.updateGameHistory();
        return tfe.getGameHistory();
    }

    /**
     * SAVE -- IMPORTANT - WRITE JAVADOCS
     */
    public void save() {
        String stringBoard = tfe.getBoardForSaving();
        String stringHistory = tfe.getGameHistoryForSaving();
        String turns = String.valueOf(tfe.getNumTurns());
        String savedStuff = stringBoard + ";" + stringHistory + ";" + turns;
        System.out.println(tfe.getGameHistory());
        System.out.println(savedStuff);
        tfe.writeStringsToFile(savedStuff, "./files/2048_game.txt", false);
    }

    /**
     * RELOAD -- IMPORTANT - WRITE JAVADOCS
     */
    public void reload() {
        FileLineIterator li = new FileLineIterator("./files/2048_game.txt");
        String stuffInFile = "";
        while (li.hasNext()) {
            stuffInFile += li.next();
        }
        String[] stuffAsList = stuffInFile.split(";");
        String[] boardAsList = stuffAsList[0].split(" ");
        String[] historyAsList = stuffAsList[1].split(" ");
        String turnsAsList = stuffAsList[2];

//        tfe.reset();

        int col = 0;
        for (int i = 0; i < boardAsList.length; i++) {
            int row = i / 4;
            tfe.getCell(row, col).updateValue(Integer.parseInt(boardAsList[i]));
            if (col == 3) {
                col = 0;
            } else {
                col++;
            }
        }

//        int counter = 0;
        for (int i = 0; i <= historyAsList.length - 3; i = i + 3) {
            tfe.updateGameHistory(Integer.parseInt(historyAsList[i]),
                    Integer.parseInt(historyAsList[i + 1]),
                    Integer.parseInt(historyAsList[i + 2]));
        }

        tfe.updateNumturns(Integer.parseInt(turnsAsList));

        tfe.reset(Integer.parseInt(turnsAsList), tfe.getBoard());
        repaint();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (tfe.fullBoardAndOver() == 2) {
            status.setText("Game Over");
        } else {
            status.setText("Turn " + tfe.getNumTurns());
        }
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(100, 0, 100, 400);
        g.drawLine(200, 0, 200, 400);
        g.drawLine(300, 0, 300, 400);
        g.drawLine(0, 100, 400, 100);
        g.drawLine(0, 200, 400, 200);
        g.drawLine(0, 300, 400, 300);

        // Draws X's and O's
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                Tile state = tfe.getCell(i, j);
                g.setColor(state.getColor());
//                g.drawRect(1+100*j, 1+100*i, 98, 98);
                g.fillRect(1+100*j, 1+100*i, 98, 98);
                g.setColor(Color.BLACK);
                if (state.getValue() != 0) {
                    g.drawString(String.valueOf(state.getValue()), 30 + 100 * j, 30 + 100 * i);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
