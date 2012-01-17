package dynamicearth.app.graphics;

import processing.core.PApplet;
import processing.core.PFont;
import dynamicearth.app.util.BasicUtils;
import de.fhpotsdam.unfolding.geo.*;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class Quake
{
	PApplet parent;
	float xpos, ypos;
	boolean MODEL = false;
	
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
	int animating = 0;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	float easing = 0.025f;
	float aniStroke = 10;
	float aniAlpha = 100;
	
	public Quake(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, String d, float la, float lo, boolean b)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;

		Motion.setup(parent);
		tweenIN = new Tween(0f, 255, 10f);
		tweenOUT = new Tween(255, 0f, 10f);
		
		if(b){ //| 7 Day RSS
			name = n.replace(",", ""); 
			name = name.substring(2);
			PApplet.println(name);
			
		} else { //| 30 Day RSS
			name = n.replace("-", "");
			name = name.replace(",", "");
			PApplet.println(name);
		}
		
		description = d.substring(0, 26);
		quake = new Location(la,lo);
		
		String[] a = name.split(" ");
		mag = Math.round(Float.valueOf(a[0]).floatValue() * 10);
		magnitude = Float.parseFloat(a[0]);

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
		animating = 2;
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
			
			case 2:
			float j = tweenOUT.getPosition();
			alphaBackground = j - 155;
			alphaForeground = j;
			if(alphaForeground == 0) animating = 0;
			break;
		}
	}
	
	public void draw() 	
	{	
		//| Earthquake Size
		int scale = mag;

		//| Larger Scale to Bay Model Screen Resolution
		float[] position = BasicUtils.scaleCoordinates(wid, hei, xpos, ypos);

		//| Model Projection View
		parent.pushMatrix();
		parent.translate(position[0],position[1]);
		if(MODEL) parent.rotate(PApplet.radians(270));
		
		parent.smooth(); 	
		parent.fill(255, 255, 255, alphaBackground);
		parent.stroke(255, alphaForeground);
		parent.strokeWeight(1);
		parent.ellipse(0, 0, scale, scale);

		parent.fill(255, 255, 255, alphaForeground);
		parent.textFont(displayText, 12);
		parent.text(description, 0 + mag/2 + 10, 5);
		parent.text("Magnitude " + magnitude, 0 + mag/2 + 10, 16);
		
		//| Animation
		if(alphaForeground < 255){
			parent.popMatrix();
			return;
		}
		
		this.drawAni();			
		parent.noFill();
		parent.stroke(255, 255, 255, aniAlpha);
		parent.strokeWeight(aniStroke);
		parent.ellipse(0, 0, scale +  aniStroke, scale +  aniStroke);

		parent.popMatrix();
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
	
	public void kill()
	{
		tweenIN.stop();
		tweenOUT.stop();
		animating = 0;
		alphaBackground = 0;
		alphaForeground = 0;
		easing = 0.025f;
		aniStroke = 10;
		aniAlpha = 100;
	}
}












