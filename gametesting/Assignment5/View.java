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


public class View extends JPanel
{
	private Model model;
	private int scrollPosYView = 0;
	// Text displayed at the bottom left of screen
	private String mode = "View", modeType = null;
	private boolean isEdit = false;

	// Constructor
	public View(Controller c, Model m)
	{
		model = m;
		c.setView(this);		
	}
	
	// Draws background color, sprite graphics, and text
	public void paintComponent(Graphics g)
	{
		// Moves the map screen while not at one of the vertical wall borders
		// --------------------------------------------------------------------
		for(int i = 0; i < model.getSpritesSize(); i++)
		{
			Sprite sprite = model.getSprite(i);
			if (sprite.isPacman()) {
				// Conditionals prevent the camera from going beyond vertical boundaries
				// The "-25" positions the camera exactly in the middle of the window
				scrollPosYView = ((Game.WINDOW_HEIGHT/2) - 25 - sprite.getY());
				if (scrollPosYView < -300) {
					scrollPosYView = -300;
				}
					
				if (scrollPosYView > 300) {
					scrollPosYView = 300;
				}
			}
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
	
		
		// Creates all sprite graphics
		for(int i = 0; i < model.getSpritesSize(); i++)
		{
			Sprite sprite = model.getSprite(i);
			sprite.draw(g, scrollPosYView);
		}

		// Text that indicates current score and edit mode
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.BOLD, 25));
		g.drawString("Score: " + model.getScore(), Game.MAP_WIDTH/2 - 52, 27);
		g.setFont(new Font("Calibri", Font.BOLD, 20));
		if(isEdit) {
			g.drawString("Edit: " + modeType, 11, 736);
		}
			
		g.setColor(Color.WHITE);
		g.setFont(new Font("Calibri", Font.BOLD, 25));
		g.drawString("Score: " + model.getScore(), Game.MAP_WIDTH/2 - 50, 25);
		g.setFont(new Font("Calibri", Font.BOLD, 20));
		if(isEdit) {
			g.drawString("Edit: " + modeType, 10, 735);
		}
	}

	// Image loading methodm
	public static BufferedImage loadImage(String filename) {
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
		if(mode.equals("View")) {
			isEdit = false;
			return;
		}		
		isEdit = true;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public int getScrollPos() {
		return scrollPosYView;
	}
	//----------------------------------------------------------------

}