package orientation.app.elements;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class LabelOrientation 
{
	PApplet parent;

	boolean BAYMODEL = true;
	int hei = (BAYMODEL) ? 1200 : 1050;

	PImage compas;
	PImage elevation;
	
	PFont titleText;
	PFont secondaryText;
	PFont legendText;
	
	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	Tween tweenTickerOUT;

	Tween tweenRevealIN;
	Tween tweenRevealOUT;
	
	int animating = 0;
	float alphaBackground = 0;
	float alphaForeground = 0;
	float alphaTicker = 0;
	float alphaReveal = 0;
	
	int currentElevation = 63;
	String textElevation = "You're 400 meters";
	String textSecondary = "below sea level";
	
	public LabelOrientation(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Tween Test
		Motion.setup(parent);
		tweenBackgroundIN = new Tween(0f, 255f, 15f);
		tweenForegroundIN = new Tween(0f, 255f, 20f, 15f);
		tweenBackgroundOUT = new Tween(255f, 0f, 10f, 10f);
		tweenForegroundOUT = new Tween(255f, 0f, 10f);
		tweenTickerOUT = new Tween(255f, 0f, 10f);
		tweenRevealIN = new Tween(0f, 255f, 15f, 7f);
		tweenRevealOUT = new Tween(255f, 0f, 10f);

		compas = parent.loadImage("data/images/compas.png");
		elevation = parent.loadImage("data/images/elevation.png");
		
		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 16);
		secondaryText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
		legendText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 9);
	}
	
	public void setElevation(float e)
	{	
		int elevation = Math.round(e);
		int convertedElevation = 63 - elevation;
		
		if(elevation < 16){
			int txt = (convertedElevation - 47) * 25;
			textElevation = "You're " + txt + " meters";
			textSecondary = "below sea level";
			
		} else if(elevation > 16) {
			int txt = (elevation - 16) * 25;
			textElevation = "You're " + txt + " meters";
			textSecondary = "above sea level";
			
		} else {
			textElevation = "You're now";
			textSecondary = "at sea level";
		}
		
		currentElevation = convertedElevation;
	}
	
	public void update()
	{	
		switch(animating){		
			case 1: //| Animate In
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			alphaTicker = tweenForegroundIN.getPosition();
			if(alphaBackground == 255) tweenBackgroundIN.stop();
			if(alphaForeground == 255) tweenForegroundIN.stop();
			break;
			
			case 2: //| End Of The Road
			alphaReveal = tweenRevealIN.getPosition();
			alphaTicker = tweenTickerOUT.getPosition();
			if(alphaTicker == 0) tweenTickerOUT.stop();
			break;
			
			case 3: //| Animate Out
			alphaReveal = tweenRevealOUT.getPosition();
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			if(alphaBackground == 0) tweenBackgroundOUT.stop();
			if(alphaForeground == 0) tweenForegroundOUT.stop();
			break;
		}
	}
	
	public void open()
	{
		animating = 1;
		tweenBackgroundIN.play();
		tweenForegroundIN.play();
	}
	
	public void sequenceEnd()
	{
		animating = 2;
		tweenRevealIN.play();
		tweenTickerOUT.play();
	}
	
	public void close()
	{
		animating = 3;
		tweenRevealOUT.play();
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
	
	public void draw() 	
	{	
		//| Label Starting Variables
		int buffer = 30;
		int boxW = 305;
		int boxH = 199;
		int xpos = buffer + 70; //| + 70 to offset from trench
		int ypos = hei - buffer - boxH;
		
		//| Box Location Matrix
		parent.pushMatrix();
		parent.translate(xpos,ypos);
		
		//| Label
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(160, alphaForeground);
		parent.rect(5, 5, boxW - 11, boxH - 11);
		
		//| Compas
		parent.tint(255, alphaBackground);
		parent.image(compas, boxW/2 - compas.width/2, 0 - compas.height - 5);
		
		//| Title and Subtitle
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 16);
		parent.text("Bay Area Topography",  25, 37);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		parent.textLeading(14);
		parent.text("An orientation through elevation", 25, 42, 268, 100);
		
		//| Legend Matrix
		parent.pushMatrix();
		parent.translate(35, 100);
		
		//| Compas
		parent.tint(255, alphaForeground);
		parent.image(elevation, 2, -1);
		
		//| Spine
		parent.noStroke();
		parent.fill(193,180,154, alphaForeground);
		parent.rect(0, 0, 2, 48);
		parent.rect(-1, 7, 4, 1);
		parent.rect(-1, 15, 4, 1);
		parent.rect(-1, 23, 4, 1);
		parent.rect(-1, 31, 4, 1);
		parent.rect(-1, 39, 4, 1);
		parent.rect(-1, 47, 4, 1);
		parent.fill(119,204,216, alphaForeground);
		parent.rect(0, 48, 2, 16);
		parent.rect(-1, 55, 4, 1);
		parent.rect(-1, 63, 4, 1);
		
		//| Spinal Cap and Text, Top and Bottom
		parent.fill(160, alphaForeground);
		parent.rect(-0.5f, -1, 3, 1);
		parent.rect(-0.5f, 64, 134, 1);

		parent.textAlign(PConstants.LEFT);
		parent.fill(0, alphaForeground);
		parent.textFont(legendText, 9);
		parent.text("1,175m", -11, -15, 67, 12);
		parent.text("0", -11, 41, 67, 12);
		parent.text("-400m", -13, 67, 67, 12);
		
		//| Final Graphic
		parent.noStroke();
		parent.fill(180,alphaReveal);
		parent.rect(141, 0, 2, 48);
		parent.rect(140, 7, 3, 1);
		parent.rect(140, 15, 3, 1);
		parent.rect(140, 23, 3, 1);
		parent.rect(140, 31, 3, 1);
		parent.rect(140, 39, 3, 1);
		parent.rect(140, 47, 4, 1);
		parent.fill(140,alphaReveal);
		parent.rect(141, 48, 2, 16);
		parent.rect(140, 55, 3, 1);
		parent.rect(140, 63, 3, 1);

		parent.fill(80, alphaReveal);
		parent.rect(138.5f, -1, 6, 1);
		parent.rect(138.5f, 64, 6, 1);

		parent.textFont(secondaryText, 12);
		parent.textLeading(14);
		parent.fill(140, alphaReveal);
		parent.text("In total the model", 148, 35, 268, 100);
		parent.fill(140, alphaReveal);
		parent.text("covers 1,575 meters", 148, 47, 268, 100);
		
		//| Elevation Ticker & Matrix
		float ticker = currentElevation;
		
		parent.pushMatrix();
		parent.translate(0, ticker);
		
		parent.fill(0, alphaTicker);
		parent.rect(0, 0, 136, 1);
		if(ticker > 55) parent.rect(135, -2, 1, 3);
		else if(ticker < 8) parent.rect(135, 0, 1, 3);
		else parent.rect(135, -2, 1, 5);

		parent.textFont(secondaryText, 12);
		parent.textLeading(14);
		parent.fill(0, alphaTicker);
		parent.text(textElevation, 143, -11, 268, 100);
		parent.fill(100, alphaTicker);
		parent.text(textSecondary, 143, 1, 268, 100);

		parent.popMatrix();
		parent.popMatrix();
		parent.popMatrix();
	}
	
	public void kill()
	{
		animating = 0;
		alphaBackground = 0;
		alphaForeground = 0;
		alphaTicker = 0;
		alphaReveal = 0;
		
		currentElevation = 63;
		textElevation = "You're 400 meters";
		textSecondary = "below sea level";
		
		tweenBackgroundIN.seek(0);
		tweenForegroundIN.seek(0);
		tweenBackgroundOUT.seek(0);
		tweenForegroundOUT.seek(0);
		tweenTickerOUT.seek(0);
		tweenRevealIN.seek(0);
		tweenRevealOUT.seek(0);
	}
}
