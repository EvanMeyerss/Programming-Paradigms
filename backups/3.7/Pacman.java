// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pacman {
    private int x, y, prevX, prevY, w = 40, h = 50, direction, yFixed = 0, frameIteration = 1;
    private static BufferedImage pacmanImages[] = null;
    private double speed;
    private final int MAX_IMAGES_PER_DIRECTION = 4;
    // private boolean moveLeft, moveUp, moveRight, moveDown;

    // Default constructor
    public Pacman() {
        this(350, 350, 10, 3);
    }

    // Constructor
    public Pacman(int x, int y, int speed, int direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;

        // Lazy loading
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

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public void setYFixed(int yFixed) {
        this.yFixed = yFixed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    // Sets player speed to normal
    public void normalSpeed() {
        speed = 10;
    }

    // Lets player sprint
    public void sprintSpeed() {
        speed = 15;
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
        g.drawImage(pacmanImages[MAX_IMAGES_PER_DIRECTION * direction + frameIteration], x, yFixed, w, h, null);
    }

    // Uses parameters from model to determine current direction and change character position
    public void movePlayer(int dir) {
        
        direction = dir;
        if (direction == 0) {
            x -= speed;
            // moveLeft = true;
        }
        if (direction == 1) {
            y -= speed;
            // moveUp = true;
        }
        if (direction == 2) {
            x += speed;
            // moveRight = true;
        }
        if (direction == 3) {
            y += speed;
            // moveDown = true;
        }
    }

    // Prevents player from being drawn within a collision
    public void getOutOfWall() {

        if (direction == 0) {
            x += (int)speed;
            // moveLeft = false;
        }
        if (direction == 1) {
            y += (int)speed;
            // moveUp = false;
        }
        if (direction == 2) {
            x -= (int)speed;
            // moveRight = false;
        }
        if (direction == 3) {
            y -= (int)speed;
            // moveDown = false;
        }
    }

    // Custom toString() method used for debugging
    @Override 
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", " + y + ")";
    }
}