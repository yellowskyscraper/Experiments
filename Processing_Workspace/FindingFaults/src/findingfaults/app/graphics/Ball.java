package findingfaults.app.graphics;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;

import de.fhpotsdam.unfolding.geo.*;
import findingfaults.app.util.BasicUtils;

public class Ball
{
	PApplet parent;
	int wid = 1400;
	int hei = 1050;
	
	float xpos, ypos;
	Location quake;
	String name;
	float magnitude;
	int mag;

	Tween tweenIN;
	Tween tweenOUT;
	Tween tweenDIM;
	int animating = 3;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	float easing = 0.1f;
	float aniScale = 0;
	float aniStroke = 0;
	float baseAlpha = 0;
	float aniAlpha = 0;
	float scale = 0;
	
	public Ball(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, float la, float lo)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;

		Motion.setup(parent);
		tweenIN = new Tween(0f, 255, 10f);
		tweenOUT = new Tween(255, 0f, 10f);
		tweenDIM = new Tween(255, 100f, 10f);
		
		//name = n.replace(",", ""); //| 7 Day RSS
		name = n; //| all.xml Local
		quake = new Location(la,lo);
		
		String[] a = name.split(" ");
		if(!a[1].equals("None")) mag = Math.round(Float.valueOf(a[1]).floatValue() * 10);
		else mag = 10;
		
		if(!a[1].equals("None")) magnitude = Float.parseFloat(a[1]);
		else magnitude = 0.0f;
		
		scale = mag/3;
	}
	
	public String[] getYearMonthDayMagnitude()
	{	
		String[] t = name.split(",");
		String[] d = name.split(" ");
		
		String year = d[3];
		String month = d[4];
		String day = d[5].replace(",","");
		String title = t[1];
		String magnitude = (d[1].equals("None")) ? "0" : d[1];
		
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
	
	public void open()
	{
		animating = 1;
		tweenIN.play();
		tweenDIM.play();
	}
	
	public void close()
	{
		animating = 0;
		tweenOUT.play();
	}
	
	public void dim()
	{
		
	}
	
	public void update(float x, float y)
	{	
		xpos = x;
		ypos = y;
		/*
		switch(animating){
			case 1:
			float v = tweenIN.getPosition();
			float k = tweenDIM.getPosition();
			alphaBackground = v;
			alphaForeground = k;
			break;
			
			case 0:
			float j = tweenOUT.getPosition();
			alphaBackground = j;
			alphaForeground = j;
			break;
		}
		*/
	}
	
	public void draw() 	
	{	
		//| Larger Scale to Bay Model Screen Resolution
		float[] position = BasicUtils.scaleCoordinates(wid, hei, xpos, ypos);

		parent.smooth(); 	
		parent.fill(255, 255, 255, baseAlpha);
		parent.stroke(255);
		parent.strokeWeight(1);
		parent.ellipse(position[0], position[1], scale, scale);
		
		//| Animation
		this.drawAni();			
		parent.noFill();
		parent.stroke(255, 255, 255, aniAlpha);
		parent.strokeWeight(aniStroke);
		parent.ellipse(position[0], position[1], scale +  aniStroke, scale +  aniStroke);
	}

	public void startAni()
	{	
		aniScale = scale;
		aniStroke = 10;
		aniAlpha = 100;
		baseAlpha = 255;
	}

	public void drawAni()	
	{	
		float tb = 100 + baseAlpha;
		float ta = 0 + aniAlpha;
		float ts = mag - aniStroke;

		if(baseAlpha > 50) baseAlpha -= tb * 0.01f;
		if(ta > 0) aniAlpha -= ta * 0.1f;
		if(ta > 0) aniStroke += ts * 0.1f;
	}
}












