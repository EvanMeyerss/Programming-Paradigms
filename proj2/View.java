// Name: 					Evan Meyers
// Date: 					2/7/2024
// Assignment Description: 	To familiarize myself with the relations of different 
// 							classes and how to create and animate graphics on the screen.

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

public class View extends JPanel
{
	private JButton b1;
	private BufferedImage turtle_image;
	private Model model;

	public View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		b1 = new JButton("Dr Doofenshmirtz Favorite Button (Self-Destruct)");
		b1.addActionListener(c);
		this.add(b1);
		
		try
		{
			this.turtle_image = ImageIO.read(new File("turtle.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	// Method used in Controller.java that allows for the button to be removed
	void removeButton()
	{
		this.remove(b1);
		this.repaint();
	}
	
	// Changes window color and graphically updates turtle position
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(this.turtle_image, model.getTurtleX(), model.getTurtleY(), null);
	}
}
