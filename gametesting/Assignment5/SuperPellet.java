// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class SuperPellet extends Sprite{
    private static BufferedImage superPelletImage = null;

    public SuperPellet() {
        this(0, 0, 20, 20);
    }

    public SuperPellet(int x, int y, int w, int h) {
        super(x,y,w,h);
        // ONLY LOADS ONE IMAGE NOW -- CHANGE
        if (superPelletImage == null) {
            superPelletImage = View.loadImage("superpellet.png");
        }
    }

    public void draw(Graphics g, int yFixed) {
        g.drawImage(superPelletImage, x, y + yFixed, 34, 34, null);
    }
    public boolean update() {
        return isValid;
    }

    @Override
    public boolean isSuperPellet() {
        return true;
    }

    // Deletes pellet sprite from model
    public void eatSuperPellet() {
        isValid = false;
    }

    // Custom toString() method used for debugging
    @Override 
    public String toString()
    {
        return "Super Pellet (x,y) = (" + x + ", " + y + ")";
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

    SuperPellet(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        if (superPelletImage == null) {
            superPelletImage = View.loadImage("superpellet.png");
        }
    }
}
            