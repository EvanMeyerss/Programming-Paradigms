// Name: 					Evan Meyers
// Date: 					2/17/2024
// Assignment Description: 	Familarize myself with JSON files, ArrayLists, and listeners

public class Wall {
    
    private int x, y;

    // Wall default constructor
    public Wall() {
        x = 0;
        y = 0;
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
    }
}