/* Name: 					Evan Meyers
// Date: 					2/17/2024
// Assignment Description: 	Familarize myself with JSON files, more
							object oriented programming, and listeners 
*/

public class Wall {
    
    private int w, h;
    private int x, y;

    public Wall() {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
    }

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    //Getters and setters
    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    // Dectect if a wall exists in given position
    public boolean wallExists(double x, double y) {
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
        // Don't need these bc 8-bit remove later
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    // Unmarshaling constructor
    Wall(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        // Don't need these bc 8-bit remove later
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");

    }
}
