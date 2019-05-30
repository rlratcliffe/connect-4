/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author R. L. Ratcliffe
 */
public class Connect4View extends JPanel {

    private int rows = 7;
    private int cols = 7;
    private GridLayout gridBoard;
    private GridLayout gridSelector;
    private LayoutManager overlay;

    private JComponent[][] tiles;
    private JComponent[] select;

    private JLabel turns;
    private JLabel message;
    private JPanel boardOverlay;
    private JPanel messageBrd;

    private JPanel boardHolder;
    private JPanel board;
    private JPanel selection;

    private JPanel menu;

    private JLabel yellow;
    private JLabel red;
    CompoundBorder raisedPadding = BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED),
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
    CompoundBorder loweredPadding = BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.LOWERED),
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
    private String colorSelected;

    private JButton returnToMenu;

    GridBagConstraints gbc;
    GridBagConstraints gbcm;

    Color blueBg = new Color(58, 102, 173);
    Color grayBg = new Color(172, 172, 173);

    private Connect4Model model;

    public Connect4View(Connect4Model model) {
        this.model = model;

        board = new JPanel();
        selection = new JPanel();
        returnToMenu = new JButton("Reset");
    }

    public void addGame() {
        removeAll();

        boardOverlay = new JPanel();
        messageBrd = new JPanel();
        boardHolder = new JPanel();

        boardHolder.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.LINE_START;

        turns = new JLabel();
        turns.setText("Turns left: " + 21);
        boardHolder.add(turns, gbc);
        gbc.gridx = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        boardHolder.add(returnToMenu, gbc);

        setMinimumSize(new Dimension(545, 550));

        initBoard();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        selection.setLayout(gridSelector);
        selection.setPreferredSize(new Dimension(545, 80));
        selection.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 5));
        boardHolder.add(selection, gbc);

        gbc.gridy = 2;
        board.setLayout(gridBoard);
        board.setPreferredSize(new Dimension(545, 400));
        board.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        board.setBackground(blueBg);
        boardHolder.setOpaque(true);
        boardHolder.add(board, gbc);

        overlay = new OverlayLayout(this);
        setLayout(overlay);

        boardHolder.setAlignmentY(0.5f);
        boardHolder.setAlignmentX(0.5f);

        messageBrd.setLayout(new BorderLayout());
        message = new JLabel("");
        message.setHorizontalAlignment(JLabel.CENTER);
        message.setFont(new Font("Helvetica", Font.BOLD, 20));
        messageBrd.setBackground(new Color(58, 102, 173, 0));
        messageBrd.setOpaque(true);
        message.setForeground(Color.white);
        messageBrd.setMaximumSize(new Dimension(545, 430));
        messageBrd.setAlignmentX(0.5f);
        messageBrd.setAlignmentY(0.32f);
        messageBrd.add(message);

        add(messageBrd);
        add(boardHolder);

        revalidate();
        repaint();

    }

    public void initBoard() {
        rows = 6;
        cols = 7;

        gridSelector = new GridLayout(0, cols, 5, 5);
        gridBoard = new GridLayout(0, cols, 5, 5);

        tiles = new JComponent[rows][cols];
        select = new JComponent[cols];

    }

    public void updateRowRight(int col, String color) {

        selection.remove(col - 1);
        selection.add(new Connect4Piece(color), col);
        selection.repaint();
        selection.revalidate();

    }

    public void updateRowLeft(int col, String color) {

        selection.remove(col + 1);
        selection.add(new Connect4Piece(color), col);
        selection.repaint();
        selection.revalidate();

    }

    public void addPiece(int choice) {

        board.remove(choice);
        board.add(new Connect4Piece(model.getCurrentPlayer().getColor()), choice);
        validate();
        repaint();

    }

    public void updateSelector() {
        selection.removeAll();
        for (int col = 0; col < cols; col++) {

            if (col == model.getColChoice()) {
                select[col] = new Connect4Piece(model.getCurrentPlayer().getColor()); // switch with current player color in controller
            } else {
                select[col] = new Connect4Piece("filler");
            }

            selection.add(select[col]);
        }

    }

    public void removeMessage() {
        messageBrd.removeAll();
        messageBrd.setBackground(new Color(0, 0, 0, 0));

    }

    public void updateBoard() {

        board.removeAll();

        updateSelector();

        for (int row = 0; row < rows; row++) {

            for (int col = 0; col < cols; col++) {

                int playerID = model.getBoard()[row][col];
                String playerColor = null;
                if (playerID == 1 || playerID == 2) {
                    playerColor = model.getPlayersColor(playerID);
                }

                switch (playerID) {
                    case 1:
                    case 2:
                        tiles[row][col] = new Connect4Piece(playerColor);
                        break;
                    default:
                        tiles[row][col] = new Connect4Piece("empty");
                        break;

                }
                board.add(tiles[row][col]);

            }

        }

        board.repaint();
        board.revalidate();

        turns.setText("Turns left: " + model.getCurrentPlayer().getTurns());
    }
    
        public void updateText(String s, int choice) {
        // TODO: Show winner's pieces/highlight them 

        selection.removeAll();

        messageBrd.setBackground(new Color(58, 102, 173, 200));
        repaint();

        if (s.equals("waitForComputer")) {
            message.setText("Computer's Turn");
        } else if (s.equals("playerWon")) {
            String winner = model.getCurrentPlayer().getName();
            if (winner.equals("User")) {
                winner = "You";
            }
            message.setText(winner + " won!! ");
            //
        } else if (s.equals("gameOver")) {
            message.setText("Game over, no winner.");
            //
        }
        messageBrd.add(message);

        if (choice < 42) {
            int delay = 1500; //milliseconds
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {

                    addPiece(choice);
                }
            };
            Timer timer = new Timer(delay, taskPerformer);
            timer.setRepeats(false);
            timer.start();
        }

    }

    public void updateText(String s) {
        // TODO: Show winner's pieces/highlight them 

        selection.removeAll();

        messageBrd.setBackground(new Color(58, 102, 173, 200));
        repaint();

        if (s.equals("waitForComputer")) {
            message.setText("Computer's Turn");
        } else if (s.equals("playerWon")) {
            String winner = model.getCurrentPlayer().getName();
            if (winner.equals("User")) {
                winner = "You";
            }
            message.setText(winner + " won!! ");
            //
        } else if (s.equals("gameOver")) {
            message.setText("Game over, no winner.");
            //
        }
        messageBrd.add(message);

    }

    public JButton getButton() {
        return returnToMenu;
    }

    public void addMenu() {
        menu = new JPanel();

        menu.setLayout(new GridBagLayout());
        gbcm = new GridBagConstraints();
        gbcm.anchor = GridBagConstraints.CENTER;
        gbcm.insets = new Insets(10, 10, 10, 10);

        JLabel intro = new JLabel("Choose Your Color:");
        gbcm.gridx = 0;
        gbcm.gridy = 0;
        gbcm.gridwidth = 10;

        menu.add(intro, gbcm);

        menu.setPreferredSize(new Dimension(540, 500));

        menu.setBackground(new Color(58, 102, 173, 50));

        gbcm.gridy = 1;
        gbcm.gridx = 0;
        gbcm.gridwidth = 5;
        gbcm.gridheight = 3;

        JLabel leftArrow = new JLabel("");
        ImageIcon left = new ImageIcon("connect4images/left.png");
        leftArrow.setIcon(left);
        menu.add(leftArrow, gbcm);

        gbcm.gridx = 5;
        JLabel rightArrow = new JLabel("");
        ImageIcon right = new ImageIcon("connect4images/right.png");
        rightArrow.setIcon(right);
        menu.add(rightArrow, gbcm);

        ImageIcon yellowI = new ImageIcon("connect4images/yellow.png");
        yellow = new JLabel("");
        yellow.setIcon(yellowI);
        yellow.setOpaque(true);
        yellow.setBackground(blueBg);
        yellow.setBorder(loweredPadding);
        colorSelected = "yellow";

        red = new JLabel("");
        ImageIcon redI = new ImageIcon("connect4images/red.png");
        red.setIcon(redI);
        red.setOpaque(true);
        red.setBackground(grayBg);
        red.setBorder(raisedPadding);

        gbcm.gridx = 0;
        gbcm.gridy = 4;

        menu.add(yellow, gbcm);
        gbcm.gridx = 5;
        menu.add(red, gbcm);

        menu.revalidate();
        menu.repaint();
        add(menu);

        isReady();

    }

    public void updateMenu(String direction) {
        if (direction.equals("left")) {
            yellow.setBorder(loweredPadding);
            yellow.setBackground(blueBg);
            red.setBorder(raisedPadding);
            red.setBackground(grayBg);
            colorSelected = "yellow";
        } else if (direction.equals("right")) {
            red.setBorder(loweredPadding);
            red.setBackground(blueBg);
            yellow.setBorder(raisedPadding);
            yellow.setBackground(grayBg);
            colorSelected = "red";
        }
    }

    public String getColorSelected() {
        return colorSelected;
    }

    public void isReady() {
        gbcm.gridx = 0;
        gbcm.gridwidth = 10;
        JLabel ready = new JLabel("Ready?");
        gbcm.gridy = 7;
        menu.add(ready, gbcm);

        gbcm.gridy = 10;
        JLabel downArrow = new JLabel("");
        ImageIcon down = new ImageIcon("connect4images/down.png");
        downArrow.setIcon(down);
        menu.add(downArrow, gbcm);
    }

    public void clear() {
        removeAll();
    }

}
