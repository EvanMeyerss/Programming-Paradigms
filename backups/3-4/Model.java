// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.util.ArrayList; // imports the ArrayList class
import java.awt.Toolkit;


public class Model
{
	private ArrayList<Wall> walls;
	private int width = 50, height = 50;
	private int posXClick, posYClick, posXDrag, posYDrag;
	private boolean wallFoundAtCursorPos;
	private Pacman player = new Pacman();
	private int direction = 3;
	// private int playerX = 350, playerY = 0;

	// Default constructor
	public Model()
	{
		walls = new ArrayList<Wall>();
	}

	// Update method
	public void update() {
		player.update();
	}

	// Getters and setters
	//----------------------------------------------------------------
	public Wall getWall(int x) {
		return walls.get(x);
	}

	public int getWallSize() {
		return walls.size();
	}
	//----------------------------------------------------------------

	// Draw square walls on grid
	public void setWallGridClick(int xClick, int yClick) {
		posXClick = (int)Math.floor(xClick / 50.0) * 50;
		posYClick = (int)Math.floor(yClick / 50.0) * 50;
		wallAtCursorPos(posXClick, posYClick);		
		
		if (wallFoundAtCursorPos) {
			for (int i = 0; i < walls.size(); i++) {
				if (walls.get(i).wallExists(posXClick, posYClick)) {
					walls.remove(i);
				}
			}
		}
		else {
			Wall wall = new Wall(posXClick, posYClick, width, height);
			walls.add(wall);
		}
	}

	// Draw square walls on grid
	public void setWallGridDrag(int xDrag, int yDrag) {
		posXDrag = (int)Math.floor(xDrag / 50.0) * 50;
		posYDrag = (int)Math.floor(yDrag / 50.0) * 50;

		// Checks if wall exists at initial position
		if(!wallFoundAtCursorPos) 
		{

			// Checks if wall exists at current position
			boolean addAnotherWall = true;
			for (int i = 0; i < walls.size(); i++) {
				if(walls.get(i).wallExists(posXDrag, posYDrag)) {
					addAnotherWall = false;
					break;
				}
			}
			
			// Creates walls if there was no wall at current position
			if(addAnotherWall) {
				Wall wall = new Wall(posXDrag, posYDrag, width, height);
				walls.add(wall);
			}
		}

		// Removes walls if there was a wall at initial position
		else {
			for (int i = 0; i < walls.size(); i++) {
				if(walls.get(i).wallExists(posXDrag, posYDrag)) {
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

	// Loops through walls ArrayList and checks if wall exists based on inputted grid coords
	public void wallAtCursorPos(int x, int y) {
		
		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).wallExists(x, y)) {
				wallFoundAtCursorPos = true;
				return;
			}
		}
		wallFoundAtCursorPos = false;
	}

	// Returns player position 
	public int getPlayerX()
	{
		return player.getX();
	}
	
	public int getPlayerY()
	{
		return player.getY();
	}

	public void setPlayerX(int playerX) {
		player.setX(playerX);
		// this.playerX = playerX;
	}

	public void setPlayerY(int playerY) {
		player.setY(playerY);
		// this.playerY = playerY;
	}

	// Following four methods allow for the use of arrow keys in Controller.java
	public void setPlayerXRight() 
	{
		player.setX(player.getX()+10);
		player.setDirection(2);
		direction = 3;
	}
	
	public void setPlayerXLeft()
	{
		player.setX(player.getX()-10);
		player.setDirection(0);
		direction = 1;
	}
	
	public void setPlayerYUp()
	{
		player.setY(player.getY()-10);
		player.setDirection(1);
		direction = 2;
	}
	
	public void setPlayerYDown()
	{
		player.setY(player.getY()+10);
		player.setDirection(3);
		direction = 4;
	}

	public int getDirection() {
		return direction;
	}  

	public Pacman getPlayerObject() {
		return player;
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

	// Unmarshaling method
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