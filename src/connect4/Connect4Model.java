/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.util.ArrayList;

/**
 *
 * @author R. L. Ratcliffe
 */
public class Connect4Model {

    private int[][] boardModel;

    private ArrayList<Integer> colChoice;
    private ArrayList<Player> players;

    private int rows;
    private int cols;

    private boolean colChanged;
    private boolean pieceAdded;

    private Player currentPlayer;

    private boolean ifWon;

    private boolean fullBoardDebug1;
    private boolean fullBoardDebug2;

    public Connect4Model() {
        initBoard();
    }

    public void initBoard() {
        colChoice = new ArrayList<>();
        players = new ArrayList<>();

        rows = 6;
        cols = 7;
        
        // Testing methods

//        testDiagLeftHuman();
//        testDiagRightHuman();
//        testHoriz();
//        testVert();
//        testDiagLeftComp();
//        testDiagRightComp();
//        fullBoardHuman();
//        fullBoardComp();

        blankBoard();
        initColChoice();

        colChanged = false;
        pieceAdded = false;

        ifWon = false;

    }

    public void blankBoard() {
        boardModel = new int[rows][cols];
    }

    // Test cases
    
    public void testDiagLeftHuman() {
        boardModel = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0},
            {1, 2, 2, 1, 2, 1, 0}
        };

    }

    public void testDiagRightHuman() {
        boardModel = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 2, 2},
            {0, 1, 2, 1, 2, 2, 1}

        };

    }

    public void testDiagLeftComp() {
        boardModel = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0},
            {2, 1, 1, 2, 1, 2, 0}
        };

    }

    public void testDiagRightComp() {
        boardModel = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 2},
            {0, 0, 0, 0, 0, 2, 2},
            {0, 0, 0, 0, 0, 1, 1},
            {0, 2, 1, 2, 1, 1, 2}

        };

    }

    public void testHoriz() {
        boardModel = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 2},
            {0, 0, 0, 0, 0, 1, 2},
            {0, 0, 2, 2, 1, 2, 2},
            {1, 0, 1, 1, 2, 2, 1}

        };

    }

    public void testVert() {
        boardModel = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0},
            {1, 2, 0, 2, 0, 0, 0},
            {1, 2, 2, 1, 2, 2, 0}
        };

    }

    // https://twitter.com/mreducationator/status/682334249240821760
    public void fullBoardHuman() {
        fullBoardDebug1 = true;
        boardModel = new int[][]{
            {1, 2, 1, 0, 1, 2, 2},
            {2, 2, 1, 1, 2, 1, 1},
            {2, 2, 2, 1, 2, 2, 2},
            {1, 1, 1, 2, 1, 1, 1},
            {2, 2, 1, 1, 1, 2, 2},
            {1, 2, 2, 1, 1, 2, 2}
        };
    }

    public void fullBoardComp() {
        fullBoardDebug2 = true;
        boardModel = new int[][]{
            {1, 2, 1, 0, 1, 2, 0},
            {2, 2, 1, 1, 2, 1, 1},
            {2, 2, 2, 1, 2, 2, 2},
            {1, 1, 1, 2, 1, 1, 1},
            {2, 2, 1, 1, 1, 2, 2},
            {1, 2, 2, 1, 1, 2, 2}
        };
    }

    // End of test cases 
    public int[][] getBoard() {
        return boardModel;
    }

    public void printBoard() {
        for (int row = 0; row < rows; row++) {
            System.out.println("");
            for (int col = 0; col < cols; col++) {
                System.out.print(boardModel[row][col] + " ");
            }
        }

    }

    public void updateModelBoard(int row, int col, int id) {
        boardModel[row][col] = id;

        printBoard();

    }

    public void initColChoice() {
        for (int i = 0; i <= 6; i++) {
            if (i == 0) {
                colChoice.add(1);
            } else {
                colChoice.add(0);
            }

        }
    }

    public int getColChoice() {
        return colChoice.indexOf(currentPlayer.getID());
    }

    public void colMoved(String direction) {

        int index = getColChoice();

        if (direction.equals("left") && index > 0) {
            colChoice.remove(index);
            colChoice.add(index - 1, currentPlayer.getID());
        } else if (direction.equals("right") && index < 6) {
            colChoice.remove(index);
            colChoice.add(index + 1, currentPlayer.getID());

        }
        colChanged = true;

    }

    public boolean getIfColChanged() {

        return colChanged;
    }

    public void setColChanged(boolean colChanged) {
        this.colChanged = colChanged;
    }

    public int findEmptyCell(int col) {
        int emptyRow = -1;


        for (int row = (rows - 1); row >= 0; row--) {
            int cell = boardModel[row][col];

            if (cell == 0) {
                emptyRow = row;
                break;
            }

        }
        
        return emptyRow;
    }

    public int[] colSelected() {

        int col = getColChoice();
        int row = findEmptyCell(col);

        updateModelBoard(row, col, currentPlayer.getID());

        pieceAdded = true;

        checkIfWon(row, col);

        return new int[]{row, col};
    }

    public int computerColSelected(int col) {
        int choice = -1;
        int row = findEmptyCell(col);
        
        if (row != -1) {

            updateModelBoard(row, col, currentPlayer.getID());
            checkIfWon(row, col);

            int newRow = (row * 1) * 7;
            int newCol = col + 1;
            choice = newRow + newCol;

            choice -= 1;
        } 


        return choice;
    }

    public boolean getIfPieceAdded() {
        return pieceAdded;
    }

    public void setPieceAdded(boolean pieceAdded) {
        this.pieceAdded = pieceAdded;
    }

    public void printColChoice() {
        System.out.println("\n" + colChoice.toString());
    }

    public void initSinglePlayer(String firstChoice) {

        Player first = new Player(1);
        first.setColor(firstChoice);
        // TODO: Have user set their name
        first.setName("User");
        Computer second = new Computer(2);
        second.setColor(first);
        second.setName("Computer");
        players.add(first);
        players.add(second);

        currentPlayer = players.get(0);
//        if (fullBoardDebug1 = true) {
//            players.get(0).setTurns(1);
//            players.get(1).setTurns(0);
//        } else {
//            players.get(0).setTurns(1);
//            players.get(1).setTurns(1);
//        }

    }

    public String getPlayersColor(int id) {
        String color = null;
        for (Player player : players) {
            if (player.getID() == id) {
                color = player.getColor();
            }
        }
        return color;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public boolean checkTurns() {
        boolean turnsLeft = true;
        for (Player player : players) {
            if (!player.hasTurns()) {
                turnsLeft = false;
            }
        }
        return true;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setNextPlayer() {
        if (currentPlayer.getID() == 1) {
            currentPlayer = players.get(1);
        } else if (currentPlayer.getID() == 2) {
            currentPlayer = players.get(0);
        }
    }

    public void setIfWon(boolean ifWon) {
        this.ifWon = ifWon;
    }

    public boolean getIfWon() {
        return ifWon;
    }

    public void checkIfWon(int row, int col) {
        int[] startPointLeft = getLeftDiagStart(row, col);
        int[] startPointRight = getRightDiagStart(row, col);

        if (checkVertical(col) || checkHorizontal(row)
                || checkLeftDiag(startPointLeft) || checkRightDiag(startPointRight)) {
            ifWon = true;
        }

    }

    public boolean checkVertical(int col) {
        boolean ifWon = false;
        int count = 0;

        for (int row = 5; row >= 0; row--) {
            if (currentPlayer.getID() == boardModel[row][col]) {
                count++;
            } else {
                count = 0;
            }

            if (count == 4) {
                ifWon = true;
                break;
            }
        }

        return ifWon;
    }

    public boolean checkHorizontal(int row) {
        boolean ifWon = false;
        int count = 0;

        for (int col = 0; col <= 6; col++) {
            if (currentPlayer.getID() == boardModel[row][col]) {
                count++;
            } else {
                count = 0;
            }

            if (count == 4) {
                ifWon = true;
                break;
            }
        }

        return ifWon;
    }

    public int[] getLeftDiagStart(int rowChoice, int colChoice) {
        return getStart(rowChoice, colChoice, 0, "left");
    }

    public boolean checkLeftDiag(int[] startPoint) {
        return diagSearch(startPoint, 6, "left");
    }

    public int[] getRightDiagStart(int rowChoice, int colChoice) {
        return getStart(rowChoice, colChoice, 6, "right");
    }

    public boolean checkRightDiag(int[] startPoint) {
        return diagSearch(startPoint, 0, "right");
    }

    // Find starting point of diagonal
    public int[] getStart(int rowChoice, int colChoice, int colEnd, String LorR) {
        int startRow = 0;
        int startCol = 0;

        if ((rowChoice - 1) > 0) {
            rowChoice -= 1;
        }
        for (int row = rowChoice; row >= 0; --row) {
            if (LorR.equals("right")) {
                if (colChoice < colEnd) {
                    colChoice++;
                }
            } else if (LorR.equals("left")) {
                if (colChoice > colEnd) {
                    colChoice--;
                }
            }

            if (colChoice == colEnd || row == 0) {
                startRow = row;
                startCol = colChoice;
                break;
            }
        }

        return new int[]{startRow, startCol};
    }

    // Using starting point, search whole diagonal
    public boolean diagSearch(int[] startPoint, int colEnd, String LorR) {
        boolean ifWon = false;
        int count = 0;
        int rowChoice = startPoint[0];
        int colChoice = startPoint[1];

        for (int row = rowChoice; row <= 5; row++) {

            if (currentPlayer.getID() == boardModel[row][colChoice]) {
                count++;
            } else {
                count = 0;
            }

            if (LorR.equals("right")) {
                if (colChoice > colEnd) {
                    colChoice--;
                } else {
                    break;
                }
            } else if (LorR.equals("left")) {
                if (colChoice < colEnd) {
                    colChoice++;
                } else {
                    break;
                }
            }

            if (count == 4) {
                ifWon = true;
                break;
            }
        }

        return ifWon;
    }

}
