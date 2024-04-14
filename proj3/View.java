// Name: 					Evan Meyers
// Date: 					2/17/2024
// Assignment Description: 	Familarize myself with JSON files, ArrayLists, and listeners

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
// import java.io.IOException;

public class View extends JPanel
{
	private BufferedImage wall_image;
	private Model model;
	private int scrollPosY = 0;
	private String mode = "View";

	// Constructor
	public View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		
		try
		{
			this.wall_image = ImageIO.read(new File("wall.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	// Draws background color, wall graphics, and text
	public void paintComponent(Graphics g)
	{
		// Changes background color based on current mode
		if (mode.equals("View")) {
			g.setColor(new Color(172, 127, 105));
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
				g.drawImage(wall_image, wall.getX(), wall.getY() - scrollPosY, 50, 50, null);
			}
		
		// Text that indicates current mode
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Current mode: " + mode, 10, 730);

	}

	// Getters and setters
	//----------------------------------------------------------------
	public void setMode(String mode) {
		this.mode = mode;
	}

	public void incScrollPos() {
		scrollPosY -= 25;
	}

	public void decScrollPos() {
		scrollPosY += 25;
	}

	public int getScrollPos() {
		return scrollPosY;
	}
	//----------------------------------------------------------------
}