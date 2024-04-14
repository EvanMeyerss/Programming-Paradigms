// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.util.ArrayList; // imports the ArrayList class
import java.util.Iterator;
// import java.awt.Toolkit;


public class Model
{
	private ArrayList<Wall> walls;
	private int posXClick, posYClick, posXDrag, posYDrag;
	private boolean wallFoundAtCursorPos;
	private Pacman player = new Pacman();
	private int wallWidth = 50, wallHeight = 50, playerWidth = player.getWidth(), playerHeight = player.getHeight();


	// Default constructor
	public Model()
	{
		walls = new ArrayList<Wall>();
	}

	// Update method
	public void update() {
		player.update();

		//ArrayList Itrerator allows navegation through walls array 
		Iterator<Wall> iterate = walls.iterator();
		while (iterate.hasNext()) {
			Wall wall = iterate.next();
			if (this.isCollision(wall)) {
				player.getOutOfWall();
			}
		}

	}

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
			Wall wall = new Wall(posXClick, posYClick, wallWidth, wallHeight);
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
				Wall wall = new Wall(posXDrag, posYDrag, wallWidth, wallHeight);
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

	// Lets player sprint
	public void sprintSpeed() {
		player.sprintSpeed();
	}

	// Sets player speed back to normal
	public void normalSpeed() {
		player.normalSpeed();
	}

	// Following four methods allow for the use of arrow keys in Controller.java
	public void changePlayerDirection(int dir) {
		player.movePlayer(dir);
		player.iterateFrame();
	}

	// Collision detector method
	public boolean isCollision(Wall wall) {		
		if (this.getPlayerX() > wall.getX() + wallWidth-1)
			// Collision not detected
			return false;
		if(this.getPlayerX() + playerWidth-1 < wall.getX())
			return false;
		// wallHeight-11 allows for the player's hair to slightly pass through an above block
		if(this.getPlayerY() > wall.getY() + wallHeight-11)
			return false;
		if(this.getPlayerY() + playerHeight-1 < wall.getY())
			return false;
		// Collision detected
		return true;
	}

	// Getters and setters
	//----------------------------------------------------------------
	public Wall getWall(int x) {
		return walls.get(x);
	}

	public int getWallSize() {
		return walls.size();
	}
	
	public int getPlayerX()
	{
		return player.getX();
	}
	
	public int getPlayerY()
	{
		return player.getY();
	}

	public int getPlayerYFixed() {
		return player.getYFixed();
	}

	public void setPlayerX(int playerX) {
		player.setX(playerX);
	}

	public void setPlayerY(int playerY) {
		player.setY(playerY);
	}

	public void setPlayerPrevX(int playerPrevX) {
		player.setPrevX(playerPrevX);
	}

	public void setPlayerPrevY(int playerPrevY) {
		player.setPrevY(playerPrevY);
	}

	public void setPlayerYFixed(int yFixed) {
		player.setYFixed(yFixed);
	}

	public Pacman getPlayerObject() {
		return player;
	}
	//----------------------------------------------------------------

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