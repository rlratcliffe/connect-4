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
public class Player {


    private final int id;
    private String color; // yellow or red
    private String name;
    private final ArrayList<String> colors = new ArrayList<>();
    private int turns;
    private String turnStatus; // true for taking turn

    public Player(int number) {
        id = number;
        turns = 21;
        turnStatus = "waiting";

        initColors();
    }

    public int getID() {
        return id;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void initColors() {
        colors.add("yellow");
        colors.add("red");
    }

    public void setColor(String color) {
        if (colors.contains(color)) {
            this.color = color;
        }

    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void turnTaken() {
        this.turns--;
    }

    public int getTurns() {
        return turns;
    }
    
    public void setTurns(int turns) {
        this.turns = turns;
    }


    public boolean hasTurns() {
        return (this.turns > 0);

    }
    
    public String getStatus() {
        return turnStatus;
    }
    
    public void setStatus(String status) {
        turnStatus = status;
    }

}
