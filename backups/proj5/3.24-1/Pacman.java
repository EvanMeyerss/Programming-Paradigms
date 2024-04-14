// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Pacman extends Sprite {
    private int prevX, prevY, direction, yFixed = 0, frameIteration = 1;
    private static BufferedImage pacmanImages[] = null;
    private double speed;
    private final int MAX_IMAGES_PER_DIRECTION = 4;

    // Default constructor
    public Pacman() {
        this(360, 350, 32, 40, 10, 3);
    }

    // Constructor
    public Pacman(int x, int y, int w, int h, int speed, int direction) {
        super(x,y,w,h);
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


    public boolean update() { 
        return isValid;
    }

    // States moving object
    @Override
    public boolean isMoving() {
        return true;
    }

    @Override
    public boolean isPacman() {
        return true;
    }
    // Iterates the current frame to animate character
    public void iterateFrame(){
        frameIteration++;
        // Sets current frame to 1 if it exceeds 4
        if (frameIteration > MAX_IMAGES_PER_DIRECTION) {
            frameIteration = 1;
        }
    }   

    // Method calls for player to be drawn
    public void draw(Graphics g, int throwAway) {
        // Allows for the player to teleport horizontally
		// ----------------------------------------------------
		if (x > Game.MAP_WIDTH) {
			x = Game.MAP_WIDTH - 800;
		}
		else if (x < Game.MAP_WIDTH - 800) {
			x = Game.MAP_WIDTH;
		}
        System.out.println(x);
		// ----------------------------------------------------
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
    public void getOutOfWall(Sprite sprite) {

        // Right
        if (x + w >= sprite.getX() && prevX + w <= sprite.getX()) {
            x = sprite.getX() - w;
        }
        // Left
        if (x <= sprite.getX() + sprite.getWidth() && prevX >= sprite.getX() + sprite.getWidth()) {
            x = sprite.getX() + sprite.getWidth();
        }
        
        if ((y + h >= sprite.getY() && y + h <= sprite.getY() + sprite.getHeight()) && prevY + h <= sprite.getY()) {
            y = sprite.getY() - h;
        }
        if ((y >= sprite.getY() && y <= sprite.getY() + sprite.getHeight()) && prevY >= sprite.getY() + sprite.getHeight()) {

            y = sprite.getY() + sprite.getHeight();
        }
    }

    // Marshal method
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }


    
    // ADD UNMARSHAL




    // Custom toString() method used for debugging
    @Override 
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", " + y + ")";
    }
}