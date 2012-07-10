package findingfaultlines.app.elements;

import processing.core.PApplet;
import processing.core.PFont;


import de.fhpotsdam.unfolding.geo.*;
import findingfaultlines.app.utils.BasicUtils;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class Quake
{
	PApplet parent;

	boolean BAYMODEL = true;

	float xpos, ypos;

	Location origin;
	String name;
	String[] date; 
	float magnitude;
	int mag;
	float size;

	PFont magText;
	PFont displayText;

	boolean status = false;
	boolean STOPANIMATING = false;
	int timekeeper = 0;
	int animating = 0;
	float alphaBackground = 0;
	float alphaForeground = 0;
	float alphaQuake = 0;
	float strokeQuake = 0;
	
	Tween tweenIN;
	Tween tweenDIM;
	Tween tweenOUT;
	Tween tweenSizeOUT;
	Tween tweenQuake;
	Tween tweenQuakeAlpha;
	
	public Quake(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, String d, float la, float lo)
	{
		origin = new Location(la,lo);
		name = n;
		
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
		
		//| Size of Quake
		size = (mag == 0) ? 2 : mag;
		
		//| Animaion Tween
		Motion.setup(parent);
		tweenIN = new Tween(0f, 255f, 20f);
		tweenDIM = new Tween(255f, 180f, 10f);
		tweenOUT = new Tween(180f, 0f, 7f);
		tweenQuake = new Tween(size, mag*4, mag*2, Tween.EXPO_EASE_IN); //, Tween.EXPO_EASE_IN
		tweenQuakeAlpha = new Tween(255f, 0, mag*2, Tween.EXPO_EASE_IN); //, Tween.EXPO_EASE_IN
		tweenSizeOUT = new Tween(size, 0f, 5f);
		
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
		
		STOPANIMATING = false;
		status = true;
		
		animating = 1;
		size = (mag == 0) ? 2 : mag;

		tweenIN.play();
		tweenQuake.play();
		tweenQuakeAlpha.play();
	}
	
	public void close()
	{
		if(status == false) return;
//		alphaBackground = 0;
//		alphaForeground = 150;

		STOPANIMATING = false;
		status = false;
		
		animating = 3;

		tweenIN.stop();
		tweenQuake.stop();
		tweenQuakeAlpha.stop();
		tweenOUT.play();
		tweenSizeOUT.play();
	}
	
 	public boolean getStatus()
	{
		return status;
	}
	
	public void update(float x, float y)
	{	
		if(STOPANIMATING == true) return;
		
		//| Screen
		float[] reproject;
		if(BAYMODEL) reproject = BasicUtils.scaleCoordinates(985, 788, 1500, 1200, x, y);
		else reproject = new float[]{x,y};
		
		xpos = reproject[0];
		ypos = reproject[1];	
		
		//| Animation
		switch(animating){
			case 1: //| Animating In
				float v = tweenIN.getPosition();
				alphaBackground = v - 155;
				alphaForeground = v;
				strokeQuake = tweenQuake.getPosition();
				alphaQuake = tweenQuakeAlpha.getPosition();
				if(alphaQuake == 0) this.dim();
				if(timekeeper > 30) this.dim();
				break;
				
			case 2: //| Animating Dim
				float j = tweenDIM.getPosition();
				alphaBackground = j - 155;
				alphaForeground = j;
				break;
			
			case 3: //| Animating Out
				float k = tweenOUT.getPosition();
				alphaBackground = k - 155;
				alphaForeground = k;
				if(alphaQuake > 0) alphaQuake = k;
				size = tweenSizeOUT.getPosition();
				if(alphaForeground == 0) animating = 0;
				break;
		}
	}
	
	public void draw() 	
	{	
		//| Model Projection View
		parent.pushMatrix();
		parent.translate(xpos,ypos);
		
		parent.smooth();

		parent.noFill();
/*		
		parent.stroke(255, alphaQuake);
		parent.strokeWeight(strokeQuake);
		parent.ellipse(0, 0, size, size);
*/
		parent.stroke(255, alphaQuake);
		parent.strokeWeight(strokeQuake/2);
		parent.ellipse(0, 0, size + strokeQuake, size + strokeQuake);

		parent.stroke(200, alphaQuake);
		parent.strokeWeight(strokeQuake/4);
		parent.ellipse(0, 0, size + strokeQuake + strokeQuake/3, size + strokeQuake + strokeQuake/3);
	
		parent.fill(255, 255, 255, alphaBackground);
		parent.stroke(170, 170, 170, alphaForeground);
		parent.strokeWeight(1);
		parent.ellipse(0, 0, size, size);
		
		parent.popMatrix();
	}
	
	public void dim()
	{
		timekeeper = 0;
		animating = 2;
		
		tweenIN.stop();
		tweenQuake.stop();
		tweenQuakeAlpha.stop();
		
		tweenDIM.play();
	}
	
	public void sitStill()
	{
		STOPANIMATING = true;
		
		tweenIN.stop();
		tweenDIM.stop();
		tweenOUT.stop();
		tweenQuake.stop();
		tweenQuakeAlpha.stop();
		tweenSizeOUT.stop();
	}
	
	public void kill()
	{
		STOPANIMATING = false;
		status = false;
		
		tweenIN.stop();
		tweenDIM.stop();
		tweenOUT.stop();
		tweenQuake.stop();
		tweenQuakeAlpha.stop();
		tweenSizeOUT.stop();
		animating = 0;
		alphaBackground = 0;
		alphaForeground = 0;
		alphaQuake = 0;
		strokeQuake = 0;
	}
}
