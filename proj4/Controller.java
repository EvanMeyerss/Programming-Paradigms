// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements ActionListener, MouseListener, KeyListener, MouseMotionListener
{
	private View view;
	private Model model;
	private boolean keyE, keyUp, keyDown, keyRight, keyLeft;
	private int xClick, yClick, xDrag, yDrag;
	
	// Controller constructor
	public Controller(Model m)
	{
		model = m;

		// Loads previous map save
		Json mapload = Json.load("map.json");
		model.unmarshal(mapload);
		System.out.println("Map loaded!");
	}
	
	// Allows acess to view class
	void setView(View v)
	{
		view = v;
	}
	
	// Assings initial x,y coords to be used in either grid or free walls
	public void mousePressed(MouseEvent e)
	{
		// Checks if in edit mode
		if(keyE) {
			xClick = e.getX();
			// Fixes the y-coordinate to reflect the scroll y-position
			yClick = e.getY() - view.getScrollPos();
			model.setWallGridClick(xClick, yClick);		
		}
	}
	
	// Draws grid walls when mouse is clicked and dragged
	public void mouseDragged(MouseEvent e) {
		if(keyE) {
			xDrag = e.getX();
			// Fixes the y-coordinate to reflect the scroll y-position
			yDrag = e.getY() - view.getScrollPos();
			model.setWallGridDrag(xDrag, yDrag);
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode()) {

			// Starts player movement
			//case KeyEvent.VK_RIGHT: keyRight = true; keyLeft = false; keyUp = false; keyDown = false; break;
			//case KeyEvent.VK_LEFT: keyLeft = true; keyRight = false; keyUp = false; keyDown = false; break;
			//case KeyEvent.VK_UP: keyUp = true; keyLeft = false; keyRight = false; keyDown = false; break;
			//case KeyEvent.VK_DOWN: keyDown = true; keyLeft = false; keyUp = false; keyRight = false; break;
			case KeyEvent.VK_RIGHT: keyRight = true;  break;
			case KeyEvent.VK_LEFT: keyLeft = true;  break;
			case KeyEvent.VK_UP: keyUp = true;  break;
			case KeyEvent.VK_DOWN: keyDown = true;  break;
	
			case KeyEvent.VK_SHIFT: model.sprintSpeed(); break;

			// Removes all walls
			case KeyEvent.VK_C:
				model.clearWalls();
				break;
		}
	}

	public void keyReleased(KeyEvent e)			{
		switch (e.getKeyCode()) {

			// Stops player movement
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SHIFT: model.normalSpeed(); break;

			// Both key events will exit the program
			case KeyEvent.VK_ESCAPE: System.exit(0);
			case KeyEvent.VK_Q: System.exit(0);

			// Toggles editing mode
			case KeyEvent.VK_E: 
				if (keyE == true) {
					view.setMode("View");
					keyE = false; 
					break; 
				}
				view.setMode("Edit");
				keyE = true;
				break;

			// Saves the map to map.json
			case KeyEvent.VK_S:
				Json mapsave = model.marshal();
				mapsave.save("map.json");
				System.out.println("Map saved!");
				break;

			// Loads previously saved map from map.json
			case KeyEvent.VK_L:
				Json mapload = Json.load("map.json");
				model.unmarshal(mapload);
				System.out.println("Map loaded!");
				break;
		}
	}

	public void update() {

		model.setPlayerPrevX(model.getPlayerX());
		model.setPlayerPrevY(model.getPlayerY());

		// Changes player position
		if(keyLeft) 
			model.changePlayerDirection(0);
		else if(keyUp)
			model.changePlayerDirection(1);
		else if(keyRight) 
			model.changePlayerDirection(2);
		else if(keyDown)
			model.changePlayerDirection(3);
	}

	// Misc required methods for implementations
	public void mouseReleased(MouseEvent e) 	{	}
	public void actionPerformed(ActionEvent e)	{	}
	public void mouseMoved(MouseEvent e) 		{	}
	public void mouseEntered(MouseEvent e) 		{	}
	public void mouseExited(MouseEvent e)  		{	}
	public void mouseClicked(MouseEvent e) 		{   }
	public void keyTyped(KeyEvent e)			{	}
}