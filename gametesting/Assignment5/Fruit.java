// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.Random;

public class Fruit extends Sprite{
    private static BufferedImage fruitImages[] = null;
    private int prevX, prevY, bounceDirection = 0, fruitType;
    private final int numFruit = 7;
    private double speed = 5;
    private Random random = new Random();

    public Fruit() {
        this(0,0,40,40);
    }

    public Fruit(int x, int y, int w, int h) {
        super(x,y,w,h);
        bounceDirection = 2;
        // Chooses random fruit image
        fruitType = random.nextInt(7);

        // Lazy loads fruit image array
        if (fruitImages == null) {
            fruitImages = new BufferedImage[numFruit];
            for(int i = 0; i < numFruit; i++) {
                fruitImages[i] = View.loadImage("fruit" + i + ".png");
            }
        }
    }

    public void draw(Graphics g, int yFixed) {
        
        // Allows for the player to teleport horizontally
        // ----------------------------------------------------
        if (x > Game.MAP_WIDTH) {
            x = Game.MAP_WIDTH - 800;
        }
        else if (x < Game.MAP_WIDTH - 800) {
            x = Game.MAP_WIDTH;
        }
        
        // Draws fruit sprite
        g.drawImage(fruitImages[fruitType], x, y + yFixed, w, h, null);
    }
    
    public boolean update() {
        
        // Moves fruit
        move();

        return isValid;
    }

    
    // Changes fruit direction and gets out of walls
    public void move() { 
        
        // Previous coords used for collision fixing 
        prevX = x;
        prevY = y;

        // Moves fruit depending on bounceDirection
        if(bounceDirection == 0) 
            x+=speed;
        else if(bounceDirection == 1)
            y-=speed;
        else if(bounceDirection == 2)
            x-=speed;
        else if(bounceDirection == 3)
            y+=speed;

    }

    // Prevents fruit from being drawn within a collision
    public void getOutOfWall(Wall wall) {

        // From left
        if (x + w >= wall.getX() && prevX + w <= wall.getX()) {
            x = wall.getX() - w;
        }
        // From right
        if (x <= wall.getX() + wall.getW() && prevX >= wall.getX() + wall.getW()) {
            x = wall.getX() + wall.getW();
        }
        // From top
        if ((y + h >= wall.getY() && y + h <= wall.getY() + wall.getH()) && prevY + h <= wall.getY()) {
            y = wall.getY() - h;
        }
        // From bottom
        if ((y >= wall.getY() && y <= wall.getY() + wall.getH()) && prevY >= wall.getY() + wall.getH()) {

            y = wall.getY() + wall.getH();
        }
        // Randomly change direction
        int tempDirection = bounceDirection;
        while (tempDirection == bounceDirection) {
            bounceDirection = random.nextInt(4);
        }
    }

    // Misc methods
    //-----------------------------------   
    @Override
    public boolean isFruit() {
        return true;
    }

    @Override
    public boolean isMoving() {
        return true;
    }

    // Removes fruit sprite from model
    public void eatFruit() {
        isValid = false;
    }
    
    // Custom toString() method used for debugging
    @Override 
    public String toString()
    {
        return "Fruit (x,y) = (" + x + ", " + y + ")";
    }

    // Marshals this object into a JSON DOM
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("fruitType", fruitType);
        return ob;
    }

    // Unmarshaling constructor
    Fruit(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        fruitType = (int)ob.getLong("fruitType");
        if (fruitImages == null) {
            fruitImages = new BufferedImage[numFruit];
            for(int i = 0; i < numFruit; i++) {
                fruitImages[i] = View.loadImage("fruit" + i + ".png");
            }
        }
    }
}
            