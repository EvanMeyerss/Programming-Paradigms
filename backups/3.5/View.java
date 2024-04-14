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
	private int scrollPosYView = 0;
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
	
		// Determines camera y-axis behavior
		// Stops scroll when player gets to vertical boundary	
		scrollPosYView = model.getPlayerY();
		if (model.getPlayerY() <= -300) {
			scrollPosYView = -300;
		}
		else if (model.getPlayerY() >= 300) {
			scrollPosYView = 300;
		}

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
				int yFixed = wall.getY() - scrollPosYView;
				wall.drawYourself(g, yFixed);
			}
		
		// Draws Pacman player
		Pacman player = model.getPlayerObject();
		// System.out.println(isCollision());
		if (!model.isCollision())
		player.drawYourself(g);

		
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

	public void incScrollPos() {
		scrollPosYView -= 25;
	}

	public void decScrollPos() {
		scrollPosYView += 25;
	}

	public int getScrollPos() {
		return scrollPosYView;
	}
	//----------------------------------------------------------------

}