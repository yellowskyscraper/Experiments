package app;
import processing.core.PApplet;
import processing.core.PFont;

public class Ball
{
	PApplet parent;
	public float xpos, ypos;
	String name;
	PFont magText;
	String mag;
	int magnitude = 0;
	
	int size = 0;
	int fillalph = 0;
	int strokealph = 0;
	
	public Ball(PApplet p)
	{
		parent = p;
		magText = parent.createFont("../data/fonts/Georgia-Bold.ttf", 20);
	}
	
	public void setup(String n, float x, float y)
	{
		name = n;
		xpos = x;
		ypos = y;

		String[] a = name.split(" ");
		
		mag = Character.toString(a[1].charAt(0));
		int m = 10;
		if(!a[1].equals("None")) m = Math.round(Float.valueOf(a[1]).floatValue() * 10);
		magnitude = m;
	}
	
	public String[] getYearMonthDay()
	{	
		
		String[] t = name.split(",");
		String[] d = name.split(" ");
		
		String year = d[3];
		String month = d[4];
		String day = d[5].replace(",","");
		String title = t[1].toLowerCase() + " " + d[1];
		String magnitude = d[2];
		
		String[] date = {year, month, day, title, magnitude}; 
		return date;
	}
	
	public void start()
	{
		size = 2 * magnitude;
		fillalph = 255;
		strokealph = 255;
	}
	
	public void drawAni() 	
	{	
		//| Conditions
		if(fillalph > 0) fillalph -= 40;
		if(fillalph <= 0 && strokealph > 0) strokealph -= 10;
		if(fillalph <= 0 && strokealph > 0 && strokealph < 200) size += 3;

		//| Elements
		parent.smooth(); 	
		parent.fill(255, 255, 255, fillalph);
		parent.ellipse(xpos, ypos, size, size);
		
		parent.fill(0, 255, 255, fillalph);
		parent.stroke(255, 255, 255, strokealph);
		parent.ellipse(xpos, ypos, size, size);
		parent.noStroke();
		parent.ellipse(xpos, ypos, 1, 1);
	}
	
	public void draw() 	
	{	
		parent.smooth(); 	
		parent.fill(0, 255, 255, 255);
		parent.stroke(255);
		parent.ellipse(xpos, ypos, 8, 8);
	}
}
