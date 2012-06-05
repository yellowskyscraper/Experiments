package dynamicearth2.app.elements;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class LabelEarthquakeArchive {
	
	PApplet parent;

	boolean BAYMODEL = false;
	int hei = (BAYMODEL) ? 1200 : 1050;

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
	float[] quakes;
	String latestEarthuake;
	String latestEarthquakePlace;
	String latestEarthquakeDate;
	int totalEarthuakes;
	float scrubber = 0;
	
	public LabelEarthquakeArchive(PApplet p)
	{
		parent = p;
	}
	
	public void setup(float[] q)
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

	public void open()
	{
		animating = 1;
		tweenBackgroundIN.play();
		tweenForegroundIN.play();
	}
	
	public void close()
	{
		animating = 2;
		tweenBackgroundOUT.play();
		tweenForegroundOUT.play();
	}
	
	public boolean isOpened()
	{
		if(alphaForeground == 255) delay += 1;
		return (alphaForeground == 255 && delay > 20) ? true : false;
	}
	
	public boolean isClosed()
	{
		return (alphaForeground == 0) ? true : false;
	}
	
	public void timelineFinished()
	{

	}
	
	public void scrubber(float s)
	{
		scrubber = s;
	}
	
	public void update(String[] q, float m)
	{	
		switch(animating){
			case 1:
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			alphaForegroundTicker = tweenForegroundIN.getPosition();
			break;
			
			case 2:
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			alphaForegroundTicker = tweenForegroundOUT.getPosition();
			break;
		}

		latestEarthuake = "Magnitude " + m;
		latestEarthquakeDate = q[1] +" "+ q[2] +", "+ q[0];
	}
	
	public void draw(Map m)
	{	
		//| Label Starting Variables
		int archiveLength = quakes.length;
		
		int buffer = 30;
		int boxW = (archiveLength / 4) + 50 + 1; //514
		int boxH = 159;
		int xpos = buffer;
		int ypos = hei - buffer - boxH;
		
		int timelineW = archiveLength / 4 + 1;
		float timelineStart = 0.0f; 
		float carrotIncrement = 0.25f;
		float carrotPosition = scrubber / 4;
		float tick = carrotPosition; //| Check for graphical elements
		
		//| Label 
		parent.pushMatrix();
		parent.translate(xpos, ypos);
		
		//| Backing, white with an inset border 
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(154,194,185, alphaForeground);
		parent.rect(5, 5, boxW - 11, boxH - 11);

		//| Title and Subtitle
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 16);
		parent.text("Recorded Earthquakes from 1973 to 2011",  25, 37);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		parent.textLeading(14);
		parent.text("Source: United States Geological Service", 25, 42, 268, 100);

		//| Earthquake Current & Timeline Push Matrix
		parent.pushMatrix();
		parent.translate(25, 114);
		
		parent.noSmooth();
		parent.noStroke();
		
		for(int i = 0; i < archiveLength; i ++) 
		{
			float mag = (quakes[i] == 0) ? 1 : quakes[i] * 3;
			//| Graph quakes to a line
			parent.fill(158, 194, 175, alphaForeground);
			parent.rect(timelineStart, 15 - mag, 1, mag);
			timelineStart += carrotIncrement;
		}
		
		//| Carrot and Text
		boolean neartheend = false;
		if(tick > (timelineW - 74)) {
			neartheend = true;
			parent.textAlign(PConstants.RIGHT);
			tick = timelineW + 1;
		}
		
		parent.fill(0, 103, 73, alphaForegroundTicker);
		parent.textFont(secondaryTitleText, 12);
		parent.text(latestEarthuake, tick, -24); //| Magnitude Text
		parent.fill(100, 100, 100, alphaForegroundTicker);
		parent.textFont(secondaryText, 12);
		parent.text(latestEarthquakeDate, tick, -12); //| Date Text
		
		parent.fill(0, 103, 73, alphaForegroundTicker);
		if(neartheend) {
			parent.rect(carrotPosition, -7, 1, 22); //| Carrot Stem
			parent.rect(carrotPosition, -7, -2, 1); //| Carrot Top
		} else {
			parent.rect(carrotPosition, -7, 1, 22); //| Carrot Stem
			parent.rect(carrotPosition, -7, 3, 1); //| Carrot Top
		}
		
		//| Timeline Braces and Year
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.rect(-1, 0, 1, 15); //| Left Brace
		parent.rect(timelineW, 0, 1, 15); //| Right Brace

		parent.fill(0, alphaForeground);
		parent.textFont(yearText, 9);
		parent.textAlign(PConstants.RIGHT);
		parent.text("2011",  timelineW + 1, 26);
		parent.textAlign(PConstants.LEFT);
		parent.text("1973",  0, 26);
		
		parent.popMatrix(); //| Pop Timeline Matrix
		parent.popMatrix(); //| Pop Label Matrix
	}
		
	public void writeLabelStatic(int t)
	{	
		latestEarthuake = "";
		latestEarthquakeDate = "Total recorded earthquakes: " + t;
	}
	
	public void kill()
	{
		animating = 0;
		scrubber = 0;
		tweenBackgroundIN.stop();
		tweenForegroundIN.stop();
		tweenBackgroundOUT.stop();
		tweenForegroundOUT.stop();
		alphaBackground = 0;
		alphaForeground = 0;
		alphaForegroundTicker = 0;
		latestEarthuake = "";
		latestEarthquakeDate = "";
	}
}
