// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.awt.image.BufferedImage;
import java.awt.Graphics;

    public class Pacman extends Sprite {
    private int prevX, prevY, direction = 3, yFixed = 0, frameIteration = 1;
    private static BufferedImage pacmanImages[] = null;
    private double speed = 10;
    private final int MAX_IMAGES_PER_DIRECTION = 4;

    // Default constructor
    public Pacman() {
        this(360, 350, 32, 40);
    }

    // Constructor
    public Pacman(int x, int y, int w, int h) {
        
        // Uses sprite constructor
        super(x,y,w,h);

        // Lazy loading
        if (pacmanImages == null) {
            // Initializes array of player images
            pacmanImages = new BufferedImage[MAX_IMAGES_PER_DIRECTION * 4 + 1];
            for (int i = 1; i < MAX_IMAGES_PER_DIRECTION * 4 + 1; i++) {
                pacmanImages[i] = View.loadImage("player" + i + ".png");
            }
        }
    }

    // Method calls for player to be drawn
    public void draw(Graphics g, int throwAway) {

        // if statements check if the map stops moving.
        // if it has, then the player starts to be drawn vertically
        if (y >= 650) { 
            yFixed = y - 300;
        }
        else if (y <= 50) {
            yFixed = y + 300;
        }
        else 
            yFixed = Game.WINDOW_HEIGHT/2 - 25;
        
        // Allows for the player to teleport horizontally
        // ----------------------------------------------------
        if (x > Game.MAP_WIDTH) {
            x = Game.MAP_WIDTH - 800;
        }
        else if (x < Game.MAP_WIDTH - 800) {
            x = Game.MAP_WIDTH;
        }
        // ----------------------------------------------------
        g.drawImage(pacmanImages[MAX_IMAGES_PER_DIRECTION * direction + frameIteration], x, yFixed, w, h, null);
    }

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
    }

    // Prevents player from being drawn within a collision
    public void getOutOfWall(Sprite sprite) {

        // Right
        if (x + w >= sprite.getX() && prevX + w <= sprite.getX()) {
            x = sprite.getX() - w;
        }
        // Left
        if (x <= sprite.getX() + sprite.getW() && prevX >= sprite.getX() + sprite.getW()) {
            x = sprite.getX() + sprite.getW();
        }
        
        if ((y + h >= sprite.getY() && y + h <= sprite.getY() + sprite.getH()) && prevY + h <= sprite.getY()) {
            y = sprite.getY() - h;
        }
        if ((y >= sprite.getY() && y <= sprite.getY() + sprite.getH()) && prevY >= sprite.getY() + sprite.getH()) {

            y = sprite.getY() + sprite.getH();
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
    //----------------------------------------------------------------

    // Custom toString() method used for debugging
    @Override 
    public String toString()
    {
        return "Pacman (x,y) = (" + x + ", " + y + ")";
    }

    public void eatPlayer() {
        isValid = false;
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

    // Unmarshaling constructor
    Pacman(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        if (pacmanImages == null) {
            // Initializes array of player images
            pacmanImages = new BufferedImage[MAX_IMAGES_PER_DIRECTION * 4 + 1];
            for (int i = 1; i < MAX_IMAGES_PER_DIRECTION * 4 + 1; i++) {
                pacmanImages[i] = View.loadImage("player" + i + ".png");
            }
        }
    }
}