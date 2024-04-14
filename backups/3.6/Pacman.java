// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pacman {
    private int x, y, w = 40, h = 50, oldX, oldY, direction, yFixed = 0, frameIteration = 1;
    private static BufferedImage pacmanImages[] = null;
    private double speed;
    private final int MAX_IMAGES_PER_DIRECTION = 4;

    // Default constructor
    public Pacman() {
        this(350, 350, 10, 3);
    }

    public Pacman(int x, int y, int speed, int direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;

        if (pacmanImages == null) {
            // Initializes array of player images
            pacmanImages = new BufferedImage[MAX_IMAGES_PER_DIRECTION * 4 + 1];
            for (int i = 1; i < MAX_IMAGES_PER_DIRECTION * 4 + 1; i++) {
                pacmanImages[i] = View.loadImage("man" + i + ".png");
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

    public void setYFixed(int yFixed) {
        this.yFixed = yFixed;
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

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    //----------------------------------------------------------------


    public void update() {    }

    // Iterates the current frame to animate character
    public void iterateFrame(){
        frameIteration++;
        // Sets current frame to 1 if it exceeds 4
        if (frameIteration > MAX_IMAGES_PER_DIRECTION) {
            frameIteration = 1;
        }
    }   

    // Method calls for player to be drawn
    public void drawYourself(Graphics g) {
        // System.out.println(" y: " + y + " - yFixed: " + (yFixed));
        g.drawImage(pacmanImages[MAX_IMAGES_PER_DIRECTION* direction + frameIteration], x, yFixed, w, h, null);
    }

    // Uses parameters from model to determine current direction and change character position
    public void movePlayer(int dir) {
        
        direction = dir;
        if (direction == 0) {
            x -= speed;
        }
        if (direction == 1) {
            y -= speed;
        }
        if (direction == 2) {
            x += speed;
        }
        if (direction == 3) {
            y += speed;
        }
        // oldX = x;
        // oldY = y;
    }

    // (DOES NOT WORK) - Prevents player from being drawn within a collision
    public void getOutOfWall() {
        // x = oldX;
        // y = oldY;


        // Delete before submission \/
        // System.out.println("COLLISION!!!");
    }

    // Custom toString() method used for debugging
    @Override 
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", " + y + ")";
    }
}