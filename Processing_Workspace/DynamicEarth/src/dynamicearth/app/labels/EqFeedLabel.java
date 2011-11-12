package dynamicearth.app.labels;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class EqFeedLabel {
	
	PApplet parent;

	PFont titleText;
	PFont secondaryTitleText;
	PFont secondaryText;

	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	int animating = 3;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	//| Data 
	String[] latestEarthuake;
	String latestEarthquakeDate;
	int totalEarthuakes;
	
	public EqFeedLabel(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		totalEarthuakes = 0;
		
		//| Tween Test
		Motion.setup(parent);
		tweenBackgroundIN = new Tween(0f, 255f, 20f);
		tweenForegroundIN = new Tween(0f, 255f, 10f, 10f);
		tweenBackgroundOUT = new Tween(255f, 0f, 10f, 5f);
		tweenForegroundOUT = new Tween(255f, 0f, 10f);
				
		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 16);
		secondaryTitleText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
		secondaryText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
	}
	
	public void update(String[] e, int c)
	{	
		switch(animating){
			case 1:
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			break;
			
			case 0:
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			break;
		}

		String[] s = PApplet.split(e[0], ',');
		latestEarthuake = s;
		latestEarthquakeDate = e[1];
		totalEarthuakes = c;
	}
	
	public void open()
	{
		animating = 1;
		tweenBackgroundIN.play();
		tweenForegroundIN.play();
	}
	
	public void close()
	{
		animating = 0;
		tweenBackgroundOUT.play();
		tweenForegroundOUT.play();
	}
	
	public boolean opened()
	{
		return (alphaForeground == 255) ? true : false;
	}
	
	public boolean closed()
	{
		return (alphaBackground == 0) ? true : false;
	}
	
	public void draw(Map m) 	
	{	
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));
		
		parent.pushMatrix();
		parent.translate(tl[0], tl[1]);
		
		//| Box Location
		parent.pushMatrix();
		parent.translate(20, 891);
		
		//| Label
		int boxW = 266;
		int boxH = 140;
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(154,194,185, alphaForeground);
		parent.rect(5, 5, boxW - 10, boxH - 10);
		
		//| Title
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 16);
		parent.text("Total Earthquakes Worldwide",  25, 37);
		parent.text("In The Past 7 Days: " + totalEarthuakes,  25, 60);
	
		//| Secondary
		parent.fill(0, 103, 73, alphaForeground);
		parent.textFont(secondaryTitleText, 12);
		String mag = latestEarthuake[0].substring(1);
		parent.text("Magnitude " + mag, 25, 90);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		String des = latestEarthuake[1].substring(1);
		parent.text(des, 25, 104);
		
		parent.textFont(secondaryText, 12);
		parent.text(latestEarthquakeDate, 25, 118);

		parent.popMatrix();
		parent.popMatrix();
	}
}
