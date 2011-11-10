package dynamicearth.app.labels;

import processing.core.PApplet;
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

	Tween tweenIN;
	Tween tweenOUT;
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
		tweenIN = new Tween(0f, 255f, 30f);
		tweenOUT = new Tween(255f, 0f, 10f);
				
		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 21);
		secondaryTitleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 15);
		secondaryText = parent.createFont("data/fonts/ExploSlab/ExploSlab.otf", 15);
	}
	
	public void update(String[] e, int c)
	{	
		switch(animating){
			case 1:
			float v = tweenIN.getPosition();
			alphaBackground = v;
			alphaForeground = v;
			break;
			
			case 0:
			float x = tweenOUT.getPosition();
			alphaBackground = x;
			alphaForeground = x;
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
		tweenIN.play();
	}
	
	public void close()
	{
		animating = 0;
		tweenOUT.play();
	}
	
	public boolean opened()
	{
		return (alphaForeground == 255) ? true : false;
	}
	
	public boolean closed()
	{
		return (alphaForeground == 0) ? true : false;
	}
	
	public void draw(Map m) 	
	{	
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));
		
		parent.pushMatrix();
		parent.translate(tl[0], tl[1]);
		
		//| Label
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(20, 855, 336, 176);
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(154,194,185, alphaBackground);
		parent.rect(25, 860, 326, 166);
		
		//| Type
		parent.smooth();

		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 21);
		parent.text("Total Earthquakes Worldwide",  50, 900);
		parent.text("In The Past 7 Days: " + totalEarthuakes,  50, 925);
	
		parent.fill(0, 103, 73, alphaForeground);
		parent.textFont(secondaryTitleText, 15);
		String mag = latestEarthuake[0].substring(1);
		parent.text("Magnitude " + mag, 50, 960);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 15);
		String des = latestEarthuake[1].substring(1);
		parent.text(des, 50, 980);
		
		parent.textFont(secondaryText, 15);
		parent.text(latestEarthquakeDate, 50, 1000);
		
		parent.popMatrix();
	}
}
