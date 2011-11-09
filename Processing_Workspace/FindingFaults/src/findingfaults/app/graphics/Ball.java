package findingfaults.app.graphics;

import processing.core.PApplet;
import processing.core.PFont;

import de.fhpotsdam.unfolding.geo.*;
import findingfaults.app.util.BasicUtils;

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
	int wid = 1400;
	int hei = 1050;

	float easing = 0.05f;
	float aniStroke = 0;
	float aniAlpha = 0;
	float scale = 0;
	float aniScale = 0;
	
	public Ball(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, float la, float lo)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;
		
		//name = n.replace(",", ""); //| 7 Day RSS
		name = n; //| all.xml Local
		quake = new Location(la,lo);
		
		String[] a = name.split(" ");
		mag = Character.toString(a[1].charAt(0));
		int m = 10;
		if(!a[1].equals("None")) m = Math.round(Float.valueOf(a[1]).floatValue() * 10);
		magnitude = m;
		scale = magnitude/3;

		displayText = parent.createFont("data/fonts/Explo/Explo-Light.otf", 20);
	}
	
	public String[] getYearMonthDayMagnitude()
	{	
		String[] t = name.split(",");
		String[] d = name.split(" ");
		
		String year = d[3];
		String month = d[4];
		String day = d[5].replace(",","");
		String title = t[1];
		String magnitude = d[1];
		
		String m = "00";
		if(month.equals("Jan")) m = "01";
		else if(month.equals("Feb")) m = "02";
		else if(month.equals("Mar")) m = "03";
		else if(month.equals("Apr")) m = "04";
		else if(month.equals("May")) m = "05";
		else if(month.equals("Jun")) m = "06";
		else if(month.equals("Jul")) m = "07";
		else if(month.equals("Aug")) m = "08";
		else if(month.equals("Sep")) m = "09";
		else if(month.equals("Oct")) m = "10";
		else if(month.equals("Nov")) m = "11";
		else if(month.equals("Dec")) m = "12";	
		
		String n = "00";
		if(day.equals("1")) n = "01";
		else if(day.equals("2")) n = "02";
		else if(day.equals("3")) n = "03";
		else if(day.equals("4")) n = "04";
		else if(day.equals("5")) n = "05";
		else if(day.equals("6")) n = "06";
		else if(day.equals("7")) n = "07";
		else if(day.equals("8")) n = "08";
		else if(day.equals("9")) n = "09";
		else n = day;
		
		String code = year +"."+ m +"."+ n;
		
		String[] date = {year, month, day, title, magnitude, code}; 
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
	}

	public void startAni()
	{
		aniScale = scale;
		aniStroke = 10;
		aniAlpha = 100;
	}

	public void drawAni() 	
	{	
		//| Larger Scale to Bay Model Screen Resolution
		float[] position = BasicUtils.scaleCoordinates(wid, hei, xpos, ypos);
		
		parent.smooth(); 	
		parent.fill(255, 255, 255, 100);
		parent.stroke(255);
		parent.strokeWeight(1);
		parent.ellipse(position[0], position[1], aniScale, aniScale);

		/*
		parent.fill(255, 255, 255, 255);
		parent.textFont(displayText, 15);
		parent.text(name, newX + magnitude/2 + 10, newY + 5);
		
		//| Attempt to animate
		float ta = 0 + aniAlpha;
		float ts = 30 - aniStroke;
		
		if(ta > 0){
			aniAlpha -= ta * easing;
			aniStroke += ts * easing;
		} 
		
		if(ta < 5){
			aniAlpha = 100;
			aniStroke = 10;
		}
		
		parent.smooth(); 	
		parent.noFill();
		parent.stroke(255, 255, 255, aniAlpha);
		parent.strokeWeight(aniStroke);
		parent.ellipse(newX, newY, scale +  aniStroke, scale +  aniStroke);
		*/
	}
}












