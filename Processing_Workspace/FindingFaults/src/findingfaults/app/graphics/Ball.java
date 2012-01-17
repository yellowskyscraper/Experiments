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

	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenStrokeShockWaveIN;
	Tween tweenAlphaShockWaveIN;
	Tween tweenBackgroundDIM;
	Tween tweenForegroundDIM;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	
	int animating = 0;
	float scaleEarthquake = 0;
	float alphaBackground = 0;
	float alphaForeground = 0;
	float alphaShockWave = 0;
	float strokeShockWave = 0;
	
	public Ball(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, float la, float lo)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;
		name = n;
		quake = new Location(la,lo);
		
		String[] a = name.split(" ");
		if(a[1].equals("None")) {
			mag = 10;
			magnitude = 0.0f;
		} else {
			mag = Math.round(Float.valueOf(a[1]).floatValue() * 10);
			magnitude = Float.parseFloat(a[1]);
		}

		scaleEarthquake = mag/3;
		
		//| Animation Setup
		float shockwave = mag;
		Motion.setup(parent);
		tweenBackgroundIN = new Tween(255f, 50f, shockwave/2);
		tweenForegroundIN = new Tween(255f, 200f, shockwave/2);
		tweenStrokeShockWaveIN = new Tween(0f, shockwave, shockwave/2);
		tweenAlphaShockWaveIN = new Tween(255f, 0, shockwave/2);
		
		tweenBackgroundDIM = new Tween(50f, 200f, 10f, 5f);
		tweenForegroundDIM = new Tween(140f, 255f, 5f);
		
		tweenBackgroundOUT = new Tween(255f, 0f, 5f);
		tweenForegroundOUT = new Tween(255f, 0f, 5f);
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
	
	public void triggerQuake()
	{	
		animating = 1;
		tweenBackgroundIN.play();
		tweenForegroundIN.play();
		tweenStrokeShockWaveIN.play();
		tweenAlphaShockWaveIN.play();
	}
	
	public void dim()
	{
		animating = 2;
		tweenBackgroundIN.stop();
		tweenForegroundIN.stop();
		tweenBackgroundDIM.play();
		tweenForegroundDIM.play();
	}
	
	public void close()
	{
		animating = 3;
		tweenBackgroundDIM.stop();
		tweenForegroundDIM.stop();
		tweenBackgroundOUT.play();
		tweenForegroundOUT.play();
	}
	
	public void update(float x, float y)
	{	
		xpos = x;
		ypos = y;
		
		switch(animating){
			case 1:
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			alphaShockWave = tweenAlphaShockWaveIN.getPosition();
			strokeShockWave = tweenStrokeShockWaveIN.getPosition();
			if(alphaShockWave == 0){
				tweenStrokeShockWaveIN.stop();
				tweenAlphaShockWaveIN.stop();
			}
			if(alphaBackground == 50){
				tweenBackgroundIN.stop();
				tweenForegroundIN.stop();
			}
			break;
		
			case 2:
			alphaBackground = tweenBackgroundDIM.getPosition();
			alphaForeground = tweenForegroundDIM.getPosition();
			alphaShockWave = tweenAlphaShockWaveIN.getPosition();
			strokeShockWave = tweenStrokeShockWaveIN.getPosition();
			if(alphaBackground == 255){
				tweenBackgroundDIM.stop();
				tweenForegroundDIM.stop();
			}
			if(alphaShockWave == 0){
				tweenStrokeShockWaveIN.stop();
				tweenAlphaShockWaveIN.stop();
			}
			break;

			case 3:
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			if(alphaBackground == 0){
				tweenBackgroundOUT.stop();
				tweenForegroundOUT.stop();
			}
			break;
		}
	}
	
	public void draw() 	
	{	
		//| Larger Scale to Bay Model Screen Resolution
		float[] position = BasicUtils.scaleCoordinates(wid, hei, xpos, ypos);

		//| Background Circle
		parent.smooth();
		parent.noStroke();
		parent.fill(255, 255, 255, alphaBackground);
		parent.ellipse(position[0], position[1], scaleEarthquake, scaleEarthquake);
		
		//| Foreground Stroke
		parent.noFill();
		parent.strokeWeight(1);
//		parent.stroke(154, 194, 185, alphaForeground);
		parent.stroke(255, 255, 255, alphaForeground);
		parent.ellipse(position[0], position[1], scaleEarthquake, scaleEarthquake);
		
		//| Shockwave Stroke
		parent.stroke(255, 255, 255, alphaShockWave);
		parent.strokeWeight(strokeShockWave);
		parent.ellipse(position[0], position[1], scaleEarthquake +  strokeShockWave, scaleEarthquake +  strokeShockWave);
	}
	
}












