// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pacman {
    private int x, y, w = 50, h = 50, direction, yFixed = 350, scrollPosYView, frameIteration = 1;
    private static BufferedImage pacmanImages[] = null;
    private double speed;
    private final int MAX_IMAGES_PER_DIRECTION = 3;

    public Pacman() {
        x = 350;
        y = 0;
        speed = 10;
        direction = 3;
        
        if (pacmanImages == null) {
            // Initializes array of player images
            pacmanImages = new BufferedImage[MAX_IMAGES_PER_DIRECTION * 4 + 1];
            /* MAX_IMAGES_PER_DIRECTION * 4 + 1
            MAX*dir + current
            CurrentImage + 0 * MAX_IMAGES_PER_DIRECTION => 1,2,3
            CurrentImage + 1 * MAX_IMAGES_PER_DIRECTION => 1+3,2+3,3+3 = 4,5,6
            CurrentImage + 2 * MAX_IMAGES_PER_DIRECTION => 1+6,2+6,3+6 = 7,8,9
            CurrentImage + 3 * MAX_IMAGES_PER_DIRECTION => 1+9,2+9,3+9 = 10,11,12 */
            for (int i = 1; i < MAX_IMAGES_PER_DIRECTION * 4 + 1; i++) {
                pacmanImages[i] = View.loadImage("pacman" + i + ".png");
            }
        }
    }

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

    public int getYFixed() {
        return yFixed;
    }

    public int getDirection() {
        return direction;
    }
    //----------------------------------------------------------------

    public void update() {     }

    public void iterateFrame(){
        frameIteration++;
        if (frameIteration > 3) {
            frameIteration = 1;
        }
    }
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

        // MAX*dir + current
        // Draws initial player image - with collision detection (DOESN'T WORK)
		for (int i = 1; i < 3; i++) {
			g.drawImage(pacmanImages[MAX_IMAGES_PER_DIRECTION* direction + i], x, yFixed, w, h, null);
		}
    }

    @Override 
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", " + y + ")";
    }
}