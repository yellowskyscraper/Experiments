package dynamicearth.app;

import processing.core.PApplet;
import processing.core.PFont;

import de.fhpotsdam.unfolding.geo.*;

public class Ball
{
	PApplet parent;
	float xpos, ypos;
	Location quake;
	String name;
	PFont magText;
	String mag;
	int magnitude = 0;
	PFont displayText;
	//| Projector 1400, 1050
	//| My Screen 1680, 1050
	int wid = 1400;
	int hei = 1050;
	
	int size = 0;
	int fillalph = 0;
	int strokealph = 0;
	
	public Ball(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, float la, float lo)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;
		
		name = n.replace(",", ""); //| 7 Day RSS
		//name = n; //| all.xml Local
		quake = new Location(la,lo);
		
		
		String[] a = name.split(" ");
		mag = Character.toString(a[1].charAt(0));
		int m = 10;
		if(!a[1].equals("None")) m = Math.round(Float.valueOf(a[1]).floatValue() * 10);
		magnitude = m;

		displayText = parent.createFont("data/fonts/Explo/Explo-Light.otf", 20);
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
	
	public Location getLocation()
	{
		return quake;
	}
	
	public void update(float x, float y)
	{	
		xpos = x;
		ypos = y;
	}
	
	public void draw() 	
	{	
		//| Earthquake Size
		int scale = magnitude/3;
		
		/*
		//| Small Scale of Unfolding Map Zoom 10
		int oldwidth = wid;
		float percentincrease = 0.26f * oldwidth;
		float newwidth = oldwidth + percentincrease;
		*/
		
		//| Larger Scale to Bay Model Screen Resolution
		int oldwidth = wid;
		float percentincreaseW = 0.42f * oldwidth;
		float newwidth = oldwidth + percentincreaseW;
		
		int oldheight = hei; 
		float percentincreaseH = 0.14f * oldheight;
		float newheight = oldheight + percentincreaseH;
		
		//| Scaled position based on percentage of stretch (x = newMax * n/oldMax)
		float newX = newwidth * xpos/oldwidth;
		float newY = newheight * ypos/oldheight;
		
		parent.smooth(); 	
		parent.fill(255, 255, 255, 100);
		parent.stroke(255);
		parent.ellipse(newX, newY, scale, scale);

		parent.fill(255, 255, 255, 255);
		parent.textFont(displayText, 15);
		parent.text(name, newX + 15, newY + 5);
	}
}
