// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
// import java.nio.Buffer;
// import java.io.IOException;

public class View extends JPanel
{
	private Model model;
	private int scrollPosYView = 0;
	// Text displayed at the bottom left of screen
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
		// The "-25" positions the character exactly in the middle of the window
		scrollPosYView = ((Game.WINDOW_HEIGHT/2) - 25 - player.getY());
		if (scrollPosYView < -300) {
			scrollPosYView = -300;
		}
			
		if (scrollPosYView > 300) {
			scrollPosYView = 300;
		}
		// --------------------------------------------------------------------

		// Changes background color based on current mode
		if (mode.equals("View")) {
			g.setColor(new Color(182, 201, 255));
		}
		else {
			g.setColor(new Color(204, 181, 158));
		}

		// Creates background
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		
		// Draws Pacman player
		// ----------------------------------------------------------
		// if statements check if the map stops moving.
		// if it has, then the player starts to be drawn vertically
		int localYFixed;
		if (player.getY() >= 650) { 
            localYFixed = player.getY() - 300;
		}
        else if (player.getY() <= 50) {
            localYFixed = player.getY() + 300;
        }
        else 
        	localYFixed = Game.WINDOW_HEIGHT/2 - 25;
		player.setYFixed(localYFixed);
		player.draw(g, 0);
		
		// Creates all sprite graphics
		for(int i = 0; i < model.getSpritesSize(); i++)
		{
			Sprite sprite = model.getSprite(i);
			sprite.draw(g, scrollPosYView);
		}

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