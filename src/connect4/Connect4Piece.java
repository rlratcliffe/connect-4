/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author R. L. Ratcliffe
 */
public class Connect4Piece extends JComponent {
    // TODO: Make images resizable
    
    private BufferedImage image;

    public Connect4Piece(String type) {
        
        if (type.equals("yellow") || type.equals("red")
            || type.equals("empty") || type.equals("filler")) {            
        
        File file = new File("connect4images/" + type + ".png");

        try {
            image = ImageIO.read(file);
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL.");
        }
        catch (IOException ex) {
                Logger.getLogger(Connect4Piece.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } else {
            System.out.println("Connect 4 Piece not valid.");
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }

    }
}
