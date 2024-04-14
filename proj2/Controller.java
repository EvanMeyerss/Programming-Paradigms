// Name: 					Evan Meyers
// Date: 					2/7/2024
// Assignment Description: 	To familiarize myself with the relations of different 
// 							classes and how to create and animate graphics on the screen.

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements ActionListener, MouseListener, KeyListener
{
	private View view;
	private Model model;
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	private boolean keyDown;
	
	public Controller(Model m)
	{
		model = m;
	}

	public void actionPerformed(ActionEvent e)
	{
	// Removes button from screen
	view.removeButton();
	}
	
	void setView(View v)
	{
		view = v;
	}
	
	// Changes turtle destination to where the cursor is clicked
	public void mousePressed(MouseEvent e)
	{
		model.setDestination(e.getX(), e.getY());
	}
	
	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
	
	// Establishes logic for turtle navegation throught arrow keys
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
	}

	// Allows use of the arrow keys to change turtle position
	public void update()
	{
		if(keyRight) 
			model.setDestXRight();
		if(keyLeft) 
			model.setDestXLeft();
		if(keyDown)
			model.setDestYDown();
		if(keyUp)
			model.setDestYUp();
	}

}
