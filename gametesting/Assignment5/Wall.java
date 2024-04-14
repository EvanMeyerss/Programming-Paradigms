// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Wall extends Sprite {
    
    private static BufferedImage wallImage = null;

    // Default constructor
    public Wall() {
        this(0, 0, 50,50);
    }

    // Constructor
    public Wall(int x, int y, int w, int h) {
        super(x,y,w,h);
        if (image == null) {
            wallImage = View.loadImage("wall.png");
        }
    }

    public boolean update() {
        return isValid;
    }
    
    // Getters and setters
    //----------------------------------------------------------------
    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    //----------------------------------------------------------------

    @Override
    public boolean isWall() {
        return true;
    }

    // Called in View.java - Draws the wall to the screen
    public void draw(Graphics g, int yFixed) {
        g.drawImage(wallImage, x, y + yFixed, w, h, null);
    }

    // Marshals this object into a JSON DOM
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    // Unmarshaling constructor
    Wall(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        if (wallImage == null) {
            wallImage = View.loadImage("wall.png");
        }
    }

    // Custom toString() method used for debugging
    @Override 
    public String toString()
    {
        return "Wall (x,y) = (" + x + ", " + y + "),";
}
}