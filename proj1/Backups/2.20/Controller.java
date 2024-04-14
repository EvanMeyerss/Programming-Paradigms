/* Name: 					Evan Meyers
// Date: 					2/17/2024
// Assignment Description: 	Familarize myself with JSON files, more
							object oriented programming, and listeners 
*/

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
	private boolean keyE, keyA = true, keyUp, keyDown;
	private int x1, y1, x2, y2, x, y;
	
	public Controller(Model m)
	{
		model = m;
	}
	
	void setView(View v)
	{
		view = v;
	}
	

	// Will assign initial x and y coords to be used in either grid or free walls
	public void mousePressed(MouseEvent e)
	{
		if(keyE) {
			x1 = e.getX();
			y1 = e.getY();
			if(keyA) {
				model.setWallGrid(x1, y1);
			}
			else {
				model.removeWallGrid(x1, y1);
			}
				
		}
	}
	
	// Draws either free wall on release of mouse
	public void mouseReleased(MouseEvent e) 
	{
		if(keyE) {
			x2 = e.getX();
			y2 = e.getY();
			// model.setWallFree(x1, y1, x2, y2);	
		}
	}

	// Draws grid walls when mouse is clicked and dragged
	public void mouseDragged(MouseEvent e) {
		if(keyE) {
			x = e.getX();
			y = e.getY();
			if(keyA) {
				model.setWallGrid(x, y);
			}
			else {
				model.removeWallGrid(x, y);
			}		}
	}
	
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			// Both key events will exit the program
			case KeyEvent.VK_ESCAPE: System.exit(0);
			case KeyEvent.VK_Q: System.exit(0);

			// Change camera position
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			
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

			// Toggles add/remove modes
			case KeyEvent.VK_A: 
				if (keyA == true) {
					view.setAddRemove("Removing");
					keyA = false;
					break; 
				}
				view.setAddRemove("Adding");
				keyA = true;
				break;

			// Removes all walls
			case KeyEvent.VK_C:
				model.clearWalls();
				break;
			
			// Saves the map to map.json
			case KeyEvent.VK_S:
				Json mapsave = model.marshal();
				mapsave.save("map.json");
				System.out.println("Map saved!");
				break;

			// Loads previously saved map from map.json
			case KeyEvent.VK_L:
				System.out.println("Map loaded!");
				Json mapload = Json.load("map.json");
				model.unmarshal(mapload);
				break;
		}
	}

	public void keyReleased(KeyEvent e)			{
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
		}
		}

	

	public void update()
	{
		// Changes camera position
		if(keyUp) {
			view.incScrollPos();
		}
		if(keyDown) {
			view.decScrollPos();
		}
	}

	// Misc required methods for implementations
	public void actionPerformed(ActionEvent e)	{	}
	public void mouseMoved(MouseEvent e) 		{	}
	public void mouseEntered(MouseEvent e) 		{	}
	public void mouseExited(MouseEvent e)  		{	}
	public void mouseClicked(MouseEvent e) 		{   }
	public void keyTyped(KeyEvent e)			{	}
}
