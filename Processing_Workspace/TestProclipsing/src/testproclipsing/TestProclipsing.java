package testproclipsing;

import processing.core.PApplet;


public class TestProclipsing extends PApplet 
{
	private static final long serialVersionUID = 1L;
	
	public void setup() 
	{
		background(223);
		size(screenWidth, screenHeight);
	}

	public void draw() 
	{
		
	}
	
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", TestProclipsing.class.getName() });
	}
}
