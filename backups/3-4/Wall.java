// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Wall {
    
    private int x, y, w = 50, h = 50;
    private static BufferedImage wall_image = null;

    // Wall default constructor
    public Wall() {
        x = 0;
        y = 0;

        // if (wall_image == null) {
        //     // Image used for walls
		//     wall_image = View.loadImage("wall.png");
        // }

    }

    // Wall constructor
    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
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

    // Draws the wall
    // public void drawWall(Graphics g) {
    //     // Creates all wall graphics
	// 	for(int i = 0; i < model.getWallSize(); i++)
    //     {
    //         Wall wall = model.getWall(i);
    //         g.drawImage(wall_image, wall.getX(), wall.getY() - scrollPosYWall, 50, 50, null);
    //     }
    // }

    // Detect if a wall exists in given position
    public boolean wallExists(int x, int y) {
        if(this.x == x && this.y == y)
            return true;
        return false;
    }

    // Marshals this object into a JSON DOM
    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }

    // Unmarshaling constructor
    Wall(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        if (wall_image == null) {
            wall_image = View.loadImage("wall.png");
        }
    }

    public void drawYourself(Graphics g, int yFixed) {
        // Determines camera y-axis behavior	
		// int scrollPosYWall;
        // scrollPosYWall = playerPosY;
		// if (playerPosY <= -300) {
		// 	scrollPosYWall = -300;
		// }
		// else if (playerPosY >= 300) {
		// 	scrollPosYWall = 300;
		// }
        g.drawImage(wall_image, x, yFixed, w, h, null);
    }

    @Override 
    public String toString()
    {
        return "Wall (x,y) = (" + x + ", " + y + "),";
}
}