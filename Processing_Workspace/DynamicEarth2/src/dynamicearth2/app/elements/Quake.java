package dynamicearth2.app.elements;

import processing.core.PApplet;
import processing.core.PFont;
import dynamicearth2.app.utils.BasicUtils;
import de.fhpotsdam.unfolding.geo.*;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class Quake
{
	PApplet parent;
	float xpos, ypos;
	boolean status = false;

	Location origin;
	String name;
	String[] date; 
	float magnitude;
	int mag;
	
	PFont magText;
	PFont displayText;

	Tween tweenIN;
	Tween tweenOUT;
	int animating = 0;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	public Quake(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, String d, float la, float lo)
	{
		origin = new Location(la,lo);
		name = n;
		
		//| Animaion Tween
		Motion.setup(parent);
		tweenIN = new Tween(0f, 255, 5f);
		tweenOUT = new Tween(255, 0f, 10f);
		
		//| Figure out the Year, Month, Day, and Code (20120101)
		String[] sd = d.split(" "); //| d = 1974 08 04
		String month;
		if(sd[1].equals("01")) month = "January";
		else if(sd[1].equals("02")) month = "Ferbruary";
		else if(sd[1].equals("03")) month = "March";
		else if(sd[1].equals("04")) month = "April";
		else if(sd[1].equals("05")) month = "May";
		else if(sd[1].equals("06")) month = "June";
		else if(sd[1].equals("07")) month = "July";
		else if(sd[1].equals("08")) month = "August";
		else if(sd[1].equals("09")) month = "September";
		else if(sd[1].equals("10")) month = "October";
		else if(sd[1].equals("11")) month = "November";
		else if(sd[1].equals("12")) month = "December";
		else month = "Rapture";

		date = new String[4];
		date[0] = sd[0];
		date[1] = month;
		date[2] = sd[2];
		date[3] = sd[0] +""+ sd[1] +""+ sd[2];
		
		//| Figure out the Magnitude Int and Float
		String[] a = name.split(" "); //| a = M, 0.0, Location Name
		float m = 0.0f;
		if(!a[1].equals("None")) m = Float.valueOf(a[1]).floatValue();
		mag = Math.round(m * 2);
		magnitude = m;
		
		//| Needs no itroduction
		displayText = parent.createFont("data/fonts/Explo/Explo-Light.otf", 20);
	}
	
	public String getName()
	{	
		return name;
	}
	
	public String[] getDate()
	{
		return date;
	}
	
	public Location getLocation()
	{
		return origin;
	}
		
	public float getMagnitude()
	{
		return magnitude;
	}
	
	public void open()
	{
		if(status == true) return;
//		alphaBackground = 255;
//		alphaForeground = 255;
		animating = 1;
		tweenIN.play();
		status = true;
	}
	
	public void close()
	{
		if(status == false) return;
//		alphaBackground = 0;
//		alphaForeground = 150;
		animating = 2;
		tweenOUT.play();
		status = false;
	}
	
	public boolean getStatus()
	{
		return status;
	}
	
	public void update(float x, float y)
	{	
		//| Larger Scale to Bay Model Screen Resolution
		float[] reproject = BasicUtils.scaleCoordinates(985, 788, 1500, 1200, x, y);
//		float[] reproject = {x,y};
		xpos = reproject[0];
		ypos = reproject[1];	
		
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
		float size = mag;
		if(size == 0) size = 2;
		
		//| Model Projection View
		parent.pushMatrix();
		parent.translate(xpos,ypos);
		
		parent.smooth();
		parent.fill(255, 255, 255, alphaBackground);
		parent.stroke(170, 170, 170, alphaForeground);
		parent.strokeWeight(1);
		parent.ellipse(0, 0, size, size);
		
		parent.popMatrix();
	}
	
	public void kill()
	{
		tweenIN.stop();
		tweenOUT.stop();
		alphaBackground = 0;
		alphaForeground = 0;
	}
}












