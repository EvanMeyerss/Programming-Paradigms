/* Name: 					Evan Meyers
// Date: 					2/17/2024
// Assignment Description: 	Familarize myself with JSON files, more
							object oriented programming, and listeners 
*/

import java.util.ArrayList; // import the ArrayList class

public class Model
{
	private ArrayList<Wall> walls;
	private int topLeftX, topLeftY, width, height;
	private int posX, posY;

	public Model()
	{
		walls = new ArrayList<Wall>();
	}

	public void update()
	{
	}

	// Getters and setters
	public Wall getWall(int x) {
		return walls.get(x);
	}

	public int getWallSize() {
		return walls.size();
	}


	// Draw square walls on grid
	public void setWallGrid(int x, int y) {
		width = 50;
		height = 50;
		posX = (int)Math.floor(x / 50.0) * 50;
		posY = (int)Math.floor(y / 50.0) * 50;

		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).wallExists(posX, posY)) {
				return;
			}
		}
		Wall wall = new Wall(posX, posY, width, height);
		walls.add(wall);
	}

	// Remove square walls from grid
	public void removeWallGrid(int x, int y) {
		width = 50;
		height = 50;
		posX = (int)Math.floor(x / 50.0) * 50;
		posY = (int)Math.floor(y / 50.0) * 50;

		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).wallExists(posX, posY)) {
				walls.remove(walls.get(i));
			}
		}
	}

	// Clears screen of all walls
	public void clearWalls() {
		walls.clear();
	}

	/* 	Free wall draw method
	------------------------------
		public void setWallFree(int x1, int y1, int x2, int y2) {
		width = Math.abs(x2 - x1);
		height = Math.abs(y2 - y1);
		topLeftX = Math.min(x1, x2);
		topLeftY = Math.min(y1, y2);
		Wall wall = new Wall(topLeftX, topLeftY, width, height);
		walls.add(wall);
	}
	*/

	// Marshals this object into a JSON DOM
    Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpList = Json.newList();
        ob.add("walls", tmpList);
        for(int i = 0; i < walls.size(); i++)
            tmpList.add(walls.get(i).marshal());
        return ob;
    }

	// Unmarshaling constructor
    public void unmarshal(Json ob)
    {
		walls.clear();
        Json tmpList = ob.get("walls");
        for(int i = 0; i < tmpList.size(); i++) {
			Wall w = new Wall(tmpList.get(i));
			walls.add(w);
		}
	}
}