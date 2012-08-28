package findingfaultlines.app.elements;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

import de.fhpotsdam.unfolding.Map;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class LabelEarthquakeArchive {
	
	PApplet parent;

	boolean BAYMODEL = true;
	int hei = (BAYMODEL) ? 1200 : 1050;

	PImage compas;
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
	int archiveLength;
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
		archiveLength = quakes.length;

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

		compas = parent.loadImage("data/images/compas.png");
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
		int buffer = 30;
		int boxW = Math.round((archiveLength / 3) + 50 + 1); //| TASK: Divisble
		int boxH = 260;
		int xpos = buffer + 25;
		int ypos = hei - buffer - boxH;

		int timelineW = Math.round(archiveLength / 3 + 1); //| TASK: Divisble
		float timelineStart = 0.0f; 
		float carrotIncrement = 0.334f; //| TASK: Divisble
		float carrotPosition = Math.round(scrubber / 3); //| TASK: Divisble
		float tick = carrotPosition;

		//| Label Coordinates
		parent.pushMatrix();
		parent.translate(xpos, ypos);
		
		//| Compas
		parent.tint(255, alphaBackground);
		parent.image(compas, boxW/2 - compas.width/2, 0 - compas.height - 5);
		
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
		parent.text("Finding Fault Lines",  25, 37);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		parent.textLeading(14);
		parent.text("A history of earthquakes in the Bay Area since 1973 \n \nEach dot marks the location of an earthquake’s epicenter. The size of the dot shows the quake’s magnitude. \n \nEarthquakes tend to cluster in linear patterns called fault lines.", 25, 42, 300, 130);

		//| Earthquake Current & Timeline Push Matrix
		parent.pushMatrix();
		parent.translate(25, 210);
		
		parent.noSmooth();
		parent.noStroke();
		
		for(int i = 0; i < archiveLength; i ++) 
		{
			float mag = (quakes[i] == 0) ? 1 : quakes[i] * 3;
			//| Graph quakes to a line
//			parent.fill(158, 194, 175, alphaForeground);
			parent.fill(188, 214, 205, alphaForeground);
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
		parent.fill(154, 194, 185, alphaForeground);
		parent.rect(0, 14, timelineW, 1); //| Bottom line
		parent.fill(0, alphaForeground);
		parent.rect(-1, 0, 1, 15); //| Left Brace
		parent.rect(timelineW, 0, 1, 15); //| Right Brace

		parent.fill(0, alphaForeground);
		parent.textFont(yearText, 9);
		parent.textAlign(PConstants.RIGHT);
		parent.text("Present",  timelineW + 1, 28);
		parent.textAlign(PConstants.LEFT);
		parent.text("1973",  -3, 28);
		
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
