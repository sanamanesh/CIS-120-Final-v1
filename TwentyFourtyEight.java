package org.cis120.twentyfourtyeight;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.*;


/**
 * This class is a model for TicTacToe.
 *
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 *
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 *
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class TwentyFourtyEight {

    private Tile[][] board;
    private int numTurns;
    private boolean gameOver;
    private boolean notSummed;
    private TreeMap<Integer, ArrayList<Integer>> gameHistory;
    private int gameNumber;

    /**
     * Constructor sets up game state.
     */
    public TwentyFourtyEight() {
        gameHistory = new TreeMap<Integer, ArrayList<Integer>>();
        reset();
    }

    public TwentyFourtyEight(Tile[][] board) {
        gameHistory = new TreeMap<Integer, ArrayList<Integer>>();
        reset(0, board);
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    //TreeMap<Integer, ArrayList<Integer>> history
    public void reset() {
//        updateGameHistory();

        board = new Tile[4][4];
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                board[i][j] = new Tile();
            }
        }
        numTurns = 0;
        gameOver = false;
        notSummed = true;
        gameNumber = 1;
//        gameHistory = history;
        generateValue();
        generateValue();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    //TreeMap<Integer, ArrayList<Integer>> history
    public void reset(int gameNum, Tile[][] pastBoard) {
//        updateGameHistory();

        board = pastBoard;

        numTurns = 0;
        gameOver = false;
        notSummed = true;
        gameNumber = gameNum;
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param e keyCode of player
     * @return whether the turn was successful
     */
    public boolean playTurn(int e) {
        if (fullBoardAndOver() == 2) {
            return false;
        }

        boolean moves = false;
        if (e == KeyEvent.VK_UP) {
            moves = up();
        } else if (e == KeyEvent.VK_DOWN) {
            moves = down();
        } else if (e == KeyEvent.VK_LEFT) {
            moves = left();
        } else if (e == KeyEvent.VK_RIGHT) {
            moves = right();
        }
        if (moves) {
            numTurns++;
        }
        return moves;
    }

    public boolean up() {
        boolean moves = false;
        if (fullBoardAndOver() != 2) {
//            for (int i = 1; i <= 3; i++) {
//                for (int j = 0; j <= 3; j++) {
//                    if (board[i][j].getValue() != 0) {
//                        int highestEmptyRow = i; //
//                        //look thru all rows @ that col val above to find highestEmptyRow
//                        for (int backi = i; backi >= 0; backi--) {
//                            if (board[backi][j].getValue() == 0) {
//                                highestEmptyRow = backi;
//                            }
//                        }
//                        board[highestEmptyRow][j].updateValue(board[i][j].getValue());
//                        // update board based off highestEmptyRow value
////                        board[highestEmptyRow][j].updateValue(board[i][j].getValue());
////                        if (highestEmptyRow != 0) {
////                            if (board[highestEmptyRow - 1][j].getValue() ==
////                                    board[i][j].getValue()) {
////                                board[highestEmptyRow - 1][j].updateValue(2 * board[i][j].getValue());
////                                board[highestEmptyRow][j].updateValue(0);
////                            }
////                        }
////                        board[i][j].updateValue(0);
//                    }
//                }
//            }
            boolean moves1 = upMove(); //move up
            upSum(); //add all tiles that have same num vertically up
            boolean moves2 = upMove(); //move up again to eliminate spaces from upSum
            moves = moves1 || moves2;
            if (fullBoardAndOver() == 0 && moves) { //check if board is not full
                generateValue();  //generate a new value in an empty space
            }
            fullBoardAndOver(); //check status of board (to update val of gameOver)
        }
        return moves;
    }

    public boolean upMove() {
        boolean moves = false;

        for (int i = 1; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j].getValue() != 0) {
                    int highestEmptyRow = i; //
                    //look thru all rows @ that col val above to find highestEmptyRow
                    for (int backi = i; backi >= 0; backi--) {
                        if (board[backi][j].getValue() == 0) {
                            highestEmptyRow = backi;
                        }
                    }
                    if (highestEmptyRow != i) {
                        board[highestEmptyRow][j].updateValue(board[i][j].getValue());
                        board[i][j].updateValue(0);
                        moves = true;
                    }
                    // update board based off highestEmptyRow value
//                        board[highestEmptyRow][j].updateValue(board[i][j].getValue());
//                        if (highestEmptyRow != 0) {
//                            if (board[highestEmptyRow - 1][j].getValue() ==
//                                    board[i][j].getValue()) {
//                                board[highestEmptyRow - 1][j].updateValue(2 * board[i][j].getValue());
//                                board[highestEmptyRow][j].updateValue(0);
//                            }
//                        }
//                        board[i][j].updateValue(0);
                }
            }
        }

        return moves;
//            if (notSummed) {
//                upSum();
//                notSummed = false;
//            }
//            generateValue();  //generate a new value in an empty space
//            fullBoardAndOver();
    }

    private void upSum() {
        for (int i = 1; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j].getValue() != 0 &&
                        board[i - 1][j].getValue() == board[i][j].getValue()) {
                    board[i - 1][j].updateValue(2 * board[i][j].getValue());
                    board[i][j].updateValue(0);

                }
//                board[i][j].updateValue(0);
            }
        }
    }

    public boolean down() {
        boolean moves = false;
        if (fullBoardAndOver() != 2) {
//            for (int i = 2; i >= 0; i--) {
//                for (int j = 0; j <= 3; j++) {
//                    if (board[i][j].getValue() != 0) {
//                        int lowestEmptyRow = i; //
//                        //look thru all rows @ that col val above to find lowestEmptyRow
//                        for (int backi = i; backi <= 3; backi++) {
//                            if (board[backi][j].getValue() == 0) {
//                                lowestEmptyRow = backi;
//                            }
//                        }
//                        // update board based off lowestEmptyRow value
//                        board[lowestEmptyRow][j].updateValue(board[i][j].getValue());
//                        if (lowestEmptyRow != 3) {
//                            if (board[lowestEmptyRow + 1][j].getValue() ==
//                                    board[i][j].getValue()) {
//                                board[lowestEmptyRow + 1][j].updateValue(2 * board[i][j].getValue());
//                                board[lowestEmptyRow][j].updateValue(0);
//                            }
//                        }
//                        board[i][j].updateValue(0);
//                    }
//                }
//            }
            boolean moves1 = downMove(); //move down
            downSum(); //add all tiles that have same num vertically down
            boolean moves2 = downMove(); //move down again to eliminate spaces from downSum
            moves = moves1 || moves2;
            if (fullBoardAndOver() == 0 && moves) { //check if board is not full
                generateValue();  //generate a new value in an empty space
            }
            fullBoardAndOver(); //check status of board (to update val of gameOver)
        }
        return moves;
    }

    public boolean downMove() {
        boolean moves = false;
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j].getValue() != 0) {
                    int lowestEmptyRow = i; //
                    //look thru all rows @ that col val above to find lowestEmptyRow
                    for (int backi = i; backi <= 3; backi++) {
                        if (board[backi][j].getValue() == 0) {
                            lowestEmptyRow = backi;
                        }
                    }
                    // update board based off lowestEmptyRow value
                    if (lowestEmptyRow != i) {
                        board[lowestEmptyRow][j].updateValue(board[i][j].getValue());
                        board[i][j].updateValue(0);
                        moves = true;
                    }
                }
            }
        }

        return moves;
    }

    private void downSum() {
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j].getValue() != 0 &&
                        board[i + 1][j].getValue() == board[i][j].getValue()) {
                    board[i + 1][j].updateValue(2 * board[i][j].getValue());
                    board[i][j].updateValue(0);
                }
            }
        }
    }

    public boolean left() {
        boolean moves = false;
        if (fullBoardAndOver() != 2) {
//            for (int j = 1; j <= 3; j++) {
//                for (int i = 0; i <= 3; i++) {
//                    if (board[i][j].getValue() != 0) {
//                        int mostLeftEmptyCol = j; //
//                        //look thru all cols @ that row val above to find mostLeftEmptyCol
//                        for (int backj = j; backj >= 0; backj--) {
//                            if (board[i][backj].getValue() == 0) {
//                                mostLeftEmptyCol = backj;
//                            }
//                        }
//                        // update board based off mostLeftEmptyCol value;
////                        if (board[i][mostLeftEmptyCol] == board[i][j]) {
////                            board[i][mostLeftEmptyCol] = 2 * (board[i][j]);
////                        } else {
////                            board[i][mostLeftEmptyCol] = board[i][j];
////                        }
////                        board[i][j] = 0;
//                        board[i][mostLeftEmptyCol].updateValue(board[i][j].getValue());
//                        if (mostLeftEmptyCol != 0) {
//                            if (board[i][mostLeftEmptyCol - 1].getValue() ==
//                                    board[i][j].getValue()) {
//                                board[i][mostLeftEmptyCol - 1].updateValue(2 * board[i][j].getValue());
//                                board[mostLeftEmptyCol][j].updateValue(0);
//                            }
//                        }
//                        board[i][j].updateValue(0);
//                    }
//                }
//            }
            boolean moves1 = leftMove(); //move left
            leftSum(); //add all tiles that have same num horizontally left
            boolean moves2 = leftMove(); //move left again to eliminate spaces from leftSum
            moves = moves1 || moves2;
            if (fullBoardAndOver() == 0 && moves) { //check if board is not full
                generateValue();  //generate a new value in an empty space
            }
            fullBoardAndOver(); //check status of board (to update val of gameOver)
        }
        return moves;
    }

    public boolean leftMove() {
        boolean moves = false;
        for (int j = 1; j <= 3; j++) {
            for (int i = 0; i <= 3; i++) {
                if (board[i][j].getValue() != 0) {
                    int mostLeftEmptyCol = j; //
                    //look thru all cols @ that row val above to find mostLeftEmptyCol
                    for (int backj = j; backj >= 0; backj--) {
                        if (board[i][backj].getValue() == 0) {
                            mostLeftEmptyCol = backj;
                        }
                    }

                    if (mostLeftEmptyCol != j) {
                        board[i][mostLeftEmptyCol].updateValue(board[i][j].getValue());
                        board[i][j].updateValue(0);
                        moves = true;
                    }
                    // update board based off mostLeftEmptyCol value;
//                        if (board[i][mostLeftEmptyCol] == board[i][j]) {
//                            board[i][mostLeftEmptyCol] = 2 * (board[i][j]);
//                        } else {
//                            board[i][mostLeftEmptyCol] = board[i][j];
//                        }
//                        board[i][j] = 0;
//                    board[i][mostLeftEmptyCol].updateValue(board[i][j].getValue());
//                    if (mostLeftEmptyCol != 0) {
//                        if (board[i][mostLeftEmptyCol - 1].getValue() ==
//                                board[i][j].getValue()) {
//                            board[i][mostLeftEmptyCol - 1].updateValue(2 * board[i][j].getValue());
//                            board[mostLeftEmptyCol][j].updateValue(0);
//                        }
//                    }
//                    board[i][j].updateValue(0);
                }
            }
        }
        return moves;
    }

    public void leftSum() {
        for (int j = 1; j <= 3; j++) {
            for (int i = 0; i <= 3; i++) {
                if (board[i][j].getValue() != 0 &&
                        board[i][j - 1].getValue() == board[i][j].getValue()) {
                    board[i][j - 1].updateValue(2 * board[i][j].getValue());
                    board[i][j].updateValue(0);
                }
            }
        }
    }

    public boolean right() {
        boolean moves = false;
        if (fullBoardAndOver() != 2) {
//            for (int j = 2; j >=0; j--) {
//                for (int i = 0; i <= 3; i++) {
//                    if (board[i][j].getValue() != 0) {
//                        int mostRightEmptyCol = j; //
//                        //look thru all cols @ that row val above to find mostRightEmptyCol
//                        for (int backj = j; backj <= 3; backj++) {
//                            if (board[i][backj].getValue() == 0) {
//                                mostRightEmptyCol = backj;
//                            }
//                        }
//                        // update board based off mostRightEmptyCol value
////                        if (board[i][mostRightEmptyCol] == board[i][j]) {
////                            board[i][mostRightEmptyCol] = 2 * (board[i][j]);
////                        } else {
////                            board[i][mostRightEmptyCol] = board[i][j];
////                        }
////                        board[i][j] = 0;
//                        board[i][mostRightEmptyCol].updateValue(board[i][j].getValue());
//                        if (mostRightEmptyCol != 3) {
//                            if (board[i][mostRightEmptyCol + 1].getValue() ==
//                                    board[i][j].getValue()) {
//                                board[i][mostRightEmptyCol + 1].updateValue(2 * board[i][j].getValue());
//                                board[mostRightEmptyCol][j].updateValue(0);
//                            }
//                        }
//                        board[i][j].updateValue(0);
//                    }
//                }
//            }
            boolean moves1 = rightMove(); //move right
            rightSum(); //add all tiles that have same num horizontally right
            boolean moves2 = rightMove(); //move right again to eliminate spaces from rightSum
            moves = moves1 || moves2;
            if (fullBoardAndOver() == 0 && moves) { //check if board is not full
                generateValue();  //generate a new value in an empty space
            }
            fullBoardAndOver(); //check status of board (to update val of gameOver)
        }
        return moves;
    }

    public boolean rightMove() {
        boolean moves = false;
        for (int j = 2; j >=0; j--) {
            for (int i = 0; i <= 3; i++) {
                if (board[i][j].getValue() != 0) {
                    int mostRightEmptyCol = j; //
                    //look thru all cols @ that row val above to find mostRightEmptyCol
                    for (int backj = j; backj <= 3; backj++) {
                        if (board[i][backj].getValue() == 0) {
                            mostRightEmptyCol = backj;
                        }
                    }

                    if (mostRightEmptyCol != j) {
                        board[i][mostRightEmptyCol].updateValue(board[i][j].getValue());
                        board[i][j].updateValue(0);
                        moves = true;
                    }
                }
            }
        }

        return moves;
    }

    public void rightSum() {
        for (int j = 2; j >= 0; j--) {
            for (int i = 0; i <= 3; i++) {
                if (board[i][j].getValue() != 0 &&
                        board[i][j + 1].getValue() == board[i][j].getValue()) {
                    board[i][j + 1].updateValue(2 * board[i][j].getValue());
                    board[i][j].updateValue(0);
                }
            }
        }
    }

//    public void verticalSum() {
//
//    }
//
//    public void horizontalSum() {
//    }

    public void generateValue() {
        //just use math.random, delete rand object
//        Random rand = new Random();
        int randomVal = (int)(Math.random() + 1) * 2;
//        int randomVal = rand.nextInt(1);
        boolean full = true;
//        for
//make list of all empty spots, and then randomly choose from there

        ArrayList<ArrayList<Integer>> emptySpots = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j].getValue() == 0) {
                    ArrayList<Integer> emptyLoc = new ArrayList<Integer>();
                    emptyLoc.add(i);
                    emptyLoc.add(j);
                    emptySpots.add(emptyLoc);
                }
            }
        }

        int max = emptySpots.size() - 1;
        int randomLoc = (int)(Math.random()) * (max);
        board[emptySpots.get(randomLoc).get(0)][emptySpots.get(randomLoc).get(1)].updateValue(randomVal);
//        while (full) {
//            int randomCol = rand.nextInt(3);
//            int randomRow = rand.nextInt(3);
//            if (board[randomRow][randomCol].getValue() == 0) {
//                board[randomRow][randomCol].updateValue(randomVal);
//                full = false;
//            }
//        }
    }

    public int fullBoardAndOver() {
        int numZeros = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j].getValue() == 0) {
                    numZeros = 1;
                    break;
                }
            }
        }
        if (numZeros > 0) {
            return 0;
        }
        else {
            if (!gameOver()) {
                return 2;
            } else {
//                updateGameHistory();
                return 3;
            }
        }
    }

    public boolean gameOver() {
        boolean isOver = false;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if ((i == 0 || i == 3) && (j != 0) && (j != 3)) {
                    if (board[i][j - 1].getValue() == board[i][j].getValue()) {
                        isOver = true;
                    } else if (board[i][j + 1].getValue() == board[i][j].getValue()) {
                        isOver = true;
                    }
                } else if ((i != 0) && (i != 3) && (j == 0 || j == 3)) {
                        if (board[i - 1][j].getValue() == board[i][j].getValue()) {
                            isOver = true;
                        } else if (board[i + 1][j].getValue() == board[i][j].getValue()) {
                            isOver = true;
                        }
                } else if ((i != 0) && (i != 3) && (j != 0) && (j != 3)){
                        if (board[i][j - 1].getValue() == board[i][j].getValue()) {
                            isOver = true;
                        } else if (board[i + 1][j].getValue() == board[i][j].getValue()) {
                            isOver = true;
                        } else if (board[i][j + 1].getValue() == board[i][j].getValue()) {
                            isOver = true;
                        } else if (board[i - 1][j].getValue() == board[i][j].getValue()) {
                            isOver = true;
                        }
                    }
                }

        }
        System.out.println(isOver);
        this.gameOver = isOver;
        return isOver;
    }

    public int getNumTurns() {
//        List<String> i = new ArrayList<String>();
//        ArrayList<String> j = new ArrayList<String>();
//        List<Object> k;

//        i = j;
//        j = i;
//        k = i;
//        k = j;
        return numTurns;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public int getHighestTile() {
        int highestTileValue = 0;
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (board[i][j].getValue() > highestTileValue) {
                    highestTileValue = board[i][j].getValue();
                }
            }
        }
        return highestTileValue;
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public void updateGameHistory() {


        ArrayList<Integer> turnsAndValue = new ArrayList<Integer>();
        turnsAndValue.add(getNumTurns());
        turnsAndValue.add(getHighestTile());
        this.gameHistory.put(this.gameNumber, turnsAndValue);

        System.out.println(this.gameNumber + " " + getNumTurns() + " " + getHighestTile());

        this.gameNumber++;

    }

    public void updateGameHistory(int gameNum, int turns, int val) {
        ArrayList<Integer> turnsAndValue = new ArrayList<Integer>();
        turnsAndValue.add(turns);
        turnsAndValue.add(val);
        gameHistory.put(gameNum, turnsAndValue);
    }

    public void updateNumturns(int turns) {
        this.numTurns = turns;
    }

    public String getGameHistory() {
        String gameHistoryString = "";
        for(Map.Entry<Integer, ArrayList<Integer>> entry : gameHistory.entrySet()) {
            int gameNum = entry.getKey();
            int turns = entry.getValue().get(0);
            int highestTile = entry.getValue().get(1);

            gameHistoryString += "Game " + gameNum + ":\n    Number of Turns - " + turns +
                    ", Highest Tile - " + highestTile + "\n";
//            System.out.println("Game " + gameNum + ":      Number of Turns - " + turns +
//                    " Highest Tile: " + highestTile);
        }
        return gameHistoryString;
    }

    public String getBoardForSaving() {
        String stringBoard = "";
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                stringBoard += String.valueOf(this.board[i][j].getValue());
                stringBoard += " ";
            }
        }
        return stringBoard;
    }

    public String getGameHistoryForSaving() {
        String stringGameHistory = "";
        for(Map.Entry<Integer, ArrayList<Integer>> entry : gameHistory.entrySet()) {
            int gameNum = entry.getKey();
            int turns = entry.getValue().get(0);
            int highestTile = entry.getValue().get(1);

//            stringGameHistory += (String.valueOf(gameNum) + " " +
//                    String.valueOf(turns) + " " + String.valueOf(highestTile) + " ");
            stringGameHistory += String.valueOf(gameNum);
            stringGameHistory += " ";
            stringGameHistory += String.valueOf(turns);
            stringGameHistory += " ";
            stringGameHistory += String.valueOf(highestTile);
            stringGameHistory += " ";
        }
        System.out.println(stringGameHistory);
        return stringGameHistory;
    }

    public void writeStringsToFile(
            String stringsToWrite, String filePath,
            boolean append
    ) {
        BufferedWriter bw = null;
        File file = Paths.get(filePath).toFile();
        try {
            FileWriter fileWriter = new FileWriter(file, append);
            bw = new BufferedWriter(fileWriter);
            System.out.println(stringsToWrite);
            bw.write(stringsToWrite);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            try {
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Error");
        }
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                System.out.print(board[i][j].getValue());
                if (j < 3) {
                    System.out.print(" | ");
                }
            }
            if (i < 3) {
                System.out.println("\n--------------");
            }
        }
    }


    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public Tile getCell(int r, int c) {
        return board[r][c];
    }

    public TreeMap<Integer, ArrayList<Integer>> getGameHistoryTreeMap() {
        return this.gameHistory;
    }

    public Tile[][] getBoard() {
        return board;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        TwentyFourtyEight t = new TwentyFourtyEight();

        t.playTurn(KeyEvent.VK_UP);
        t.printGameState();

        t.playTurn(KeyEvent.VK_LEFT);
        t.printGameState();

        t.playTurn(KeyEvent.VK_DOWN);
        t.printGameState();

        t.reset();

        t.playTurn(KeyEvent.VK_RIGHT);
        t.printGameState();

        t.playTurn(KeyEvent.VK_UP);
        t.printGameState();

        t.reset();


        String stuff = t.getGameHistoryForSaving();
        System.out.println(stuff);

        t.playTurn(KeyEvent.VK_RIGHT);
        t.printGameState();

        t.playTurn(KeyEvent.VK_UP);
        t.printGameState();


//        TwentyFourtyEight t = new TwentyFourtyEight();
//        t.printGameState();
//
//        t.playTurn(KeyEvent.VK_UP);
//        t.printGameState();
//
//        t.playTurn(KeyEvent.VK_DOWN);
//        t.printGameState();
//
//        t.playTurn(KeyEvent.VK_UP);
//        t.printGameState();
//
//        t.playTurn(KeyEvent.VK_LEFT);
//        t.printGameState();
//
//        t.playTurn(KeyEvent.VK_UP);
//        t.printGameState();
//
//        t.playTurn(KeyEvent.VK_RIGHT);
//        t.printGameState();
//
//        t.playTurn(KeyEvent.VK_UP);
//        t.printGameState();
    }
}
