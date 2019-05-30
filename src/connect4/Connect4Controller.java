/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author R. L. Ratcliffe
 */
public class Connect4Controller extends JPanel {

    private Connect4Model model;
    private Connect4View board;

    private boolean gameOver = false;

    private JButton button;

    private int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private InputMap imap;
    private ActionMap amap;

    public Connect4Controller() {

    }

    private void updateRow(String direction) {
        String color = model.getCurrentPlayer().getColor();

        if (model.getCurrentPlayer().getStatus().equals("waiting")) {
            int prevCol = model.getColChoice();
            model.colMoved(direction);
            int col = model.getColChoice();

            if (prevCol != col) {
                if (direction.equals("left")) {
                    board.updateRowLeft(col, color);
                } else if (direction.equals("right")) {
                    board.updateRowRight(col, color);
                }
            }

        }

    }

    // Reference for learning to use key bindings: ftp.ecs.csus.edu/clevengr/133/handouts/UsingJavaKeyBindings.pdf
    private void startGameBindings() {

        amap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRow("left");
            }
        });
        amap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRow("right");
            }
        });
        amap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getCurrentPlayer().getStatus().equals("waiting")) {
                    model.colSelected();

                    clearKeyBindings();

                    board.updateBoard();

                    int delay = 1500; //milliseconds
                    ActionListener taskPerformer = new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            endTurn();
                        }
                    };
                    Timer timer = new Timer(delay, taskPerformer);
                    timer.setRepeats(false);
                    timer.start();

                }
            }
        });

        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down");

    }

    private void startMenuBindings() {

        amap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.updateMenu("left");
            }
        });
        amap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.updateMenu("right");
            }
        });
        amap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right");
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down");

    }

    public void viewButtonListen() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearKeyBindings();
                newGame();
            }
        });
    }

    private void clearKeyBindings() {

        imap.clear();
    }

    public void startTurn() {


        if (model.getCurrentPlayer().hasTurns()) {

            model.getCurrentPlayer().setStatus("waiting");

            if (model.getCurrentPlayer().getID() == 1) {// if human/first player

                board.updateBoard();
                startGameBindings();

            } else if (model.getCurrentPlayer().getID() == 2) { // if computer

                Computer computer = (Computer) model.getCurrentPlayer();
                int selectedCol = computer.chooseColumn();
                int choice = model.computerColSelected(selectedCol);

                boolean noneEmpty = false;
                
                board.updateText("waitForComputer");

                if (choice != -1) {
                    computerTurn(choice);
                } else {
                    

                    int[] choices = {0, 1, 2, 3, 4, 5, 6};

                    ArrayList<Integer> choicesList = new ArrayList<>();
                    for (int i = 0; i < choices.length; i++) {
                        if (choices[i] != selectedCol) {
                            choicesList.add(choices[i]);
                        }

                    }

                    Collections.shuffle(choicesList);
                    for (int i = 0; i < choicesList.size(); i++) {
                        choice = model.computerColSelected(choicesList.get(i));
                        if (choice != -1) {
                            computerTurn(choicesList.get(i));
                        } else if (i == 5 && choice == -1) {
                            gameOver = true;
                            endTurn();
                        }

                    }

                }

            }
        } else {
            endTurn();
            if (model.getCurrentPlayer().getID() == 2) {
                gameOver = true;
            }
        }

    }

    public void computerTurn(int choice) {
        board.updateText("waitForComputer", choice);

        int delay = 2500; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                endTurn();

            }
        };
        Timer timer = new Timer(delay, taskPerformer);
        timer.setRepeats(false);
        timer.start();
    }

    public void endTurn() {

        if (model.getIfWon()) {
            board.updateText("playerWon", 50);
        } else if (gameOver) {
            board.updateText("gameOver", 50);
        } else {
            board.removeMessage();
            model.getCurrentPlayer().setStatus("done");
            model.getCurrentPlayer().turnTaken();
            model.setColChanged(false);
            model.setPieceAdded(false);
            model.setNextPlayer();
            startTurn();
        }
    }

    public void newGame() {
        model = new Connect4Model();
        board = new Connect4View(model);

        removeAll();

        imap = board.getInputMap(mapName);
        amap = board.getActionMap();
        button = board.getButton();

        board.addMenu();
        add(board);
        startMenuBindings();

        revalidate();
        repaint();

    }

    public void startGame() {

        board.clear();
        board.addGame();

        model.initSinglePlayer(board.getColorSelected());
        board.updateBoard();
        clearKeyBindings();

        startTurn();
        viewButtonListen();
    }

}
