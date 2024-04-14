// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pacman {
    private int x, y, w = 50, h = 50, direction, yFixed = 350, scrollPosYView;
    private static BufferedImage pacmanImages[] = null;
    private double speed;

    public Pacman() {
        x = 350;
        y = 0;
        direction = 3;
        
        if (pacmanImages == null) {
            // Initializes array of player images
            pacmanImages = new BufferedImage[12];
            for (int i = 0; i < pacmanImages.length; i++) {
                int j = i + 1;
                pacmanImages[i] = View.loadImage("pacman" + j + ".png");
            }
        }
    }

    // public void drawPlayer(Graphics g) {
        
    // }

    // Getters and setters
    //----------------------------------------------------------------
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }
    //----------------------------------------------------------------

    public void update() {     }

    public void drawYourself(Graphics g) {
        
        // Determines camera y-axis behavior	
		scrollPosYView = y;
		if (y <= -300) {
			scrollPosYView = -300;
			yFixed = y + 650;
		}
		else if (y >= 300) {
			scrollPosYView = 300;
			yFixed = y + 50;
		}
        
        // Determines horizontal warping behavior
		if (x >= Game.MAP_WIDTH) {
			x = Game.MAP_WIDTH - 800;
		}
		else if (x <= Game.MAP_WIDTH - 800) {
			x = Game.MAP_WIDTH;
		}
        
        // Draws initial player image - with collision detection (DOESN'T WORK)
		for (int i = 0; i < 2; i++) {
			g.drawImage(pacmanImages[(direction * 3) + i], x, yFixed, 50, 50, null);
		}
    }

    @Override 
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", " + y + ")";
    }
}