// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.sampled.*;


public class Model
{
	private ArrayList<Sprite> sprites;
	private ArrayList<Sprite> queuedSprites;
	private int posXClick, posYClick, posXDrag, posYDrag;
	private boolean spriteFoundAtCursorPos;
	private Pacman player = new Pacman();
	private int score = 0;
	private Clip pointSFX;

	// Grid & Sprite Dimensions
	//--------------------------------------------
	private final int gridWidth = 50, gridHeight = 50;
	private final int wallWidth = 50, wallHeight = 50;
	private final int pelletWidth = 10, pelletHeight = 10;
	private final int ghostWidth = 40, ghostHeight = 40;
	private final int fruitWidth = 40, fruitHeight = 40;
	//---------------------------------------------


	// Default constructor
	public Model() {
		sprites = new ArrayList<Sprite>();
		queuedSprites = new ArrayList<Sprite>();
	}

	// Update method
	public void update() {
		// Updates player element within the element ArrayList
		sprites.set(0, player);
		// Adds queued sprites to avoid modification errors
		sprites.addAll(queuedSprites);
		queuedSprites.clear();

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

			// Second iteration through list to compare collisions in sprites
			Iterator<Sprite> iter2 = sprites.iterator();
			while (iter2.hasNext()) {
				Sprite sprite2 = iter2.next();
				if (sprite1 != sprite2 && sprite1.doesItCollide(sprite2)) {
					if(sprite1.isFruit() && sprite2.isWall()) { 			// Fruit vs. Wall
						((Fruit)sprite1).getOutOfWall((Wall)sprite2);
					}
					else if(sprite1.isGhost() && sprite2.isWall()) {		// Ghost vs. Wall
						((Ghost)sprite1).getOutOfWall((Wall)sprite2);
					}
					else if(sprite1.isPacman() && sprite2.isWall()) {		// Pacman vs. Wall
						player.getOutOfWall(sprite2);
					}
					// if the player collides with a ghost
					else if (sprite1.isPacman() && sprite2.isGhost()){		// Pacman vs. Ghost
						if(((Ghost)sprite2).scorable())	{					// Only adds score on initial collision
							playSound();
							score += 10;
						}
						((Ghost)sprite2).eatGhost();
					}
					else if (sprite1.isPacman() && sprite2.isPellet()) {	// Pacman vs. Pellet
						((Pellet)sprite2).eatPellet();
						score += 5;
					}
					else if (sprite1.isPacman() && sprite2.isFruit()) {		// Pacman vs. Fruit
						((Fruit)sprite2).eatFruit();
						score += 25;
					}
				}
			}
		}
	}

	// Add/remove walls grid
	public void setWallGridClick(int xClick, int yClick) {
		posXClick = (int)Math.floor(xClick / (float)gridWidth) * gridWidth;
		posYClick = (int)Math.floor(yClick / (float)gridHeight) * gridHeight;
		spriteAtCursorPos(posXClick, posYClick);		
		
		if (spriteFoundAtCursorPos) {
			for (int i = 0; i < sprites.size(); i++) {
				if (sprites.get(i).spriteExists(posXClick, posYClick)) {
					sprites.remove(i);
				}
			}
		}
		else {
			Sprite sprite = new Wall(posXClick, posYClick, wallWidth, wallHeight);
			queuedSprites.add(sprite);
		}
	}

	// Add/remove walls grid
	public void setWallGridDrag(int xDrag, int yDrag) {
		posXDrag = (int)Math.floor(xDrag / (float)gridWidth) * gridWidth;
		posYDrag = (int)Math.floor(yDrag / (float)gridHeight) * gridHeight;

		// Checks if wall exists at initial position
		if(!spriteFoundAtCursorPos) 
		{

			// Checks if wall exists at current position
			boolean addAnotherWall = true;
			for (int i = 0; i < sprites.size(); i++) {
				if(sprites.get(i).spriteExists(posXDrag, posYDrag)) {
					addAnotherWall = false;
					break;
				}
			}
			// Checks secondary ArrayList to prevent wall sprites from adding on top of each other
			for(int i = 0; i < queuedSprites.size(); i++) {
				if(!queuedSprites.isEmpty())
					if(queuedSprites.get(i).spriteExists(posXDrag, posYDrag)){
						addAnotherWall = false;
						break;	
					}
			}
			
			// Creates walls if there was no wall at current position
			if(addAnotherWall) {
				Sprite sprite = new Wall(posXDrag, posYDrag, wallWidth, wallHeight);
				queuedSprites.add(sprite);
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

	// Add ghosts to grid
	public void setGhostGridClick(int xClick, int yClick) {
		posXClick = (int)Math.floor(xClick / (float)gridWidth) * gridWidth;
		posYClick = (int)Math.floor(yClick / (float)gridHeight) * gridHeight;
		spriteAtCursorPos(posXClick, posYClick);		
		
		if (!spriteFoundAtCursorPos) {
			// The "+5" in the coords centers the ghost
			Sprite sprite = new Ghost(posXClick + 5, posYClick + 5, ghostWidth, ghostHeight);
			queuedSprites.add(sprite);
		}
	}

	// Add pellets to grid
	public void setPelletGridClick(int xClick, int yClick) {
		posXClick = (int)Math.floor(xClick / (float)gridWidth) * gridWidth;
		posYClick = (int)Math.floor(yClick / (float)gridHeight) * gridHeight;
		spriteAtCursorPos(posXClick, posYClick);		
		
		if (!spriteFoundAtCursorPos) {
			// The "+20" in the coords centers the pellet
			Sprite sprite = new Pellet(posXClick + 20, posYClick + 20, pelletWidth, pelletHeight);
			queuedSprites.add(sprite);
		}
	}

	// Add fruit to grid
	public void setFruitGridClick(int xClick, int yClick) {
		posXClick = (int)Math.floor(xClick / (float)gridWidth) * gridWidth;
		posYClick = (int)Math.floor(yClick / (float)gridHeight) * gridHeight;
		spriteAtCursorPos(posXClick, posYClick);		
		
		if (!spriteFoundAtCursorPos) {
			// The "+5" in the coords centers the fruit
			Sprite sprite = new Fruit(posXClick + 5, posYClick + 5, fruitWidth, fruitHeight);
			queuedSprites.add(sprite);
		}
	}

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------


	// Clears screen of all walls
	public void clearSprites() {
		sprites.clear();
		sprites.add(player);
	}

	// Loops through walls ArrayList and checks if wall exists based on inputted grid coords
	public void spriteAtCursorPos(int x, int y) {
		
		for (int i = 0; i < sprites.size(); i++) {
			if (sprites.get(i).spriteExists(x, y)) {
				spriteFoundAtCursorPos = true;
				return;
			}
		}
		spriteFoundAtCursorPos = false;
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

	public void playSound() {
		try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("images/point.wav"));
			pointSFX = AudioSystem.getClip();
			pointSFX.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pointSFX != null) {
			FloatControl gainControl = (FloatControl) pointSFX.getControl(FloatControl.Type.MASTER_GAIN);
			// Decrease file volume by 25 decibles
			gainControl.setValue(-25.0f);
            // Start playing the sound clip
            pointSFX.setFramePosition(0); // rewind to the beginning
			pointSFX.start();
        }
    }

	// Getters and setters
	//----------------------------------------------------------------
	public int getScore() {
		return score;
	}

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

	public void setPlayerY(int playerY) {
		player.setY(playerY);
	}

	public void setPlayerPrevX(int playerPrevX) {
		player.setPrevX(playerPrevX);
	}

	public void setPlayerPrevY(int playerPrevY) {
		player.setPrevY(playerPrevY);
	}
	//----------------------------------------------------------------

	// Marshals this object into a JSON DOM
    Json marshal()
    {
        Json ob = Json.newObject();
		// Json tmpListPacman = Json.newList();
		// ob.add("pacman", tmpListPacman);
        Json tmpListWalls = Json.newList();
        ob.add("walls", tmpListWalls);
		Json tmpListGhosts = Json.newList();
		ob.add("ghosts", tmpListGhosts);
		Json tmpListFruits = Json.newList();
		ob.add("fruits", tmpListFruits);
		Json tmpListPellets = Json.newList();
		ob.add("pellets", tmpListPellets);
        for(int i = 0; i < sprites.size(); i++) {
			// if(sprites.get(i).isPacman()) {
			// 	tmpListPacman.add(sprites.get(i).marshal());
			// }	
			if(sprites.get(i).isWall()) {
				tmpListWalls.add(sprites.get(i).marshal());
			}
			if(sprites.get(i).isGhost()) {
				tmpListGhosts.add(sprites.get(i).marshal());
			}
			if(sprites.get(i).isFruit()) {
				tmpListFruits.add(sprites.get(i).marshal());
			}
			if(sprites.get(i).isPellet()) {
				tmpListPellets.add(sprites.get(i).marshal());
			}		
		}
		return ob;
    }

	// Unmarshaling method
    public void unmarshal(Json ob)
    {
		// Clears sprite list, adds player first, then unmarshals rest of sprites
		sprites.clear();
		sprites.add(player);
		Json tmpListPellets = ob.get("pellets");
		for(int i = 0; i < tmpListPellets.size(); i++) {
			Sprite p = new Pellet(tmpListPellets.get(i));
			sprites.add(p);
		}
		Json tmpListWalls = ob.get("walls");
        for(int i = 0; i < tmpListWalls.size(); i++) {
			Sprite w = new Wall(tmpListWalls.get(i));
			sprites.add(w);
		}
		Json tmpListGhosts = ob.get("ghosts");
		for(int i = 0; i < tmpListGhosts.size(); i++) {
			Sprite g = new Ghost(tmpListGhosts.get(i));
			sprites.add(g);
		}
		Json tmpListFruits = ob.get("fruits");
		for(int i = 0; i < tmpListFruits.size(); i++) {
			Sprite f = new Fruit(tmpListFruits.get(i));
			sprites.add(f);
		}
	}
}