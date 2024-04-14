// Name: 					Evan Meyers
// Date: 					2/7/2024
// Assignment Description: 	To familiarize myself with the relations of different 
// 							classes and how to create and animate graphics on the screen.

public class Model
{
	private int turtleX;
	private int turtleY;
	private int destX;
	private int destY;

	public Model()
	{
	}

	public void update()
	{
		// Move the turtle
		if(this.turtleX < this.destX)
			// Logic within each condition ensures that the turtle won't 
			// bounce around when stuck between a group of four pixels
			this.turtleX += Math.min(4, Math.abs(destX - turtleX));
		else if(this.turtleX > this.destX)
			this.turtleX -= Math.min(4, Math.abs(destX - turtleX));
		if(this.turtleY < this.destY)
			this.turtleY += Math.min(4, Math.abs(destY - turtleY));
		else if(this.turtleY > this.destY)
			this.turtleY -= Math.min(4, Math.abs(destY - turtleY));
	}

	// Sets destination turtle must travel to
	public void setDestination(int x, int y)
	{
		this.destX = x;
		this.destY = y;
	}
	
	// Sets the current position of turtle
	public void setTurtlePos(int x, int y)
	{
		this.turtleX = x;
		this.turtleY = y;
	}
	
	public int getTurtleX()
	{
		return turtleX;
	}
	
	public int getTurtleY()
	{
		return turtleY;
	}
	
	public int getDestX() 
	{
		return destX;
	}
	
	public int getDestY()
	{
		return destY;
	}
	
	// Following four methods allow for the use of arrow keys in Controller.java
	public void setDestXRight() 
	{
		destX+=4;
	}
	
	public void setDestXLeft()
	{
		destX-=4;
	}
	
	public void setDestYUp()
	{
		destY-=4;
	}
	
	public void setDestYDown()
	{
		destY+=4;
	}
	
	
}