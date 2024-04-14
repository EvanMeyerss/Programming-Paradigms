/* Name: 					Evan Meyers
// Date: 					2/17/2024
// Assignment Description: 	Familarize myself with JSON files, more
							object oriented programming, and listeners 
*/

import java.util.ArrayList; // import the ArrayList class

public class Model
{
	private ArrayList<Wall> walls;
	private int width = 50, height = 50;
	private int posX1, posY1, posX2, posY2;
	private boolean wallFound;
	// private ArrayList<Integer> delWalls = new ArrayList<Integer>();

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
	public void setWallGridClick(int x, int y) {
		posX1 = (int)Math.floor(x / 50.0) * 50;
		posY1 = (int)Math.floor(y / 50.0) * 50;

		if (hasWall(posX1, posY1)) {
			for (int i = 0; i < walls.size(); i++) {
				if (walls.get(i).wallExists(posX1, posY1)) {
					walls.remove(i);
				}
			}
			return;
		}
	
		Wall wall = new Wall(posX1, posY1, width, height);
		walls.add(wall);
	}

	// Draw square walls on grid
	public void setWallGridDrag(int x1, int y1, int x2, int y2) {
		posX1 = (int)Math.floor(x1 / 50.0) * 50;
		posY1 = (int)Math.floor(y1 / 50.0) * 50;
		posX2 = (int)Math.floor(x2 / 50.0) * 50;
		posY2 = (int)Math.floor(y2 / 50.0) * 50;


		// if(hasWall(posX1, posY1)) {
		// 	for (int i = 0; i < walls.size(); i++) {
		// 		if(walls.get(i).wallExists(posX2, posY2)) {
		// 			walls.remove(i);
		// 		}
		// 	}
		// 	return;
		// }

		//if (!hasWall(posX2, posY2)) {
		if(!wallFound) //we are in add mode, but only want to add one per location
		{
			//check if the current mouse position is over a wall, and if so, don't add another one
			Wall wall = new Wall(posX2, posY2, width, height);
			walls.add(wall);
		}
		else //we are in remove mode
		{
			//check if there is a wall at the current mouse position and remove if so
			for (int i = 0; i < walls.size(); i++) {
				if(walls.get(i).wallExists(posX2, posY2)) {
					walls.remove(i);
					break;
				}
			}
		}

	}

	// Clears screen of all walls
	public void clearWalls() {
		walls.clear();
	}

	public boolean hasWall(int x, int y) {
		
		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).wallExists(posX1, posY1)) {
				wallFound = true;
				return true;
			}
		}
		wallFound = false;
		return false;
	}

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