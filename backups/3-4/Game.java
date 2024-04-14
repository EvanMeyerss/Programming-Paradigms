// Name: 					Evan Meyers
// Date: 					3/3/2024
// Assignment Description: 	Implements a PacMan-like character

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{

	private Model model = new Model();
	private Controller controller = new Controller(model);
	private View view = new View(controller, model);
	public static final int MAP_WIDTH = 750, MAP_HEIGHT = 1350, WINDOW_WIDTH = 750, WINDOW_HEIGHT = 750;
	
	public Game()
	{
		this.setTitle("A4 - Character Implementation");
		this.setResizable(false);
		// I don't put WINDOW_WIDTH and WINDOW_HEIGHT here because they don't make a perfectly square window for some reason.
		this.setSize(764,785);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseMotionListener(controller);
		view.addMouseListener(controller);
		view.addKeyListener(controller);
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
			} 
			catch(Exception e) 
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}								
}