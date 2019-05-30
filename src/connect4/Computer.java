/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author R. L. Ratcliffe
 */
public class Computer extends Player {
    
    public Computer(int number) {
        super(number);
        setStatus("done");
    }
    
    // Choose other color not chosen
    public void setColor(Player player) {
        String firstColor = player.getColor();
        
        ArrayList<String> colors = getColors();
        
        colors.remove(firstColor);
        
        super.setColor(colors.get(0));
        
    }
    
    public int chooseColumn() {
        Random rand = new Random();
        int value = rand.nextInt(6);
        System.out.println("Computer chose random number " + value);
        return value;
    }
}
