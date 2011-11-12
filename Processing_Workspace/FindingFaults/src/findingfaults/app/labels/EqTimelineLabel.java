package findingfaults.app.labels;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import findingfaults.app.graphics.Ball;

public class EqTimelineLabel {
	
	PApplet parent;

	PFont titleText;
	PFont secondaryTitleText;
	PFont secondaryText;

	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	Tween tweenQuakesDIM;
	int animating = 3;
	float alphaBackground = 0;
	float alphaForeground = 0;
	float alphaForegroundTicker = 0;
	int delay = 0;
	
	//| Data 
	String[][] quakes;
	String latestEarthuake;
	String latestEarthquakePlace;
	String latestEarthquakeDate;
	int totalEarthuakes;
	float markerTick = 51;
	float increment = 0.327f;
	
	public EqTimelineLabel(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String[][] q)
	{
		quakes = q;

		latestEarthuake = "";
		latestEarthquakePlace = "";
		latestEarthquakeDate = "";
		totalEarthuakes = 0;

		//| Tween Test
		Motion.setup(parent);
		tweenBackgroundIN = new Tween(0f, 255f, 20f);
		tweenForegroundIN = new Tween(0f, 255f, 10f, 10f);
		tweenBackgroundOUT = new Tween(255f, 0f, 10f, 5f);
		tweenForegroundOUT = new Tween(255f, 0f, 10f);
		tweenQuakesDIM = new Tween(255f, 0f, 10f);

		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 16);
		secondaryTitleText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
		secondaryText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
	}
	
	public void update(int y, String m, int d, float c)
	{	
		switch(animating){
			case 2:
			float j = tweenQuakesDIM.getPosition();
			alphaForegroundTicker = j;
			break;
		
			case 1:
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			alphaForegroundTicker = tweenForegroundIN.getPosition();;
			break;
			
			case 0:
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			alphaForegroundTicker = tweenForegroundOUT.getPosition();
			break;
		}
		
		markerTick = increment * c;
		latestEarthquakeDate = y +" "+ m +" "+ d;
		latestEarthquakeDate = "";
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
		if(alphaForeground == 255) delay += 1;
		return (alphaForeground == 255 && delay > 20) ? true : false;
	}
	
	public boolean closed()
	{
		return (alphaForeground == 0) ? true : false;
	}
	
	public void done1()
	{
		animating = 2;
		tweenQuakesDIM.play();
	}
	
	public void done()
	{
		animating = 0;
		tweenBackgroundOUT.play();
		tweenForegroundOUT.play();
	}
	
	public void draw(Map m)
	{	
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));
		
		//| Backing
		parent.pushMatrix();
		parent.translate(tl[0], tl[1]);
		
		//| Box Location
		parent.pushMatrix();
		parent.translate(20, 891);
		
		//| Label
		int boxW = 306;
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
		parent.text("Cumulative Earthquakes 1973-2010",  25, 37);
	
		//| Sub Title
		int tick = PApplet.round(markerTick) + 24;
		if(tick > 213) {
			parent.textAlign(PConstants.RIGHT);
			tick = boxW - 25;
		}
		
		parent.fill(0, 103, 73, alphaForegroundTicker);
		parent.textFont(secondaryTitleText, 12);
		parent.text(latestEarthuake, tick, 67);

		parent.fill(100, 100, 100, alphaForegroundTicker);
		parent.textFont(secondaryText, 12);
		parent.text(latestEarthquakePlace, tick, 81);
		
		parent.text(latestEarthquakeDate, tick, 95);
		
		this.drawTimeline();

		parent.popMatrix();
		parent.popMatrix();
	}
	
	public void drawTimeline()
	{	
		parent.pushMatrix();
		parent.translate(25, 97);
		
		//| Draw Time Line
		//| 253 / 776 = increment percentage.
		int width = 255;  
		float start = 1.0f; 
		parent.noSmooth();
		parent.noStroke();
		
		for(int i = 0; i < quakes.length; i ++) 
		{
			float mag = Float.parseFloat(quakes[i][4]) * 3;
			if(mag == 0) mag = 1;
			
			parent.fill(158, 194, 175, alphaForeground);
			parent.rect(start, 15 - mag, 1, mag);
			
			start += increment;
		}
		
		float tick = PApplet.round(markerTick);
		
		parent.smooth();
//		parent.fill(158, 194, 175, alphaForeground);
//		parent.rect(0, 15, width, 1); //| Base Line
		
		parent.fill(0, 103, 73, alphaForegroundTicker);
		parent.rect(tick, -7, 2, 22);
		parent.rect(tick, -7, 4, 1);

		parent.fill(0, alphaForeground);
		parent.rect(0, 0, 1, 15); //| Left Brace
		parent.rect(width, 0, 1, 15); //| Right Brace

		parent.popMatrix();
	}
	
	public void write(String[] q)
	{	
		latestEarthuake = "Magnitude " + q[4];
		//String[] temp = q[3].substring(1).split(" ");
		//for(int i=0; i<temp.length; i++) temp[i] = toProperCase(temp[i]); 
		//latestEarthquakePlace = PApplet.join(temp, ' ');
		latestEarthquakePlace = q[1] +" "+ q[2] +", "+ q[0];
	}
	
	public static String toProperCase(String t)
	{	
		String forename = t;
		String forenameInitial = "";

		forename = forename.toLowerCase();
		forenameInitial = forename.substring(0, 1).toUpperCase();
		forename = forenameInitial + forename.substring(1,forename.length());
	
		return forename;
	}
}
