// Name: 					Evan Meyers
// Date: 					2/7/2024
// Assignment Description: 	To familiarize myself with the relations of different 
// 							classes and how to create and animate graphics on the screen.

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller, model);
	
	public Game()
	{
		this.setTitle("Turtle wars!");
		this.setSize(500, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
	
	// Updates the screen at a rate of 25 frames-per-second showing the turtle move positions
	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // This will indirectly call View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 milliseconds --> Should update 25 frames-per-second
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}								
}
