// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.util.ArrayList; // imports the ArrayList class
import java.util.Iterator;
// import java.awt.Toolkit;


public class Model
{
	public ArrayList<Sprite> sprites;
	private int posXClick, posYClick, posXDrag, posYDrag;
	private boolean wallFoundAtCursorPos;
	public Pacman player = new Pacman();
	private int wallWidth = 50, wallHeight = 50, playerWidth = player.getWidth(), playerHeight = player.getHeight();


	// Default constructor
	public Model()
	{
		sprites = new ArrayList<Sprite>();
	}

	// Update method
	public void update() {
		sprites.set(0,player);
		// update sprites, remove if necessary, THEN call collision detection/fixing
		Iterator<Sprite> iter1 = sprites.iterator();
		while(iter1.hasNext()) {
			Sprite sprite1 = iter1.next();
			if (!sprite1.update()) // if this returns false, remove from arraylist and don't attempt collision dection
			{
				iter1.remove();
				continue;
			}
			if (!sprite1.isMoving()){
				continue;
			}

			// now check for collisions
			// DO NOT REMOVE ANY SPRITES FROM WITHIN THIS ITERATOR!!!!
			Iterator<Sprite> iter2 = sprites.iterator();
			while (iter2.hasNext()) {
				Sprite sprite2 = iter2.next();
				if (sprite1 != sprite2 && sprite1.doesItCollide(sprite2)) {
					System.out.println("we're colliding");
					if(sprite1.isPacman()) {
						player.getOutOfWall(sprite2);
					}
										// if the player collides with a ghost
					if (sprite1.isPacman() && sprite2.isGhost()){
						((Ghost)sprite2).eatGhost();
					}
				}
			}
			sprite1.update();
		}
		
		// player.update();

		// //ArrayList Itrerator allows navegation through walls array 
		// Iterator<Sprite> iterate = sprites.iterator();
		// while (iterate.hasNext()) {
		// 	Sprite sprites = iterate.next();
		// 	if (this.isCollision(sprites)) {
		// 		player.getOutOfWall(sprites);
		// 	}
		// }

	}

	// Draw square walls on grid
	public void setWallGridClick(int xClick, int yClick) {
		posXClick = (int)Math.floor(xClick / wallWidth) * wallWidth;
		posYClick = (int)Math.floor(yClick / wallHeight) * wallHeight;
		wallAtCursorPos(posXClick, posYClick);		
		
		if (wallFoundAtCursorPos) {
			for (int i = 0; i < sprites.size(); i++) {
				if (sprites.get(i).spriteExists(posXClick, posYClick)) {
					sprites.remove(i);
				}
			}
		}
		else {
			Sprite wall = new Wall(posXClick, posYClick, wallWidth, wallHeight);
			sprites.add(wall);
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
			for (int i = 0; i < sprites.size(); i++) {
				if(sprites.get(i).spriteExists(posXDrag, posYDrag)) {
					addAnotherWall = false;
					break;
				}
			}
			
			// Creates walls if there was no wall at current position
			if(addAnotherWall) {
				Sprite sprite = new Wall(posXDrag, posYDrag, wallWidth, wallHeight);
				sprites.add(sprite);
			}
		}

		// Removes walls if there was a wall at initial position
		else {
			for (int i = 0; i < sprites.size(); i++) {
				if(sprites.get(i).spriteExists(posXDrag, posYDrag)) {
					sprites.remove(i);
					break;
				}
			}
		}

	}

	// Clears screen of all walls
	public void clearWalls() {
		sprites.clear();
	}

	// Loops through walls ArrayList and checks if wall exists based on inputted grid coords
	public void wallAtCursorPos(int x, int y) {
		
		for (int i = 0; i < sprites.size(); i++) {
			if (sprites.get(i).spriteExists(x, y)) {
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
	public boolean isCollision(Sprite sprite) {		
		if (this.getPlayerX() > sprite.getX() + wallWidth-1)
			// Collision not detected
			return false;
		if(this.getPlayerX() + playerWidth < sprite.getX()+1)
			return false;
		// wallHeight-11 allows for the player's hair to slightly pass through an above block
		if(this.getPlayerY() > sprite.getY() + wallHeight-1)
			return false;
		if(this.getPlayerY() + playerHeight < sprite.getY()+1)
			return false;
		// Collision detected
		return true;
	}

	// Getters and setters
	//----------------------------------------------------------------
	public Sprite getSprite(int x) {
		return sprites.get(x);
	}

	public int getSpritesSize() {
		return sprites.size();
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
        Json tmpListWalls = Json.newList();
        ob.add("walls", tmpListWalls);
		Json tmpListGhosts = Json.newList();
		ob.add("ghost", tmpListGhosts);
        for(int i = 0; i < sprites.size(); i++) {
			if(sprites.get(i).isWall()) {
				tmpListWalls.add(sprites.get(i).marshal());
			}
			if(sprites.get(i).isGhost()) {
				tmpListGhosts.add(sprites.get(i).marshal());
			}
		}
		return ob;
    }

	// Unmarshaling method
    public void unmarshal(Json ob)
    {
		sprites.clear();
		sprites.add(player);
        Json tmpListWalls = ob.get("walls");
        for(int i = 0; i < tmpListWalls.size(); i++) {
			Sprite w = new Wall(tmpListWalls.get(i));
			sprites.add(w);
		}

		// ADD LOGIC FOR LOADING GHOSTS FROM tmpListGhosts

		
	}

}