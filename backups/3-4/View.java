// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.Buffer;
import java.awt.Color;
import java.awt.Font;
// import java.io.IOException;

public class View extends JPanel
{
	private BufferedImage wall_image;
	private Model model;
	private int scrollPosYView = 0, scrollPosYPlayer = 350;
	private String mode = "View";
	private BufferedImage[] pacmanImages;

	// Constructor
	public View(Controller c, Model m)
	{
		model = m;
		c.setView(this);		

		wall_image = loadImage("wall.png");

		pacmanImages = new BufferedImage[12];
            for (int i = 0; i < pacmanImages.length; i++) {
                int j = i + 1;
                pacmanImages[i] = loadImage("pacman" + j + ".png");
            }
	}
	
	// Draws background color, wall graphics, and text
	public void paintComponent(Graphics g)
	{
	
		// Determines camera y-axis behavior	
		scrollPosYView = model.getPlayerY();
		if (model.getPlayerY() <= -300) {
			scrollPosYView = -300;
			// scrollPosYPlayer = model.getPlayerY() + 650;
		}
		else if (model.getPlayerY() >= 300) {
			scrollPosYView = 300;
			// scrollPosYPlayer = model.getPlayerY() + 50;
		}

		// Determines horizontal warping behavior
		// if (model.getPlayerX() > Game.MAP_WIDTH) {
		// 	model.setPlayerX(Game.MAP_WIDTH - 800);
		// }
		// else if (model.getPlayerX() < Game.MAP_WIDTH - 800) {
		// 	model.setPlayerX(Game.MAP_WIDTH);
		// }

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
				// g.drawImage(wall_image, wall.getX(), wall.getY() - scrollPosYView, 50, 50, null);
			}
		
		// Draws Pacman player
		Pacman player = model.getPlayerObject();
		player.drawYourself(g);

		// Draws initial player image - with collision detection (DOESN'T WORK)
		// if (!noCollision())
		// for (int i = 0; i < 2; i++) {
		// 	g.drawImage(pacmanImages[(i + 1) * model.getDirection()], model.getPlayerX(), scrollPosYPlayer, 50, 50, null);
	
		// }

		
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

	// Collision detector method
	// public boolean noCollision() {		
	// 	for(int i = 0; i < model.getWallSize(); i++)
	// 		{
	// 			Wall wall = model.getWall(i);
				
	// 			if (model.getPlayerX() + 50 < wall.getX()) {
	// 				return false;
	// 			}
	// 			if (model.getPlayerX() > wall.getX() + 50) {
	// 				return false;
	// 			}
	// 			if (model.getPlayerY() + 50 < wall.getY()) {
	// 				return false;
	// 			}
	// 			if (model.getPlayerY() > wall.getY() + 50) {
	// 				return false;
	// 			}
	// 		}
	// 	return true;
	// }
}