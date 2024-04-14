/* Name: 					Evan Meyers
// Date: 					2/17/2024
// Assignment Description: 	Familarize myself with JSON files, more
							object oriented programming, and listeners 
*/

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
// import java.io.IOException;
import java.io.File;
import java.awt.Color;
import java.awt.Font;

public class View extends JPanel
{
	private BufferedImage wall_image;
	private Model model;
	private int scrollPosY;
	private String mode = "View", addRemove = "Adding";


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
	
	
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for(int i = 0; i < model.getWallSize(); i++)
			{
				Wall wall = model.getWall(i);
				g.drawImage(wall_image, wall.getX(), wall.getY() - scrollPosY, wall.getW(), wall.getH(), null);
			}
		
		// Text that indicates current mode
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Current mode: " + mode, 10, 730);
		// Only displays "add" or "remove" when in editing mode
		if (mode.equals("Edit")) g.drawString(addRemove, 10, 700);

	}

	// Getters and setters
	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setAddRemove(String addRemove) {
		this.addRemove = addRemove;
	}

	public void incScrollPos() {
		scrollPosY -= 20;
	}

	public void decScrollPos() {
		scrollPosY += 20;
	}
}
