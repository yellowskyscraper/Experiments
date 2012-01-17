package dynamicearth.app.labels;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class EqTimelineLabel {
	
	PApplet parent;
	boolean MODEL = false;

	PFont titleText;
	PFont secondaryTitleText;
	PFont secondaryText;
	PFont yearText;

	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	Tween tweenQuakesDIM;
	int animating = 0;
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
		tweenQuakesDIM = new Tween(255f, 0f, 10f);
		tweenBackgroundOUT = new Tween(255f, 0f, 20f, 20f);
		tweenForegroundOUT = new Tween(255f, 0f, 10f, 10);

		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 16);
		secondaryTitleText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
		secondaryText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
		yearText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 9);
	}
	
	public void update(int y, String m, int d, float c)
	{	
		switch(animating){
			case 1:
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			alphaForegroundTicker = tweenForegroundIN.getPosition();;
			break;

			case 2:
			alphaForegroundTicker = tweenQuakesDIM.getPosition();
			break;
			
			case 3:
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
		animating = 3;
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
	
	public void timelineFinished()
	{
		animating = 2;
		tweenQuakesDIM.play();
	}
	
	public void draw(Map m)
	{	
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));
		int xpos = Math.round(tl[0]);
		int ypos = Math.round(tl[0]);
		
		//| Backing
		parent.pushMatrix();
		parent.translate(xpos, ypos);

		//| Labal
		if(MODEL) this.drawLabel(1019 - 159 - 20, 34 + 306, -90);
		else this.drawLabel(27, 1019 - 159, 0);
		
		parent.popMatrix();
	}
	
	public void drawLabel(int x, int y, int r)
	{
		//| Box Location
		parent.pushMatrix();
		parent.translate(x,y);
		parent.rotate(PApplet.radians(r));
		
		//| Label
		int boxW = 306;
		int boxH = 159;
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(154,194,185, alphaForeground);
		parent.rect(5, 5, boxW - 11, boxH - 11);
			
		//| Title
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 16);
		parent.text("Recorded Earthquakes 1973-2010",  25, 37);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		parent.textLeading(14);
		String des = "Source: United States Geological Service";
		parent.text(des, 25, 42, 268, 100);
	
		//| Sub Title
		int tick = PApplet.round(markerTick) + 24;
		if(tick > 213) {
			parent.textAlign(PConstants.RIGHT);
			tick = boxW - 25;
		}
		
		parent.fill(0, 103, 73, alphaForegroundTicker);
		parent.textFont(secondaryTitleText, 12);
		parent.text(latestEarthuake, tick, 84);

		parent.fill(100, 100, 100, alphaForegroundTicker);
		parent.textFont(secondaryText, 12);
		parent.text(latestEarthquakePlace, tick, 98);
		
		parent.text(latestEarthquakeDate, tick, 112);
		
		this.drawTimeline();

		parent.popMatrix();
	}

	public void drawTimeline()
	{	
		parent.pushMatrix();
		parent.translate(25, 114);
		
		//| Draw Time Line
		//| 253 / 776 = increment percentage
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
		
		parent.fill(0, 103, 73, alphaForegroundTicker);
		parent.rect(tick, -7, 2, 22);
		if(tick < 194) parent.rect(tick, -7, 4, 1);
		else parent.rect(tick, -7, -2, 1);

		parent.fill(0, alphaForeground);
		parent.rect(0, 0, 1, 15); //| Left Brace
		parent.rect(width, 0, 1, 15); //| Right Brace

		parent.fill(0, alphaForeground);
		parent.textFont(yearText, 9);
		parent.textAlign(PConstants.RIGHT);
		parent.text("2010",  width + 1, 26);
		parent.textAlign(PConstants.LEFT);
		parent.text("1973",  0, 26);
		
		parent.popMatrix();
	}
	
	public void write(String[] q)
	{	
		latestEarthuake = "Magnitude " + q[4];
		latestEarthquakePlace = q[1] +" "+ q[2] +", "+ q[0];
	}
	
	public void writeStatic(int t)
	{	
		latestEarthuake = "";
		latestEarthquakePlace = "Total recorded earthquakes: " + t;
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
	
	public void kill()
	{
		animating = 0;
		markerTick = 51;
		tweenBackgroundIN.stop();
		tweenForegroundIN.stop();
		tweenBackgroundOUT.stop();
		tweenForegroundOUT.stop();
		alphaBackground = 0;
		alphaForeground = 0;
		alphaForegroundTicker = 0;
	}
}
