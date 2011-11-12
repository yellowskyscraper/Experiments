package dynamicearth.app.graphics;

import processing.core.PApplet;
import processing.core.PFont;
import dynamicearth.app.util.BasicUtils;
import de.fhpotsdam.unfolding.geo.*;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

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

	Tween tweenIN;
	Tween tweenOUT;
	int animating = 3;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
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

		Motion.setup(parent);
		tweenIN = new Tween(0f, 255, 10f);
		tweenOUT = new Tween(255, 0f, 10f);
		
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
	
	public void open()
	{
		animating = 1;
		tweenIN.play();
	}
	
	public void close()
	{
		animating = 0;
		tweenOUT.play();
	}
	
	public void update(float x, float y)
	{	
		xpos = x;
		ypos = y;
		
		switch(animating){
			case 1:
			float v = tweenIN.getPosition();
			alphaBackground = v - 155;
			alphaForeground = v;
			break;
			
			case 0:
			float j = tweenOUT.getPosition();
			alphaBackground = j - 155;
			alphaForeground = j;
			break;
		}
	}
	
	public void draw() 	
	{	
		//| Earthquake Size
		int scale = mag;

		//| Larger Scale to Bay Model Screen Resolution
		float[] position = BasicUtils.scaleCoordinates(wid, hei, xpos, ypos);
		
		parent.smooth(); 	
		parent.fill(255, 255, 255, alphaBackground);
		parent.stroke(255, alphaForeground);
		parent.strokeWeight(1);
		parent.ellipse(position[0], position[1], scale, scale);

		parent.fill(255, 255, 255, alphaForeground);
		parent.textFont(displayText, 15);
		parent.text("Magnitude " + magnitude, position[0] + mag/2 + 10, position[1] - 2);
		parent.text(description, position[0] + mag/2 + 10, position[1] + 16);
		
		//| Animation
		if(alphaForeground < 255) return;
		this.drawAni();			
		parent.noFill();
		parent.stroke(255, 255, 255, aniAlpha);
		parent.strokeWeight(aniStroke);
		parent.ellipse(position[0], position[1], scale +  aniStroke, scale +  aniStroke);
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
}












