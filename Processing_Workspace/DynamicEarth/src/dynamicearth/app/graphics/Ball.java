package dynamicearth.app.graphics;

import processing.core.PApplet;
import processing.core.PFont;

import de.fhpotsdam.unfolding.geo.*;

public class Ball
{
	PApplet parent;
	float xpos, ypos;
	
	Location quake;
	String name;
	String description;
	float magnitude;
	int mag;
	
	PFont magText;
	
	//String mag;
	PFont displayText;
	int wid = 1400;
	int hei = 1050;
	

	
	float easing = 0.05f;
	float aniStroke = 10;
	float aniAlpha = 100;
	
	public Ball(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, String d, float la, float lo)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;
		
		name = n.replace(",", ""); //| 7 Day RSS
		description = d;
		quake = new Location(la,lo);
		
		String[] a = name.split(" ");
		if(!a[1].equals("None")) mag = Math.round(Float.valueOf(a[1]).floatValue() * 10);
		else mag = 10;
		magnitude = Float.parseFloat(a[1]);

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
	
	public void drawAni()
	{
		float ta = 0 + aniAlpha;
		float ts = (mag * 2) - aniStroke;
		
		if(ta > 0){
			aniAlpha -= ta * easing;
			aniStroke += ts * easing;
		} 
		
		if(ta < 5){
			aniAlpha = 100;
			aniStroke = 10;
		}
	}
	
	public void draw() 	
	{	
		//| Earthquake Size
		int scale = mag;
		
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
		parent.strokeWeight(1);
		parent.ellipse(newX, newY, scale, scale);

		parent.fill(255, 255, 255, 255);
		parent.textFont(displayText, 15);
		parent.text("Magnitude " + magnitude, newX + mag/2 + 10, newY - 2);
		parent.text(description, newX + mag/2 + 10, newY + 16);
		
		//| Animation
		this.drawAni();			
		parent.noFill();
		parent.stroke(255, 255, 255, aniAlpha);
		parent.strokeWeight(aniStroke);
		parent.ellipse(newX, newY, scale +  aniStroke, scale +  aniStroke);
	}
}












