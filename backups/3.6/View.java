// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
// import java.nio.Buffer;
import java.awt.Color;
import java.awt.Font;
// import java.io.IOException;

public class View extends JPanel
{
	private Model model;
	private int scrollPosYView = 0, viewYFixed = 0;
	private String mode = "View";
	


	// Constructor
	public View(Controller c, Model m)
	{
		model = m;
		c.setView(this);		
	}
	
	// Draws background color, wall graphics, and text
	public void paintComponent(Graphics g)
	{
		// Moves the map screen while not at one of the vertical wall borders
		// --------------------------------------------------------------------
		Pacman player = model.getPlayerObject();
		scrollPosYView = (350 - player.getY());
		if (scrollPosYView < -300) {
			scrollPosYView = -300;
		}
			
		if (scrollPosYView > 300) {
			scrollPosYView = 300;
		}
		// --------------------------------------------------------------------
					
		// Allows for the player to teleport horizontally
		// ----------------------------------------------------
		if (player.getX() >= Game.MAP_WIDTH) {
			player.setX(Game.MAP_WIDTH - 800);
		}
		else if (player.getX() <= Game.MAP_WIDTH - 800) {
			player.setX(Game.MAP_WIDTH);
		}
		// ----------------------------------------------------

		// Changes background color based on current mode
		if (mode.equals("View")) {
			g.setColor(new Color(182, 201, 255));
		}
		else {
			g.setColor(new Color(204, 181, 158));
		}

		// Creates background
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Creates all wall graphics
		for(int i = 0; i < model.getWallSize(); i++)
			{
				Wall wall = model.getWall(i);
				wall.drawYourself(g, scrollPosYView);
			}
		
		// Draws Pacman player
		// ----------------------------------------------------------
		// if statements check if the map stops moving.
		// if it has, then the player starts to be drawn vertically
		int localYFixed;
		if (player.getY() >= 650) { 
            localYFixed = player.getY() - 300;
			viewYFixed = localYFixed;
		}
        else if (player.getY() <= 50) {
            localYFixed = player.getY() + 300;
			viewYFixed = localYFixed;
        }
        else 
        	localYFixed = 350;
		player.setYFixed(localYFixed);
		player.drawYourself(g);

		// System.out.println("Click position: " + (this.getScrollPos()) + " --- scrollPosYView: " + scrollPosYView + " --- viewYFixed: " + viewYFixed);
		
		// Text that indicates current mode
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Current mode: " + mode, 10, 730);

	}

	// Image loading method
	static BufferedImage loadImage(String filename) {
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File("images/" + filename));
		}
		catch(Exception e)
		{
			System.out.println("Could not load " + filename);
			e.printStackTrace(System.err);
			System.exit(1);
		}
		System.out.println(filename + " loaded!");
		return image;
	}

	// Getters and setters
	//----------------------------------------------------------------
	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getScrollPos() {
		return scrollPosYView;
	}
	//----------------------------------------------------------------

}