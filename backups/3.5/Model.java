// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.util.ArrayList; // imports the ArrayList class
// import java.awt.Toolkit;


public class Model
{
	private ArrayList<Wall> walls;
	private int width = 50, height = 50;
	private int posXClick, posYClick, posXDrag, posYDrag;
	private boolean wallFoundAtCursorPos;
	private Pacman player = new Pacman();

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

	public int getPlayerYFixed() {
		return player.getYFixed();
	}

	public void setPlayerX(int playerX) {
		player.setX(playerX);
	}

	public void setPlayerY(int playerY) {
		player.setY(playerY);
	}

	// Following four methods allow for the use of arrow keys in Controller.java
	public void setPlayerXRight() 
	{
		player.setX(player.getX()+10);
		player.setDirection(2);
	}
	
	public void setPlayerXLeft()
	{
		player.setX(player.getX()-10);
		player.setDirection(0);
	}
	
	public void setPlayerYUp()
	{
		player.setY(player.getY()-10);
		player.setDirection(1);
	}
	
	public void setPlayerYDown()
	{
		player.setY(player.getY()+10);
		player.setDirection(3);
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

	// Collision detector method
	public boolean isCollision() {		
		for(int i = 0; i < this.getWallSize(); i++) {
			Wall wall = this.getWall(i);
			
			if (this.getPlayerX() > wall.getX() + 50)
				// Collision not detected
				return false;
			if(this.getPlayerX() + 50 < wall.getX())
				return false;
			if(this.getPlayerYFixed() > wall.getY() + 50)
				return false;
			if(this.getPlayerYFixed() + 50 < wall.getY())
				return false;
		}
		// Collision detected
		return true;
	}
}