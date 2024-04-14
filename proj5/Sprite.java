// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public abstract class Sprite {
    protected int x, y, w, h; 
    protected boolean isValid = true;
    protected static BufferedImage image = null;

    public abstract boolean update();
    public abstract void draw(Graphics g, int yFixed);
    public abstract Json marshal();
    
    // Default constructor
    public Sprite() {    }
    public Sprite(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    // Checks for collision between two sprites
    public boolean doesItCollide(Sprite otherSprite) {
        // current sprite's right is less than the other's left
        if (x + w < otherSprite.getX() + 1) {
            return false;
        }
        // current sprite's left is greater than the other's right
        if (x > otherSprite.getX() + otherSprite.getW() - 1) {
            return false;
        }
        // current sprite's toes are greater tahn the other's head
        if (y + h < otherSprite.getY() + 1) {
            return false;
        } 
        // current sprite's head is greater than the other's toes
        if (y > otherSprite.getY() + otherSprite.getH() - 1) {
            return false;
        } 
        return true;
    }

    // Detect if a wall exists in given position
    public boolean spriteExists(int x, int y) {
        if(this.x == x && this.y == y)
            return true;
        return false;
    }

    // States if sprite object moves
    public boolean isMoving() {
        return false;   
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public boolean isWall() {
        return false;
    }

    public boolean isPacman() {
        return false;
    }

    public boolean isGhost() {
        return false;
    }

    public boolean isPellet() {
        return false;
    }

    public boolean isFruit() {
        return false;
    }
}
